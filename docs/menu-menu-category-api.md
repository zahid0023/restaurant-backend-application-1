# Menu — Menu Category Assignment API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Overview

A menu category can be assigned to many menus, and a menu can have many menu categories. Use these endpoints to manage that many-to-many relationship.

---

### Assign Menu Category to Menu

**`POST /api/v1/menu-menu-categories`**

#### Request Body

```json
{
  "menu_id": 1,
  "menu_category_id": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `menu_id` | long | yes | must be an existing active menu |
| `menu_category_id` | long | yes | must be an existing active menu category; unique per menu |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### List Menu Categories for a Menu

Returns a paginated list of all menu categories assigned to a given menu.

**`GET /api/v1/menu-menu-categories/{menu-id}/menu-categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |

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
      "code": "STARTERS",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Starters", "description": "Cold and warm starters", "sort_order": 1 },
        { "id": 2, "locale_id": 2, "name": "Mezeler", "description": "Soğuk ve sıcak mezeler", "sort_order": 1 }
      ]
    },
    {
      "id": 3,
      "code": "MAIN_COURSE",
      "sort_order": 2,
      "locales": [
        { "id": 5, "locale_id": 1, "name": "Main Course", "description": "Main dishes", "sort_order": 1 }
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

### Unassign Menu Category from Menu

Soft-removes the assignment between a menu and a menu category.

**`DELETE /api/v1/menu-menu-categories/{menu-id}/{menu-category-id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |

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

Returned when the menu, menu category, or assignment does not exist or has been soft-deleted.

```json
{
  "status": 404,
  "message": "MenuCategory 2 is not assigned to Menu 1"
}
```

### 409 Conflict

Returned when a menu category is already assigned to the menu.

```json
{
  "status": 409,
  "message": "MenuCategory 2 is already assigned to Menu 1"
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
