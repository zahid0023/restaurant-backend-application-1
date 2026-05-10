# Dining Space Type API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Dining Space Types

### Create Dining Space Type

Creates a new dining space type with optional embedded locale translations.

**`POST /api/v1/dining-space-types`**

#### Request Body

```json
{
  "code": "INDOOR",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Indoor",
      "description": "Indoor seating area",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "İç Mekan",
      "description": "İç mekan oturma alanı",
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

### Get Dining Space Type by ID

**`GET /api/v1/dining-space-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space type ID |

#### Response `200 OK`

```json
{
  "dining_space_type": {
    "id": 1,
    "code": "INDOOR",
    "sort_order": 1
  }
}
```

---

### List Dining Space Types

Returns a paginated list of all active dining space types.

**`GET /api/v1/dining-space-types`**

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
      "code": "INDOOR",
      "sort_order": 1
    },
    {
      "id": 2,
      "code": "OUTDOOR",
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

### Update Dining Space Type

Updates the fields of an existing dining space type. Locale translations are managed separately via the Dining Space Type Locales API.

**`PUT /api/v1/dining-space-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space type ID |

#### Request Body

```json
{
  "code": "INDOOR",
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

### Delete Dining Space Type

Soft-deletes a dining space type (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/dining-space-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space type ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dining Space Type Locales

Manage locale-specific translations for a dining space type. The `{dining-space-type-id}` in all paths must refer to an existing active dining space type.

---

### Create Dining Space Type Locale

**`POST /api/v1/dining-space-types/{dining-space-type-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-type-id` | long | Dining space type ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Indoor",
  "description": "Indoor seating area",
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

### Get Dining Space Type Locale by ID

**`GET /api/v1/dining-space-types/{dining-space-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-type-id` | long | Dining space type ID |
| `id` | long | Dining space type locale ID |

#### Response `200 OK`

```json
{
  "dining_space_type_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Indoor",
    "description": "Indoor seating area",
    "sort_order": 1
  }
}
```

---

### List Dining Space Type Locales

Returns a paginated list of all active locales for a given dining space type.

**`GET /api/v1/dining-space-types/{dining-space-type-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-type-id` | long | Dining space type ID |

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
      "name": "Indoor",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "İç Mekan",
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

### Update Dining Space Type Locale

**`PUT /api/v1/dining-space-types/{dining-space-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-type-id` | long | Dining space type ID |
| `id` | long | Dining space type locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Indoor",
  "description": "Indoor seating area - updated",
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

### Delete Dining Space Type Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/dining-space-types/{dining-space-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-type-id` | long | Dining space type ID |
| `id` | long | Dining space type locale ID |

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
  "message": "DiningSpaceType not found with id: 99"
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
