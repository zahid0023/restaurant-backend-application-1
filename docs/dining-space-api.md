# Dining Space API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Dining Spaces

### Create Dining Space

Creates a new dining space with optional embedded locale translations.

**`POST /api/v1/dining-spaces`**

#### Request Body

```json
{
  "dining_space_type_id": 1,
  "floor_id": 2,
  "code": "TABLE_A1",
  "sort_order": 1,
  "capacity": 4,
  "is_bookable": true,
  "locales": [
    {
      "locale_id": 1,
      "name": "Table A1",
      "description": "Window-side table on the first floor",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Masa A1",
      "description": "Birinci katta pencere kenarı masa",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `dining_space_type_id` | long | yes | must be an existing active dining space type |
| `floor_id` | long | no | must be an existing active floor if provided |
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |
| `capacity` | integer | yes | |
| `is_bookable` | boolean | yes | |
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

### Get Dining Space by ID

**`GET /api/v1/dining-spaces/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space ID |

#### Response `200 OK`

```json
{
  "dining_space": {
    "id": 1,
    "dining_space_type_id": 1,
    "floor_id": 2,
    "code": "TABLE_A1",
    "sort_order": 1,
    "capacity": 4,
    "is_bookable": true
  }
}
```

> Note: `floor_id` is `null` when no floor is assigned to the dining space.

---

### List Dining Spaces

Returns a paginated list of all active dining spaces.

**`GET /api/v1/dining-spaces`**

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
      "dining_space_type_id": 1,
      "floor_id": 2,
      "code": "TABLE_A1",
      "sort_order": 1,
      "capacity": 4,
      "is_bookable": true
    },
    {
      "id": 2,
      "dining_space_type_id": 1,
      "floor_id": null,
      "code": "TABLE_A2",
      "sort_order": 2,
      "capacity": 2,
      "is_bookable": false
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

### Update Dining Space

Updates the fields of an existing dining space. Locale translations are managed separately via the Dining Space Locales API.

**`PUT /api/v1/dining-spaces/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space ID |

#### Request Body

```json
{
  "dining_space_type_id": 1,
  "floor_id": 3,
  "code": "TABLE_A1",
  "sort_order": 1,
  "capacity": 6,
  "is_bookable": true
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `dining_space_type_id` | long | yes | must be an existing active dining space type |
| `floor_id` | long | no | must be an existing active floor if provided; send `null` to unassign |
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |
| `capacity` | integer | yes | |
| `is_bookable` | boolean | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Dining Space

Soft-deletes a dining space (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/dining-spaces/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Dining space ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dining Space Locales

Manage locale-specific translations for a dining space. The `{dining-space-id}` in all paths must refer to an existing active dining space.

---

### Create Dining Space Locale

**`POST /api/v1/dining-spaces/{dining-space-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-id` | long | Dining space ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Table A1",
  "description": "Window-side table on the first floor",
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

### Get Dining Space Locale by ID

**`GET /api/v1/dining-spaces/{dining-space-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-id` | long | Dining space ID |
| `id` | long | Dining space locale ID |

#### Response `200 OK`

```json
{
  "dining_space_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Table A1",
    "description": "Window-side table on the first floor",
    "sort_order": 1
  }
}
```

---

### List Dining Space Locales

Returns a paginated list of all active locales for a given dining space.

**`GET /api/v1/dining-spaces/{dining-space-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-id` | long | Dining space ID |

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
      "name": "Table A1",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Masa A1",
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

### Update Dining Space Locale

**`PUT /api/v1/dining-spaces/{dining-space-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-id` | long | Dining space ID |
| `id` | long | Dining space locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Table A1",
  "description": "Window-side table on the first floor - updated",
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

### Delete Dining Space Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/dining-spaces/{dining-space-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `dining-space-id` | long | Dining space ID |
| `id` | long | Dining space locale ID |

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
  "message": "DiningSpace not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "dining_space_type_id": "must not be null",
    "code": "must not be blank",
    "capacity": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
