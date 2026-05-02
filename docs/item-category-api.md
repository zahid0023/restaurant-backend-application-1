# Item Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Item Categories

### Create Item Category

Creates a new item category with optional embedded locales.

**`POST /api/v1/item-categories`**

#### Request Body

```json
{
  "item_type_id": 1,
  "code": "APPETIZER",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Appetizer",
      "description": "Starters and small dishes",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Başlangıç",
      "description": "Başlangıç yemekleri",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_type_id` | long | yes | must be an existing active item type |
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

### Get Item Category by ID

**`GET /api/v1/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item category ID |

#### Response `200 OK`

```json
{
  "item_category": {
    "id": 1,
    "item_type_id": 1,
    "code": "APPETIZER",
    "sort_order": 1
  }
}
```

---

### List Item Categories

Returns a paginated list of all active item categories.

**`GET /api/v1/item-categories`**

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
      "item_type_id": 1,
      "code": "APPETIZER",
      "sort_order": 1
    },
    {
      "id": 2,
      "item_type_id": 1,
      "code": "MAIN_COURSE",
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

### Update Item Category

Updates the fields of an existing item category. Locale translations are managed separately via the Item Category Locales API.

**`PUT /api/v1/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item category ID |

#### Request Body

```json
{
  "item_type_id": 1,
  "code": "MAIN_COURSE",
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_type_id` | long | yes | must be an existing active item type |
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

### Delete Item Category

Soft-deletes an item category (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Item category ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Item Category Locales

Manage locale-specific translations for an item category. The `{item-category-id}` in all paths must refer to an existing active item category.

---

### Create Item Category Locale

**`POST /api/v1/item-categories/{item-category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | Item category ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Appetizer",
  "description": "Starters and small dishes",
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

### Get Item Category Locale by ID

**`GET /api/v1/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | Item category ID |
| `id` | long | Item category locale ID |

#### Response `200 OK`

```json
{
  "item_category_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Appetizer",
    "description": "Starters and small dishes",
    "sort_order": 1
  }
}
```

---

### List Item Category Locales

Returns a paginated list of all active locales for a given item category.

**`GET /api/v1/item-categories/{item-category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | Item category ID |

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
      "name": "Appetizer",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Başlangıç",
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

### Update Item Category Locale

**`PUT /api/v1/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | Item category ID |
| `id` | long | Item category locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Appetizer",
  "description": "Starters, small bites and sharing plates",
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

### Delete Item Category Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | Item category ID |
| `id` | long | Item category locale ID |

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
  "message": "ItemCategory not found with id: 99"
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
    "item_type_id": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
