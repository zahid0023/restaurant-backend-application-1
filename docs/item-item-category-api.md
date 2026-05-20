# Item-Item Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Overview

The Item-Item Category API manages the many-to-many assignment relationship between items and item categories. An item can be assigned to multiple categories, and a category can contain multiple items. Assignments are soft-deleted on unassign.

---

### Assign Item to Category

Creates an assignment between an item and an item category. Returns an error if the item is already assigned to the given category.

**`POST /api/v1/item-item-categories`**

#### Request Body

```json
{
  "item_id": 3,
  "item_category_id": 2
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

> `id` is the ID of the newly created assignment record.

#### Error: Already Assigned `400 Bad Request`

```json
{
  "status": 400,
  "message": "PlatformItem 3 is already assigned to PlatformItemCategory 2"
}
```

---

### List Items in Category

Returns a paginated list of items assigned to the specified category.

**`GET /api/v1/item-item-categories/{item-category-id}/items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-category-id` | long | ID of the item category |

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
      "id": 3,
      "unit_id": 1,
      "sort_order": 1
    },
    {
      "id": 5,
      "unit_id": 2,
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

> Each entry represents an item assigned to the category. `id` is the item ID, not the assignment record ID.

---

### Unassign Item from Category

Soft-deletes the assignment between an item and a category (sets `is_active = false`).

**`DELETE /api/v1/item-item-categories/{item-id}/{item-category-id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `item-id` | long | ID of the item to unassign |
| `item-category-id` | long | ID of the item category |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

> `id` is the ID of the assignment record that was soft-deleted.

---

## Error Responses

### 404 Not Found

Returned when the item, item category, or assignment does not exist or has been soft-deleted.

```json
{
  "status": 404,
  "message": "PlatformItem 3 is not assigned to PlatformItemCategory 2"
}
```

### 400 Bad Request

Returned when request validation fails or the assignment already exists.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "item_id": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
