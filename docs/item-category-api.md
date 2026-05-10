# Item Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Item Categories

Item categories are nested under item types. All paths include `{item-type-id}` which must refer to an existing active item type.

Categories support a parent–child hierarchy. A category can optionally have a `parent_id` pointing to another category within the same item type. Note that `parent_id` is accepted on creation but is **not returned** in any Get or List response.

---

### Create Item Category

Creates a new item category with optional parent and optional embedded locale translations.

**`POST /api/v1/item-types/{item-type-id}/item-categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |

#### Request Body

```json
{
  "parent_id": null,
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
| `parent_id` | long | no | must be an existing active item category within the same item type |
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

**`GET /api/v1/item-types/{item-type-id}/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

> Note: `parent_id` is not included in the response even if the category has a parent.

---

### List Item Categories

Returns a paginated list of all active item categories for the specified item type, including both root categories and their children in a flat list.

**`GET /api/v1/item-types/{item-type-id}/item-categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |

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

> Note: `parent_id` is not included in list items. All categories (roots and children) are returned in a single flat list.

---

### Update Item Category

Updates `code` and `sort_order` of an existing item category. `parent_id` is set only at creation and cannot be changed. Locale translations are managed separately via the Item Category Locales API.

**`PUT /api/v1/item-types/{item-type-id}/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `id` | long | Item category ID |

#### Request Body

```json
{
  "code": "MAIN_COURSE",
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

### Delete Item Category

Soft-deletes an item category (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/item-types/{item-type-id}/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

Manage locale-specific translations for an item category. Both `{item-type-id}` and `{item-category-id}` must refer to existing active records.

---

### Create Item Category Locale

**`POST /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

**`GET /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

**`GET /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

**`PUT /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

**`DELETE /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
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

## Item-Category Assignments

Assign and unassign items to/from an item category. Both `{item-type-id}` and `{item-category-id}` must refer to existing active records.

---

### Assign Item to Category

**`POST /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `item-category-id` | long | Item category ID |

#### Request Body

```json
{
  "item_id": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### List Items in Category

Returns a paginated list of item-category assignment records for the specified category.

**`GET /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `item-category-id` | long | Item category ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "item_id": 3,
      "item_category_id": 2
    },
    {
      "id": 2,
      "item_id": 5,
      "item_category_id": 2
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

### Unassign Item from Category

Soft-deletes the assignment between an item and a category.

**`DELETE /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/items/{item-id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `item-category-id` | long | Item category ID |
| `item-id` | long | Item ID to unassign |

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
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
