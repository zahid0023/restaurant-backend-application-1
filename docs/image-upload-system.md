# Image Upload System — Architecture & Design

## Overview

The image upload system is a **reusable `imagehosting` layer** built on the
**Strategy Pattern**. Any domain controller calls a single service method,
passes the desired provider and a credentials map at runtime, and gets back
ready-to-persist `ImageRequest` objects — with zero knowledge of the
underlying SDK.

Two responsibilities are kept completely separate:

| Concern | Component | Description |
|---|---|---|
| **Storage** | `ImageUploadService` | Uploads files to the chosen provider, returns URL + publicId |
| **Persistence** | Domain controller/service | Maps `ImageRequest` results to domain image entities and persists them |

---

## Package Structure

```
imagehosting/
├── enums/
│   └── ImageHostingProvider.java            ← S3 | CLOUDINARY (each holds required keys + validate())
│
├── strategy/
│   ├── ImageHostingStrategy.java            ← Strategy interface: upload + delete
│   ├── CloudinaryHostingStrategy.java       ← Cloudinary implementation
│   ├── S3HostingStrategy.java               ← AWS S3 implementation
│   └── ImageHostingStrategyRegistry.java    ← Map<provider, strategy> (auto-collected at startup)
│
├── dto/
│   ├── request/
│   │   ├── ImageMetaRequest.java            ← clientImageId + caption + isDefault + sortOrder
│   │   ├── ImageRequest.java                ← assembled result: url + publicId + metadata
│   │   └── ImageStorageConfigRequest.java   ← provider + Map<String,String> config
│   └── response/
│       └── ImageUploadResponse.java         ← raw storage result: imageUrl + publicId
│
├── model/entity/
│   └── ImageEntity.java                     ← @MappedSuperclass: all image columns
│
├── service/
│   └── ImageUploadService.java              ← upload / delete / uploadAll
│
└── serviceImpl/
    └── ImageUploadServiceImpl.java          ← delegates to registry; uploadAll with rollback
```

---

## Strategy Pattern Design

### Why Strategy Pattern?

Without it, the service would contain a `switch` on `ImageHostingProvider` that must
be edited every time a new provider is added. With the Strategy Pattern:

- Each provider is an isolated `@Component` — self-contained, independently testable
- Adding a new provider (e.g. GCS, Imgur) requires **one new class only**
- `ImageUploadServiceImpl` never changes — it only talks to the registry

### Class Diagram

```
«interface»
ImageHostingStrategy
├── provider() : ImageHostingProvider
├── upload(MultipartFile, Map<String,String>) : ImageUploadResponse
└── delete(String publicId, Map<String,String>) : void
         ▲
         │
CloudinaryHostingStrategy          S3HostingStrategy
  @Component                         @Component
  builds Cloudinary per call         builds S3Client per call
  from config map                    from config map


ImageHostingStrategyRegistry
  Map<ImageHostingProvider, ImageHostingStrategy>
  ← Spring collects all ImageHostingStrategy @Component beans at startup
  get(provider) : ImageHostingStrategy


ImageUploadServiceImpl  implements  ImageUploadService
  upload(file, provider, config)   → registry.get(provider).upload(file, config)
  delete(publicId, provider, config) → registry.get(provider).delete(publicId, config)
  uploadAll(images, metaList, provider, config) → batch upload with rollback
```

### Spring Bean Auto-Collection

Spring injects `List<ImageHostingStrategy>` into the registry constructor.
Every `@Component` that implements `ImageHostingStrategy` is automatically included.
Both strategies (`S3HostingStrategy`, `CloudinaryHostingStrategy`) are always
registered — there are no `@ConditionalOnProperty` guards. Provider selection
happens at call time, not at startup.

---

## Component Reference

### `ImageHostingProvider` (enum)

```java
public enum ImageHostingProvider {
    S3(List.of("bucket", "region", "access_key", "secret_key")),
    CLOUDINARY(List.of("cloud_name", "api_key", "api_secret"));

    private final List<String> requiredKeys;

    public void validate(Map<String, String> config) {
        // throws IllegalArgumentException if any required key is missing or blank
    }
}
```

