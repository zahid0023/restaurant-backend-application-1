# Item Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Item Categories

Item categories are nested under item types. All paths include `{item-type-id}` which must refer to an existing active item type.

Categories support a parent–child hierarchy. A category can optionally have a `parent_id` pointing to another category within the same item type. `code` is immutable after creation.

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
  "code": "APPETIZER",
  "sort_order": 1,
  "parent_id": null,
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
| `code` | string | yes | max 50 chars, immutable after creation |
| `sort_order` | integer | yes | |
| `parent_id` | long | no | must be an existing active item category within the same item type |
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

Returns the full category including locale translations, immediate sub-categories, and assigned items.

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
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Appetizer",
        "description": "Starters and small dishes",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "Başlangıç",
        "description": "Başlangıç yemekleri",
        "sort_order": 1
      }
    ],
    "sub_categories": [
      {
        "id": 3,
        "item_type_id": 1,
        "code": "COLD_APPETIZER",
        "sort_order": 1,
        "locales": [
          {
            "id": 5,
            "locale_id": 1,
            "name": "Cold Appetizer",
            "description": "Chilled starters",
            "sort_order": 1
          }
        ]
      }
    ],
    "items": [
      {
        "id": 1,
        "item_type_id": 2,
        "unit_id": 1,
        "sort_order": 1,
        "locales": [
          {
            "id": 1,
            "locale_id": 1,
            "name": "Tomato",
            "description": "Fresh red tomato",
            "sort_order": 1
          }
        ]
      }
    ]
  }
}
```

> `sub_categories` contains only immediate children (one level deep). `items` lists all items assigned to this category.

---

### List Item Categories

Returns a paginated flat list of all active item categories for the specified item type (roots and children together).

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
      "parent_id": null,
      "code": "APPETIZER",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Appetizer", "description": "Starters and small dishes", "sort_order": 1 }
      ]
    },
    {
      "id": 3,
      "item_type_id": 1,
      "parent_id": 1,
      "code": "COLD_APPETIZER",
      "sort_order": 1,
      "locales": [
        { "id": 5, "locale_id": 1, "name": "Cold Appetizer", "description": "Chilled starters", "sort_order": 1 }
      ]
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

### List Root Item Categories

Returns a paginated list of only top-level (parentless) active item categories for the specified item type.

**`GET /api/v1/item-types/{item-type-id}/item-categories/root`**

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

Same shape as [List Item Categories](#list-item-categories). All entries will have `parent_id: null`.

---

### List Sub-Categories

Returns a paginated list of direct children of a given item category.

**`GET /api/v1/item-types/{item-type-id}/item-categories/{item-category-id}/sub-categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `item-category-id` | long | Parent item category ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

Same shape as [List Item Categories](#list-item-categories). All entries will have `parent_id` equal to `{item-category-id}`.

---

### Update Item Category

Updates `sort_order` of an existing item category. `code` and `parent_id` are immutable after creation. Locale translations are managed separately via the Item Category Locales API.

**`PUT /api/v1/item-types/{item-type-id}/item-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-type-id` | long | Item type ID |
| `id` | long | Item category ID |

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

### Update Item Category Locale

Updates the translation fields of an existing locale. `locale_id` is immutable after creation.

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
  "name": "Appetizer",
  "description": "Starters, small bites and sharing plates",
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

Assign and unassign items to/from item categories.

---

### Assign Item to Category

**`POST /api/v1/item-item-categories`**

#### Request Body

```json
{
  "item_id": 3,
  "item_category_id": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |
| `item_category_id` | long | yes | must be an existing active item category |

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

**`GET /api/v1/item-item-categories/{item-category-id}/items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
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
      "item_category_id": 1
    },
    {
      "id": 2,
      "item_id": 5,
      "item_category_id": 1
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

**`DELETE /api/v1/item-item-categories/{item-id}/{item-category-id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-id` | long | Item ID to unassign |
| `item-category-id` | long | Item category ID |

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
