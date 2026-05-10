# Floor API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Floors

### Create Floor

Creates a new floor with optional embedded locale translations.

**`POST /api/v1/floors`**

#### Request Body

```json
{
  "code": "FLOOR_1",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "First Floor",
      "description": "Main dining area",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Birinci Kat",
      "description": "Ana yemek alanı",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |
| `locales` | array | no | see locale fields below |
| `locales[].locale_id` | long | yes | must be an existing active locale |
| `locales[].name` | string | yes | max 255 chars |
| `locales[].description` | string | no | defaults to `""` |
| `locales[].sort_order` | integer | yes | |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Floor by ID

**`GET /api/v1/floors/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Floor ID |

#### Response `200 OK`

```json
{
  "floor": {
    "id": 1,
    "code": "FLOOR_1",
    "sort_order": 1
  }
}
```

---

### List Floors

Returns a paginated list of all active floors.

**`GET /api/v1/floors`**

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "FLOOR_1",
      "sort_order": 1
    },
    {
      "id": 2,
      "code": "FLOOR_2",
      "sort_order": 2
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

### Update Floor

Updates the fields of an existing floor. Locale translations are managed separately via the Floor Locales API.

**`PUT /api/v1/floors/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Floor ID |

#### Request Body

```json
{
  "code": "FLOOR_1",
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Floor

Soft-deletes a floor (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/floors/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Floor ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Floor Locales

Manage locale-specific translations for a floor. The `{floor-id}` in all paths must refer to an existing active floor.

---

### Create Floor Locale

**`POST /api/v1/floors/{floor-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `floor-id` | long | Floor ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "First Floor",
  "description": "Main dining area",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
| `name` | string | yes | max 255 chars |
| `description` | string | no | defaults to `""` |
| `sort_order` | integer | yes | |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Floor Locale by ID

**`GET /api/v1/floors/{floor-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `floor-id` | long | Floor ID |
| `id` | long | Floor locale ID |

#### Response `200 OK`

```json
{
  "floor_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "First Floor",
    "description": "Main dining area",
    "sort_order": 1
  }
}
```

---

### List Floor Locales

Returns a paginated list of all active locales for a given floor.

**`GET /api/v1/floors/{floor-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `floor-id` | long | Floor ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `name`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "locale_id": 1,
      "name": "First Floor",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Birinci Kat",
      "sort_order": 1
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

> Note: The list response returns a summary shape (`id`, `locale_id`, `name`, `sort_order`). Use the Get by ID endpoint to retrieve `description`.

---

### Update Floor Locale

**`PUT /api/v1/floors/{floor-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `floor-id` | long | Floor ID |
| `id` | long | Floor locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "First Floor",
  "description": "Main dining area - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
| `name` | string | yes | max 255 chars |
| `description` | string | no | defaults to `""` |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Floor Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/floors/{floor-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `floor-id` | long | Floor ID |
| `id` | long | Floor locale ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Error Responses

### 404 Not Found

Returned when the requested resource does not exist or has been soft-deleted.

```json
{
  "status": 404,
  "message": "Floor not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "code": "must not be blank",
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