Each enum value carries its own required config keys. `validate()` is called by
every strategy at the start of `upload()` and `delete()` — before any SDK call.

---

### `ImageHostingStrategy` (interface)

```java
public interface ImageHostingStrategy {
    ImageHostingProvider provider();
    ImageUploadResponse upload(MultipartFile file, Map<String, String> config);
    void delete(String publicId, Map<String, String> config);
}
```

| Method | Description |
|---|---|
| `provider()` | Returns the enum key — used by the registry to build the lookup map |
| `upload(file, config)` | Validates config, uploads to storage, returns `imageUrl` + `publicId` |
| `delete(publicId, config)` | Validates config, removes object from storage by public ID |

---

### `CloudinaryHostingStrategy`

- **Always registered** as a `@Component` — no conditional activation
- **Config keys required:** `cloud_name`, `api_key`, `api_secret`
- **Upload logic:** builds a new `Cloudinary` client from config, calls `uploader().upload(bytes, emptyMap())`
- **Delete logic:** calls `uploader().destroy(publicId, emptyMap())`
- **Returns:** `secure_url` as `imageUrl`, `public_id` as `publicId`
- **Error:** wraps `IOException` in `IllegalStateException` (→ HTTP 500 via global handler)

---

### `S3HostingStrategy`

- **Always registered** as a `@Component` — no conditional activation
- **Config keys required:** `bucket`, `region`, `access_key`, `secret_key`
- **Upload logic:** builds a new `S3Client` (try-with-resources), generates `UUID-filename` key, calls `putObject`
- **Delete logic:** calls `deleteObject(bucket, publicId)`
- **URL format:** `https://{bucket}.s3.{region}.amazonaws.com/{key}`
- **Returns:** constructed URL as `imageUrl`, object key as `publicId`
- **Error:** wraps `IOException` in `IllegalStateException` (→ HTTP 500 via global handler)

---

### `ImageHostingStrategyRegistry`

```java
public ImageHostingStrategyRegistry(List<ImageHostingStrategy> strategies) {
    this.strategies = strategies.stream()
        .collect(Collectors.toUnmodifiableMap(ImageHostingStrategy::provider, s -> s));
    log.info("Registered image hosting strategies: {}", this.strategies.keySet());
}

public ImageHostingStrategy get(ImageHostingProvider provider) {
    ImageHostingStrategy strategy = strategies.get(provider);
    if (strategy == null) throw new IllegalArgumentException(
        "No strategy registered for provider: " + provider + ". Available: " + strategies.keySet());
    return strategy;
}
```

- Built once at startup from all available `ImageHostingStrategy` beans
- `get(provider)` is an **O(1) map lookup**
- Logs all registered providers at startup

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

| Method | Description |
|---|---|
| `upload` | Uploads a single file, returns raw `ImageUploadResponse` |
| `delete` | Removes a stored image by its `publicId` |
| `uploadAll` | Uploads a batch; assembles `ImageRequest` per file; **rolls back on partial failure** |

---

### `ImageUploadServiceImpl`

```java
// Single upload — delegates directly
public ImageUploadResponse upload(MultipartFile file, ImageHostingProvider provider,
                                  Map<String, String> config) {
    return registry.get(provider).upload(file, config);
}

// Batch upload — correlates by filename, rolls back on failure
public List<ImageRequest> uploadAll(List<MultipartFile> images, List<ImageMetaRequest> metaList,
                                    ImageHostingProvider provider, Map<String, String> config) {
    Map<String, ImageMetaRequest> metaMap = metaList.stream()
        .collect(toMap(ImageMetaRequest::getClientImageId, identity()));

    List<String> uploadedPublicIds = new ArrayList<>();
    try {
        return images.stream().map(image -> {
            ImageMetaRequest meta = metaMap.get(image.getOriginalFilename());
            if (meta == null) throw new ResponseStatusException(400, "No metadata for: " + image.getOriginalFilename());

            ImageUploadResponse response = upload(image, provider, config);
            uploadedPublicIds.add(response.getPublicId());

            return ImageRequest.builder()
                .imageUrl(response.getImageUrl()).publicId(response.getPublicId())
                .caption(meta.getCaption()).isDefault(meta.getIsDefault()).sortOrder(meta.getSortOrder())
                .build();
        }).toList();
    } catch (Exception ex) {
        uploadedPublicIds.forEach(id -> {
            try { delete(id, provider, config); }
            catch (Exception e) { log.error("Rollback delete failed for {}: {}", id, e.getMessage()); }
        });
        throw ex;
    }
}
```

