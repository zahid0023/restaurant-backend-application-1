# Item Type API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Item Types

### Create Item Type

Creates a new item type with optional embedded locales.

**`POST /api/v1/item-types`**

#### Request Body

```json
{
  "code": "FOOD",
  "is_consumable": true,
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Food",
      "description": "Edible items",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Yiyecek",
      "description": "Yenilebilir ürünler",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `is_consumable` | boolean | yes | |
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

### Get Item Type by ID

**`GET /api/v1/item-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item type ID |

#### Response `200 OK`

```json
{
  "item_type": {
    "id": 1,
    "code": "FOOD",
    "is_consumable": true,
    "sort_order": 1
  }
}
```

---

### List Item Types

Returns a paginated list of all active item types.

**`GET /api/v1/item-types`**

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
      "code": "FOOD",
      "is_consumable": true,
      "sort_order": 1
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

---

### Update Item Type

Updates the fields of an existing item type. Locale translations are managed separately via the Item Type Locales API.

**`PUT /api/v1/item-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item type ID |

#### Request Body

```json
{
  "code": "BEVERAGE",
  "is_consumable": true,
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `is_consumable` | boolean | yes | |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Item Type

Soft-deletes an item type (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/item-types/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item type ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Item Type Locales

Manage locale-specific translations for an item type. The `{item-type-id}` in all paths must refer to an existing active item type.

---

### Create Item Type Locale

**`POST /api/v1/item-types/{item-type-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Food",
  "description": "Edible items",
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

### Get Item Type Locale by ID

**`GET /api/v1/item-types/{item-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `id` | long | Item type locale ID |

#### Response `200 OK`

```json
{
  "item_type_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Food",
    "description": "Edible items",
    "sort_order": 1
  }
}
```

---

### List Item Type Locales

Returns a paginated list of all active locales for a given item type.

**`GET /api/v1/item-types/{item-type-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |

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
      "name": "Food",
      "sort_order": 1
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

> Note: The list response returns a summary shape (`id`, `locale_id`, `name`, `sort_order`). Use the Get by ID endpoint to retrieve `description`.

---

### Update Item Type Locale

**`PUT /api/v1/item-types/{item-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `id` | long | Item type locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Food & Beverage",
  "description": "All edible and drinkable items",
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

### Delete Item Type Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/item-types/{item-type-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `id` | long | Item type locale ID |

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
  "message": "ItemType not found with id: 99"
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
