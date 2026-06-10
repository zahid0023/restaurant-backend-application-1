# Item API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Items

### Create Item

Creates a new item with optional embedded locale translations.

**`POST /api/v1/items`**

#### Request Body

```json 
{
  "item_type_id": 1,
  "unit_type_id": 2,
  "code": "TOMATO",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Tomato",
      "description": "Fresh red tomato",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "টমেটো",
      "description": "তাজা লাল টমেটো",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_type_id` | long | yes | must be an existing active item type |
| `unit_type_id` | long | yes | must be an existing active unit type |
| `code` | string | yes | max 20 chars, immutable after creation |
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

### Get Item by ID

**`GET /api/v1/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item ID |

#### Response `200 OK`

```json
{
  "item": {
    "id": 1,
    "code": "TOMATO",
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Tomato",
        "description": "Fresh red tomato",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "টমেটো",
        "description": "তাজা লাল টমেটো",
        "sort_order": 1
      }
    ]
  }
}
```

---

### List Items

Returns a paginated list of all active items. Optionally filter by a search query matched against item `code` or any locale `name` (case-insensitive).

**`GET /api/v1/items`**

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `query` | string | — | optional | Search term matched against `code` or any locale `name` (case-insensitive, partial match) |
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Examples

```
GET /api/v1/items                     → all items (paginated)
GET /api/v1/items?query=tomato        → items whose code or any locale name contains "tomato"
GET /api/v1/items?query=TOM&size=5   → same, with custom page size
```

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "TOMATO",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_code": "en", "name": "Tomato", "description": "Fresh red tomato", "sort_order": 1 },
        { "id": 2, "locale_code": "bn", "name": "টমেটো", "description": "তাজা লাল টমেটো", "sort_order": 1 }
      ]
    }
  ],
  "current_page": 0,
  "total_pages": 1,
  "total_elements": 1,
  "page_size": 10,
  "has_next": false,
  "has_previous": false
}
```

> Note: The list response returns a summary projection. Item locales use `locale_code`. Use the Get by ID endpoint to retrieve full locale detail with `locale_id`.

---

### Update Item

Updates mutable fields of an existing item. `code`, `item_type_id`, and `unit_type_id` are immutable and cannot be changed. Locale translations are managed separately via the Item Locales API.

**`PUT /api/v1/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item ID |

#### Request Body

```json
{
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Item

Soft-deletes an item (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Item Locales

Manage locale-specific translations for an item. The `{item-id}` in all paths must refer to an existing active item.

---

### Create Item Locale

**`POST /api/v1/items/{item-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-id` | long | Item ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Tomato",
  "description": "Fresh red tomato",
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

### Update Item Locale

Updates the translation fields of an existing item locale. The locale assignment (`locale_id`) cannot be changed.

**`PUT /api/v1/items/{item-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-id` | long | Item ID |
| `id` | long | Item locale ID |

#### Request Body

```json
{
  "name": "Tomato",
  "description": "Fresh ripe tomato",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
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

### Delete Item Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/items/{item-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-id` | long | Item ID |
| `id` | long | Item locale ID |

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
  "message": "Item not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "item_type_id": "must not be null",
    "unit_type_id": "must not be null",
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