---

### `ImageUploadResponse` (DTO)

```java
public class ImageUploadResponse {
    private String imageUrl;   // CDN/S3 URL of the stored file
    private String publicId;   // Provider asset identifier (used for deletion/transforms)
}
```

Raw result from storage. The `uploadAll` method combines this with
metadata from `ImageMetaRequest` to produce `ImageRequest`.

---

### `ImageMetaRequest` (DTO)

```java
public class ImageMetaRequest {
    private String clientImageId;   // must equal file.getOriginalFilename() for correlation
    private String caption;
    private Boolean isDefault = false;
    private Integer sortOrder = 0;
}
```

Sent alongside binary files in a `multipart/form-data` request.
`clientImageId` is the correlation key — it must exactly match the
`originalFilename` of the corresponding `MultipartFile`.

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

Assembled by `uploadAll` — combines storage result with caller metadata.
The controller maps each `ImageRequest` to a domain image entity before persisting.

---

### `ImageEntity` (`@MappedSuperclass`)

```java
@MappedSuperclass
public abstract class ImageEntity extends AuditableEntity {
    private String imageUrl;
    private String publicId;
    private String caption;
    private Boolean isDefault = false;
    private Integer sortOrder = 0;
}
```

Extended by domain-specific image entities (e.g. `RestaurantBasicInfoImageEntity`).
Each domain entity adds a `@ManyToOne` FK to its parent.

---

## Sequence Diagrams

### Single Upload

```
Controller      ImageUploadService       Registry           Strategy          Storage
    │                  │                    │                   │                │
    │─ upload(file, S3, config) ──────────►│                   │                │
    │                  │─ get(S3) ─────────►│                   │                │
    │                  │◄─ S3HostingStrategy ──────────────────│                │
    │                  │─ strategy.upload(file, config) ───────►│                │
    │                  │                    │  validate(config) │                │
    │                  │                    │  build S3Client   │                │
    │                  │                    │                   │─ putObject() ─►│
    │                  │                    │                   │◄─ success      │
    │                  │◄── ImageUploadResponse ───────────────────────────────  │
    │◄─ ImageUploadResponse ────────────────│                   │                │
```

### Batch Upload with Rollback

```
uploadAll(images, metaList, provider, config)
    │
    ├── build metaMap: { filename → ImageMetaRequest }
    │
    ├── for each file:
    │       meta = metaMap.get(file.originalFilename)
    │       if null → 400
    │       upload(file, provider, config)
    │       uploadedPublicIds.add(publicId)
    │       build ImageRequest
    │
    ├── SUCCESS → return List<ImageRequest>
    │
    └── FAILURE (any upload throws):
            for each id in uploadedPublicIds:
                delete(id, provider, config)   ← rollback
            re-throw exception
```

### Error Paths

| Scenario | Thrown By | Exception | HTTP |
|---|---|---|---|
| Provider not in registry | `Registry.get()` | `IllegalArgumentException` | 400 |
| Required config key missing | `provider().validate()` | `IllegalArgumentException` | 400 |
| No metadata for filename | `ImageUploadServiceImpl.uploadAll()` | `ResponseStatusException` | 400 |
| Cloudinary SDK failure | `CloudinaryHostingStrategy` | `IllegalStateException` | 500 |
| S3 SDK failure | `S3HostingStrategy` | `IllegalStateException` | 500 |
| DB constraint violation | `repository.saveAll()` | `DataIntegrityViolationException` | 409 |

---

## Full Request Flow (Restaurant Example)

