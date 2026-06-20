# Restaurant Images API

Base URL: `/api/v1/restaurant-images`

All endpoints require authentication (JWT Bearer token).

---

## 1. Upload Images

Upload one or more images to the configured hosting provider and save their records.

**`POST /api/v1/restaurant-images`**

**Content-Type:** `multipart/form-data`

### Request Params & Parts

| Name        | Kind           | Type       | Required | Description                                            |
|-------------|----------------|------------|----------|--------------------------------------------------------|
| `config_id` | Query param    | Long       | Yes      | ID of the active `RestaurantImageHostingConfig` to use |
| `images`    | Multipart part | File[]     | Yes      | One or more image files                                |
| `meta`      | Multipart part | JSON Array | Yes      | Metadata for each image, matched by original filename  |

### `meta` Array Item Schema

```json
[
  {
    "client_image_id": "photo1.jpg",
    "caption": "Front entrance",
    "is_default": false,
    "sort_order": 1
  }
]
```

| Field             | Type    | Required | Description                                                      |
|-------------------|---------|----------|------------------------------------------------------------------|
| `client_image_id` | String  | Yes      | Must match the original filename of the corresponding image file |
| `caption`         | String  | No       | Image caption                                                    |
| `is_default`      | Boolean | No       | Default: `false`                                                 |
| `sort_order`      | Integer | No       | Default: `0`                                                     |

### Response — `201 Created`

```json
[
  {
    "id": 1,
    "config_id": 3,
    "external_id": "restaurant/photo1",
    "url": "https://res.cloudinary.com/demo/image/upload/restaurant/photo1.jpg",
    "caption": "Front entrance",
    "sort_order": 1
  }
]
```

### Error Responses

| Status            | Reason                                           |
|-------------------|--------------------------------------------------|
| `400 Bad Request` | No metadata found for an uploaded image filename |
| `404 Not Found`   | `config_id` does not exist or is inactive        |

---

## 2. Get All Images (Paginated)

**`GET /api/v1/restaurant-images`**

### Query Parameters

| Parameter  | Type    | Required | Default | Description                                |
|------------|---------|----------|---------|--------------------------------------------|
| `page`     | Integer | No       | `0`     | Page number (0-based)                      |
| `size`     | Integer | No       | `10`    | Page size                                  |
| `sort_by`  | String  | No       | `id`    | Sort field: `id`, `sortOrder`, `createdAt` |
| `sort_dir` | String  | No       | `asc`   | Sort direction: `asc`, `desc`              |

### Response — `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "external_id": "restaurant/photo1",
      "url": "https://res.cloudinary.com/demo/image/upload/restaurant/photo1.jpg",
      "caption": "Front entrance",
      "sort_order": 1
    }
  ],
  "page": 0,
  "size": 10,
  "total_elements": 1,
  "total_pages": 1
}
```

---

## 3. Get Image by ID

**`GET /api/v1/restaurant-images/{id}`**

### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `id`      | Long | Restaurant image ID |

### Response — `200 OK`

```json
{
  "restaurant_image": {
    "id": 1,
    "config_id": 3,
    "external_id": "restaurant/photo1",
    "url": "https://res.cloudinary.com/demo/image/upload/restaurant/photo1.jpg",
    "caption": "Front entrance",
    "sort_order": 1
  }
}
```

### Error Responses

| Status          | Reason                             |
|-----------------|------------------------------------|
| `404 Not Found` | Image not found or already deleted |

---

## 4. Update Image

Update the caption and/or sort order of an existing image. Does **not** re-upload the file.

**`PUT /api/v1/restaurant-images/{id}`**

**Content-Type:** `application/json`

### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `id`      | Long | Restaurant image ID |

### Request Body

```json
{
  "caption": "Updated caption",
  "sort_order": 2
}
```

| Field        | Type    | Required | Description   |
|--------------|---------|----------|---------------|
| `caption`    | String  | No       | Image caption |
| `sort_order` | Integer | Yes      | Display order |

### Response — `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

### Error Responses

| Status            | Reason                             |
|-------------------|------------------------------------|
| `400 Bad Request` | `sort_order` is null               |
| `404 Not Found`   | Image not found or already deleted |

---

## 5. Delete Image

Deletes the image from the hosting provider (Cloudinary / S3) and soft-deletes the record.

**`DELETE /api/v1/restaurant-images/{id}`**

### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `id`      | Long | Restaurant image ID |

### Response — `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

### Error Responses

| Status          | Reason                             |
|-----------------|------------------------------------|
| `404 Not Found` | Image not found or already deleted |

---

## Notes

- Images with a null `external_id` (e.g. externally sourced URLs) are soft-deleted only — no hosting provider call is
  made.
- The `config_id` on upload determines which hosting provider and credentials are used. Ensure the config is active
  before uploading.
- Soft-deleted images are excluded from all `GET` responses.
