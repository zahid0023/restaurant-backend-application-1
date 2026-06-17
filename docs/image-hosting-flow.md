# Image Hosting Service — Full Flow Documentation

## Table of Contents

1. [Overview](#overview)
2. [Core Concept: Per-Call Provider Config](#core-concept-per-call-provider-config)
3. [Architecture Layers](#architecture-layers)
4. [Component Map](#component-map)
5. [How Config Validation Works](#how-config-validation-works)
6. [Upload Flows](#upload-flows)
   - [Single Upload](#single-upload)
   - [Batch Upload with Rollback](#batch-upload-with-rollback)
   - [Delete](#delete)
7. [Full End-to-End Upload Sequence](#full-end-to-end-upload-sequence)
8. [Data Flow Diagram](#data-flow-diagram)
9. [Provider Config Reference](#provider-config-reference)
   - [S3 Config](#s3-config)
   - [Cloudinary Config](#cloudinary-config)
10. [How Strategy Selection Works](#how-strategy-selection-works)
11. [Component Reference](#component-reference)
12. [Error Reference](#error-reference)

---

## Overview

The image hosting system is designed for **multi-provider** operation in a restaurant
backend context.

The caller (controller or service) is responsible for supplying:
1. The `MultipartFile`(s) to upload
2. The `ImageHostingProvider` enum value (`S3` or `CLOUDINARY`)
3. A `Map<String, String> config` containing the required credentials for that provider

There is no stored config entity. Credentials are passed directly per call —
typically sourced from the restaurant's config, environment variables, or a
separately managed secrets store. The upload service validates the config map,
selects the correct strategy, and returns the CDN URL and public ID.

---

## Core Concept: Per-Call Provider Config

```
Caller  →  imageUploadService.upload(file, S3, { "bucket": "...", "region": "...", ... })
Caller  →  imageUploadService.upload(file, CLOUDINARY, { "cloud_name": "...", "api_key": "...", ... })
```

The provider and credentials are resolved by the caller before calling the upload
service. The upload service itself has no knowledge of where credentials come from.

---

## Architecture Layers

```
┌─────────────────────────────────────────────────────────────────────┐
│  HTTP Layer                                                          │
│  Any domain controller (e.g. RestaurantBasicInfoController)         │
└────────────────────────────┬────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│  Upload Service Layer                                                │
│  ImageUploadService / ImageUploadServiceImpl                        │
│  upload(file, provider, config)                                     │
│  uploadAll(images, metaList, provider, config)  ← with rollback     │
│  delete(publicId, provider, config)                                 │
└────────────────────────────┬────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│  Strategy Layer                                                      │
│  ImageHostingStrategyRegistry                                        │
│  ┌──────────────────────┐   ┌──────────────────────────────────┐    │
│  │ CloudinaryHosting    │   │ S3HostingStrategy                │    │
│  │ Strategy             │   │                                  │    │
│  └──────────────────────┘   └──────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│  Provider SDKs                                                       │
│  Cloudinary SDK               AWS SDK v2 (S3Client)                 │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Component Map

| Component | Package | Role |
|---|---|---|
| `ImageHostingProvider` (enum) | `imagehosting/enums` | `S3` / `CLOUDINARY` — each holds its required config keys and `validate()` |
| `ImageUploadService` | `imagehosting/service` | Interface: `upload`, `delete`, `uploadAll` |
| `ImageUploadServiceImpl` | `imagehosting/serviceImpl` | Resolves strategy from registry, delegates; handles `uploadAll` rollback |
| `ImageHostingStrategyRegistry` | `imagehosting/strategy` | `Map<provider, strategy>` — O(1) strategy lookup |
| `ImageHostingStrategy` | `imagehosting/strategy` | Strategy interface: `provider()` + `upload(file, config)` + `delete(publicId, config)` |
| `CloudinaryHostingStrategy` | `imagehosting/strategy` | Uploads/deletes via Cloudinary SDK using credentials from config map |
| `S3HostingStrategy` | `imagehosting/strategy` | Uploads/deletes via AWS SDK v2 using credentials from config map |
| `ImageStorageConfigRequest` | `imagehosting/dto/request` | DTO carrying `provider` + `config` map |
| `ImageMetaRequest` | `imagehosting/dto/request` | Metadata per image: `clientImageId`, `caption`, `isDefault`, `sortOrder` |
| `ImageRequest` | `imagehosting/dto/request` | Assembled after upload: URL + publicId + metadata |
| `ImageUploadResponse` | `imagehosting/dto/response` | Raw result from storage: `imageUrl` + `publicId` |
| `ImageEntity` | `imagehosting/model/entity` | `@MappedSuperclass`: `imageUrl`, `publicId`, `caption`, `isDefault`, `sortOrder` |

---

## How Config Validation Works

The `ImageHostingProvider` enum owns the required keys for each provider and
validates the caller-supplied config map before any SDK call is made:

```java
// Called by each strategy before upload/delete
provider().validate(config);
```

**S3 required keys:** `bucket`, `region`, `access_key`, `secret_key`

**CLOUDINARY required keys:** `cloud_name`, `api_key`, `api_secret`

If any required key is missing or blank, `validate()` throws `IllegalArgumentException`
listing all missing keys before any network call is made.

```
provider = S3, config = { "bucket": "my-bucket", "region": "us-east-1" }
  → validate() → missing: ["access_key", "secret_key"]
  → IllegalArgumentException: "Missing or blank required config keys for provider S3: [access_key, secret_key]"
```

---

## Upload Flows

### Single Upload

```
imageUploadService.upload(file, provider, config)
  │
  ├── registry.get(provider)        → matching ImageHostingStrategy
  └── strategy.upload(file, config)
        ├── provider().validate(config)     ← check all required keys
        ├── build SDK client from config
        ├── upload binary to storage
        └── return ImageUploadResponse { imageUrl, publicId }
```

---

### Batch Upload with Rollback

`uploadAll` uploads multiple files, matching each file to its metadata by
`clientImageId` (which must equal the file's `originalFilename`). If any upload
fails mid-batch, all already-uploaded images are deleted automatically:

```
imageUploadService.uploadAll(images, metaList, provider, config)
  │
  ├── build metaMap: clientImageId → ImageMetaRequest
  │
  ├── for each file in images:
  │     meta = metaMap.get(file.originalFilename)
  │     if meta == null → 400 "No metadata found for image: <filename>"
  │
  │     response = upload(file, provider, config)
  │     uploadedPublicIds.add(response.publicId)
  │
  │     assemble ImageRequest {
  │       imageUrl:  response.imageUrl,
  │       publicId:  response.publicId,
  │       caption:   meta.caption,
  │       isDefault: meta.isDefault,
  │       sortOrder: meta.sortOrder
  │     }
  │
  ├── if any upload throws:
  │     for each already-uploaded publicId:
  │       delete(publicId, provider, config)   ← rollback
  │     re-throw original exception
  │
  └── return List<ImageRequest>
```

The caller receives `List<ImageRequest>` — all files uploaded successfully or
none (rollback ensures no orphaned storage objects on partial failure).

---

### Delete

```
imageUploadService.delete(publicId, provider, config)
  │
  └── strategy.delete(publicId, config)
        ├── provider().validate(config)
        ├── S3: s3Client.deleteObject(bucket, publicId)
        └── Cloudinary: cloudinary.uploader().destroy(publicId, emptyMap())
```

---

## Full End-to-End Upload Sequence

```
Client
  │
  │  POST /api/v1/restaurant-basic-info/{id}/images
  │  Content-Type: multipart/form-data
  │  { images: [<file1>, <file2>], meta: [{ clientImageId, caption, isDefault, sortOrder }, ...] }
  │
  ▼
Controller
  │
  ├── 1. Load restaurant entity
  │       restaurantBasicInfoService.getEntityById(id)
  │
  ├── 2. Resolve provider and config
  │       provider = ImageHostingProvider.S3   (from request or restaurant settings)
  │       config = {
  │         "bucket":     "restaurant-images",
  │         "region":     "us-east-1",
  │         "access_key": "AKIA...",
  │         "secret_key": "wJalr..."
  │       }
  │
  ├── 3. Upload all images
  │       List<ImageRequest> imageRequests =
  │         imageUploadService.uploadAll(images, metaList, provider, config)
  │
  │       For each file:
  │         provider().validate(config)
  │         key = UUID + "-" + filename
  │         PutObjectRequest { bucket, key, contentType }
  │         s3Client.putObject(request, inputStream)
  │         → ImageUploadResponse { imageUrl, publicId: key }
  │         → ImageRequest { imageUrl, publicId, caption, isDefault, sortOrder }
  │
  ├── 4. Map to domain image entities
  │       images.stream()
  │         .map(req → {
  │           entity.setImageUrl(req.imageUrl)
  │           entity.setPublicId(req.publicId)
  │           entity.setCaption(req.caption)
  │           entity.setIsDefault(req.isDefault)
  │           entity.setSortOrder(req.sortOrder)
  │         })
  │
  └── 5. Persist + return
          repository.saveAll(imageEntities)
          → 201 { success: true, id: <restaurant-id> }
```

---

## Data Flow Diagram

```
┌──────────────────────────────────────────────────────────────────────────┐
│                         IMAGE UPLOAD REQUEST                              │
│  multipart: images[] + meta[] { clientImageId, caption, isDefault, ... } │
└─────────────────────────────┬────────────────────────────────────────────┘
                              │
                    ┌─────────▼──────────┐
                    │    Controller      │
                    └─────────┬──────────┘
                              │
              ┌───────────────▼───────────────────┐
              │  STEP 1: Resolve Provider + Config │
              │                                    │
              │  provider = S3 | CLOUDINARY        │
              │  config = Map<String, String>       │
              │    containing required credentials │
              └───────────────┬───────────────────┘
                              │
              ┌───────────────▼───────────────────┐
              │  STEP 2: Validate Config           │
              │                                    │
              │  provider().validate(config)       │
              │  → throws if any required key      │
              │    is missing or blank             │
              └───────────────┬───────────────────┘
                              │
              ┌───────────────▼───────────────────┐
              │  STEP 3: Upload via Strategy       │
              │                                    │
              │  imageUploadService                │
              │    .uploadAll(images, meta,        │
              │               provider, config)    │
              │        │                           │
              │        └─ for each file:           │
              │           registry.get(provider)   │
              │           → strategy               │
              │           → strategy.upload(file,  │
              │               config)              │
              │        └─ returns List<ImageRequest│
              │           { imageUrl, publicId,    │
              │             caption, isDefault,    │
              │             sortOrder }            │
              │        └─ on failure: rollback     │
              │           (delete uploaded files)  │
              └───────────────┬───────────────────┘
                              │
              ┌───────────────▼───────────────────┐
              │  STEP 4: Persist Image Records     │
              │                                    │
              │  XxxImageEntity {                  │
              │    parent_id,                      │
              │    image_url  ← imageUrl,          │
              │    public_id  ← publicId,          │
              │    caption, isDefault, sortOrder   │
              │  }                                 │
              │  → repository.saveAll(entities)    │
              └───────────────┬───────────────────┘
                              │
              ┌───────────────▼───────────────────┐
              │  RESPONSE                          │
              │  201 Created                       │
              │  { success: true, id: <id> }       │
              └────────────────────────────────────┘
```

---

## Provider Config Reference

### S3 Config

**Required keys** in the `config` map:

| Key | Description | Example |
|---|---|---|
| `bucket` | S3 bucket name | `restaurant-images` |
| `region` | AWS region code | `us-east-1`, `ap-southeast-1` |
| `access_key` | IAM access key ID | `AKIAIOSFODNN7EXAMPLE` |
| `secret_key` | IAM secret access key | `wJalrXUtnFEMI/K7MDENG/...` |

**How it is used at upload time:**

```java
// S3HostingStrategy.upload()
provider().validate(config);  // throws if any key missing/blank

try (S3Client s3Client = S3Client.builder()
        .region(Region.of(config.get("region")))
        .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create(config.get("access_key"), config.get("secret_key"))))
        .build()) {

    String key = UUID.randomUUID() + "-" + file.getOriginalFilename();

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(config.get("bucket"))
            .key(key)
            .contentType(file.getContentType())
            .build(),
        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
    );

    return ImageUploadResponse.builder()
        .imageUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + key)
        .publicId(key)
        .build();
}
```

A new `S3Client` is created and closed per upload call (try-with-resources).

**Stored image result:**

| Field | Value |
|---|---|
| `imageUrl` | `https://restaurant-images.s3.us-east-1.amazonaws.com/uuid-filename.jpg` |
| `publicId` | `uuid-filename.jpg` (the S3 object key — used for deletion) |

---

### Cloudinary Config

**Required keys** in the `config` map:

| Key | Description | Example |
|---|---|---|
| `cloud_name` | Cloudinary cloud identifier | `my-restaurant-cloud` |
| `api_key` | Cloudinary API key | `123456789012345` |
| `api_secret` | Cloudinary API secret | `abcDEFghiJKLmnoPQR` |

**How it is used at upload time:**

```java
// CloudinaryHostingStrategy.upload()
provider().validate(config);

Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
    "cloud_name", config.get("cloud_name"),
    "api_key",    config.get("api_key"),
    "api_secret", config.get("api_secret"),
    "secure",     true
));

Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

return ImageUploadResponse.builder()
    .imageUrl(result.get("secure_url").toString())
    .publicId(result.get("public_id").toString())
    .build();
```

A new `Cloudinary` client is instantiated per upload call from the config map.

**Stored image result:**

| Field | Value |
|---|---|
| `imageUrl` | `https://res.cloudinary.com/{cloud}/image/upload/v.../public_id.jpg` |
| `publicId` | Cloudinary asset identifier (required for transforms and deletion) |

---

## How Strategy Selection Works

```
ImageHostingStrategyRegistry
  strategies = {
    S3         → S3HostingStrategy,
    CLOUDINARY → CloudinaryHostingStrategy
  }

// Built at application startup — Spring collects all @Component ImageHostingStrategy beans
// Lookup is O(1)

registry.get(ImageHostingProvider.S3)         → S3HostingStrategy
registry.get(ImageHostingProvider.CLOUDINARY) → CloudinaryHostingStrategy
registry.get(unknown)                         → IllegalArgumentException
```

The registry is immutable after construction. No `@ConditionalOnProperty` is used —
both strategies are always registered as beans. The caller controls which provider
is selected by passing the appropriate `ImageHostingProvider` enum value.

---

## Component Reference

### `ImageHostingProvider` (enum)

```java
public enum ImageHostingProvider {
    S3(List.of("bucket", "region", "access_key", "secret_key")),
    CLOUDINARY(List.of("cloud_name", "api_key", "api_secret"));

    public void validate(Map<String, String> config) { ... }
}
```

Each enum value carries its own required keys and validates any config map before
upload. This is called by every strategy at the start of `upload()` and `delete()`.

---

### `ImageHostingStrategy` (interface)

```java
public interface ImageHostingStrategy {
    ImageHostingProvider provider();
    ImageUploadResponse upload(MultipartFile file, Map<String, String> configs);
    void delete(String publicId, Map<String, String> config);
}
```

| Method | Description |
|---|---|
| `provider()` | Returns the enum key — used by the registry at startup to build the map |
| `upload(file, config)` | Validates config, uploads to storage, returns `imageUrl` + `publicId` |
| `delete(publicId, config)` | Validates config, deletes object from storage by public ID |

---

### `ImageUploadService` (interface)

```java
public interface ImageUploadService {
    ImageUploadResponse upload(MultipartFile file, ImageHostingProvider provider, Map<String, String> config);
    void delete(String publicId, ImageHostingProvider provider, Map<String, String> config);
    List<ImageRequest> uploadAll(List<MultipartFile> images, List<ImageMetaRequest> metaList,
                                 ImageHostingProvider provider, Map<String, String> config);
}
```

`uploadAll` is the primary batch method. It correlates files to metadata by
`ImageMetaRequest.clientImageId == file.getOriginalFilename()`, uploads each in
sequence, and rolls back all successful uploads if any one fails.

---

### `ImageMetaRequest` (DTO)

```java
public class ImageMetaRequest {
    private String clientImageId;   // must match file.getOriginalFilename()
    private String caption;
    private Boolean isDefault = false;
    private Integer sortOrder = 0;
}
```

Sent alongside the image files in a `multipart/form-data` request.
`clientImageId` is the correlation key used to match each metadata entry
to its corresponding file.

---

### `ImageRequest` (DTO)

```java
public class ImageRequest {
    private String imageUrl;
    private String publicId;
    private String caption;
    private Boolean isDefault;
    private Integer sortOrder;
}
```

Assembled by `uploadAll` for each file — combines storage result with caller metadata.
The controller maps each `ImageRequest` to a domain image entity before persisting.

---

## Error Reference

| Scenario | Layer | Exception | HTTP |
|---|---|---|---|
| `provider` null in request | Controller (`@Valid`) | `MethodArgumentNotValidException` | 400 |
| `config` null in request | Controller (`@Valid`) | `MethodArgumentNotValidException` | 400 |
| Required config key missing or blank | `ImageHostingProvider.validate()` | `IllegalArgumentException` | 400 |
| No metadata matching a file's name | `ImageUploadServiceImpl.uploadAll()` | `ResponseStatusException` | 400 |
| No strategy registered for provider | `ImageHostingStrategyRegistry.get()` | `IllegalArgumentException` | 400 |
| S3 upload SDK failure | `S3HostingStrategy` | `IllegalStateException` | 500 |
| Cloudinary upload SDK failure | `CloudinaryHostingStrategy` | `IllegalStateException` | 500 |
| S3 delete SDK failure | `S3HostingStrategy` | SDK exception | 500 |
| Cloudinary delete SDK failure | `CloudinaryHostingStrategy` | `IllegalStateException` | 500 |
| Partial batch failure | `ImageUploadServiceImpl` | rollback + re-throw | varies |