```
POST /api/v1/restaurant-basic-info/{id}/images
Content-Type: multipart/form-data

images:  [ <file: menu.jpg>, <file: exterior.jpg> ]
meta:    [
  { "client_image_id": "menu.jpg",     "caption": "Our menu", "is_default": true,  "sort_order": 0 },
  { "client_image_id": "exterior.jpg", "caption": "Outside",  "is_default": false, "sort_order": 1 }
]
provider: S3
config:  { "bucket": "restaurant-img", "region": "us-east-1", "access_key": "...", "secret_key": "..." }

        │
        ▼  Controller calls:
imageUploadService.uploadAll(images, metaList, S3, config)
        │
        ├── upload(menu.jpg, S3, config)    → ImageUploadResponse { imageUrl, publicId }
        ├── upload(exterior.jpg, S3, config) → ImageUploadResponse { imageUrl, publicId }
        └── return List<ImageRequest> (2 items)
        │
        ▼  Controller maps to domain entities:
RestaurantBasicInfoImageEntity[] { restaurantBasicInfo, imageUrl, publicId, caption, isDefault, sortOrder }
        │
        ▼  Controller persists:
restaurantBasicInfoImageRepository.saveAll(entities)

Response: 201 Created
```

---

## Adding a New Provider

Only one new class is needed — no existing files change.

**Step 1 — Add enum value:**
```java
public enum ImageHostingProvider {
    S3(List.of("bucket", "region", "access_key", "secret_key")),
    CLOUDINARY(List.of("cloud_name", "api_key", "api_secret")),
    GCS(List.of("bucket", "project_id", "credentials_json"))   // ← add here
}
```

**Step 2 — Implement the strategy:**
```java
@Component
@Slf4j
public class GcsHostingStrategy implements ImageHostingStrategy {

    @Override
    public ImageHostingProvider provider() {
        return ImageHostingProvider.GCS;
    }

    @Override
    public ImageUploadResponse upload(MultipartFile file, Map<String, String> config) {
        provider().validate(config);
        // GCS upload logic using config values ...
    }

    @Override
    public void delete(String publicId, Map<String, String> config) {
        provider().validate(config);
        // GCS delete logic ...
    }
}
```

`ImageHostingStrategyRegistry`, `ImageUploadServiceImpl`, and all existing strategies
remain completely unchanged.

---

## Adding Images to a New Domain

Four files, no `imagehosting` changes needed.

| File | Responsibility |
|---|---|
| `XxxImageEntity extends ImageEntity` | `@Entity` + `@ManyToOne` FK to parent entity |
| `XxxImageRepository extends JpaRepository<XxxImageEntity, Long>` | Spring Data repo |
| Controller upload endpoint | calls `uploadAll()` → maps `ImageRequest` list → saves entities |

### Controller Pattern

```java
@PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> uploadImages(
        @PathVariable Long id,
        @RequestPart List<MultipartFile> images,
        @RequestPart List<ImageMetaRequest> meta,
        @RequestParam ImageHostingProvider provider,
        @RequestParam Map<String, String> config) {

    RestaurantBasicInfoEntity restaurant = restaurantBasicInfoService.getEntityById(id);

    // 1. upload all files — rollback handled automatically on failure
    List<ImageRequest> imageRequests = imageUploadService.uploadAll(images, meta, provider, config);

    // 2. map to domain entities
    List<RestaurantBasicInfoImageEntity> entities = imageRequests.stream()
        .map(req -> {
            RestaurantBasicInfoImageEntity entity = new RestaurantBasicInfoImageEntity();
            entity.setRestaurantBasicInfoEntity(restaurant);
            entity.setImageUrl(req.getImageUrl());
            entity.setPublicId(req.getPublicId());
            entity.setCaption(req.getCaption());
            entity.setIsDefault(req.getIsDefault());
            entity.setSortOrder(req.getSortOrder());
            return entity;
        })
        .toList();

    // 3. persist + return
    restaurantBasicInfoImageRepository.saveAll(entities);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(new SuccessResponse(true, restaurant.getId()));
}
```
