# Restaurant Image Hosting Config API

Base URL: `/api/v1/restaurant-image-hosting-configs`

Manages the restaurant's image storage provider configuration. Each config record stores a provider (`S3` or
`CLOUDINARY`) and the corresponding credentials as a key-value map. The system validates that all required keys for the
chosen provider are present and non-blank on create.

All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                                 | Description                         |
|--------|------------------------------------------------------|-------------------------------------|
| POST   | `/api/v1/restaurant-image-hosting-configs`           | Create a config                     |
| GET    | `/api/v1/restaurant-image-hosting-configs`           | List all configs (paginated)        |
| GET    | `/api/v1/restaurant-image-hosting-configs/{id}`      | Get a config by ID                  |
| GET    | `/api/v1/restaurant-image-hosting-configs/providers` | List all available providers        |
| DELETE | `/api/v1/restaurant-image-hosting-configs/{id}`      | Soft-delete a config                |

---

## Authentication

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Data Model

### RestaurantImageHostingConfig

| Field      | Type                     | Required | Description                                                             |
|------------|--------------------------|----------|-------------------------------------------------------------------------|
| `id`       | Long                     | —        | Auto-generated identifier (read-only)                                   |
| `provider` | String (enum)            | Yes      | Storage provider: `S3` or `CLOUDINARY`                                  |
| `config`   | Object (String → String) | Yes      | Provider credentials map — required keys depend on provider (see below) |

### Provider Config Keys

**`S3`** — required keys:

| Key          | Description           | Example                |
|--------------|-----------------------|------------------------|
| `bucket`     | S3 bucket name        | `restaurant-images`    |
| `region`     | AWS region code       | `us-east-1`            |
| `access_key` | IAM access key ID     | `AKIAIOSFODNN7EXAMPLE` |
| `secret_key` | IAM secret access key | `wJalrXUtnFEMI/...`    |

**`CLOUDINARY`** — required keys:

| Key          | Description                 | Example               |
|--------------|-----------------------------|-----------------------|
| `cloud_name` | Cloudinary cloud identifier | `my-restaurant-cloud` |
| `api_key`    | Cloudinary API key          | `123456789012345`     |
| `api_secret` | Cloudinary API secret       | `abcDEFghiJKLmnoPQR`  |

---

## Create Config

`POST /api/v1/restaurant-image-hosting-configs`

Creates a new image hosting config. The `config` map is validated against the required keys for the chosen `provider`
before saving.

### Request Body

**S3 example:**

```json
{
  "provider": "S3",
  "config": {
    "bucket": "restaurant-images",
    "region": "us-east-1",
    "access_key": "AKIAIOSFODNN7EXAMPLE",
    "secret_key": "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
  }
}
```

**Cloudinary example:**

```json
{
  "provider": "CLOUDINARY",
  "config": {
    "cloud_name": "my-restaurant-cloud",
    "api_key": "123456789012345",
    "api_secret": "abcDEFghiJKLmnoPQR"
  }
}
```

### Request Fields

| Field      | Type          | Required | Validation                                                                  |
|------------|---------------|----------|-----------------------------------------------------------------------------|
| `provider` | String (enum) | Yes      | Not null — `S3` or `CLOUDINARY`                                             |
| `config`   | Object        | Yes      | Not null — all required keys for the provider must be present and non-blank |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Config

`GET /api/v1/restaurant-image-hosting-configs/{id}`

Returns a single config by ID.

### Path Parameters

| Parameter | Type | Description      |
|-----------|------|------------------|
| `id`      | Long | ID of the config |

### Response `200 OK`

```json
{
  "restaurant_image_hosting_config": {
    "id": 1,
    "provider": "S3",
    "config": {
      "bucket": "restaurant-images",
      "region": "us-east-1",
      "access_key": "AKIAIOSFODNN7EXAMPLE",
      "secret_key": "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
    }
  }
}
```

---

## List All Configs

`GET /api/v1/restaurant-image-hosting-configs`

Returns a paginated list of all active (non-deleted) configs.

### Query Parameters

| Parameter  | Type   | Default | Constraints                   | Description              |
|------------|--------|---------|-------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                          | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                        | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `provider`, `createdAt` | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                 | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "provider": "S3",
      "config": {
        "bucket": "restaurant-images",
        "region": "us-east-1",
        "access_key": "AKIAIOSFODNN7EXAMPLE",
        "secret_key": "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
      }
    },
    {
      "id": 2,
      "provider": "CLOUDINARY",
      "config": {
        "cloud_name": "my-restaurant-cloud",
        "api_key": "123456789012345",
        "api_secret": "abcDEFghiJKLmnoPQR"
      }
    }
  ],
  "current_page": 0,
  "total_pages": 1,
  "total_elements": 2,
  "page_size": 10,
  "has_next": false,
  "has_previous": false
}
```

---

## List Providers

`GET /api/v1/restaurant-image-hosting-configs/providers`

Returns all supported image hosting providers and their required config keys. Use this endpoint to discover which
providers are available and what credentials are needed before creating or updating a config.

### Response `200 OK`

```json
[
  {
    "provider": "S3",
    "label": "Amazon S3",
    "required_keys": [
      { "key": "bucket",     "label": "Bucket Name" },
      { "key": "region",     "label": "AWS Region" },
      { "key": "access_key", "label": "Access Key ID" },
      { "key": "secret_key", "label": "Secret Access Key" }
    ]
  },
  {
    "provider": "CLOUDINARY",
    "label": "Cloudinary",
    "required_keys": [
      { "key": "cloud_name", "label": "Cloud Name" },
      { "key": "api_key",    "label": "API Key" },
      { "key": "api_secret", "label": "API Secret" }
    ]
  }
]
```

---

## Delete Config

`DELETE /api/v1/restaurant-image-hosting-configs/{id}`

Soft-deletes the config. The record is not removed from the database but will no longer appear in any response,
including `GET /active`.

### Path Parameters

| Parameter | Type | Description      |
|-----------|------|------------------|
| `id`      | Long | ID of the config |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Error Responses

All errors follow a common structure:

```json
{
  "request_id": "abc-123",
  "status": 400,
  "error": "INVALID_ARGUMENT",
  "message": "Missing or blank required config keys for provider S3: [access_key, secret_key]. Required keys: [bucket, region, access_key, secret_key]"
}
```

| HTTP Status | Error Code                 | Cause                                                                           |
|-------------|----------------------------|---------------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | `provider` is null, `config` is null, or required config keys are missing/blank |
| 400         | `INVALID_ARGUMENT`         | Invalid `provider` value (not `S3` or `CLOUDINARY`)                             |
| 400         | `INVALID_ARGUMENT`         | Invalid pagination `sort_by` field                                              |
| 401         | `UNAUTHORIZED`             | JWT token is missing or invalid                                                 |
| 403         | `FORBIDDEN`                | Authenticated user lacks permission                                             |
| 404         | `ENTITY_NOT_FOUND`         | Config not found or already deleted                                             |
| 409         | `DATA_INTEGRITY_VIOLATION` | Optimistic lock conflict on concurrent update (`@Version`)                      |
