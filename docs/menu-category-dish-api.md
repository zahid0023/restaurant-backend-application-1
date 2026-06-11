# Menu Category Dish API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Overview

A dish can be assigned to one or more menu categories. A menu category can have one or more dishes. This API manages those assignments. Assignments are soft-deleted on unassign — the dish remains in the system but is no longer active in that category.

---

## Endpoints

| Method | Path                                                                              | Description                              |
|--------|-----------------------------------------------------------------------------------|------------------------------------------|
| POST   | `/api/v1/menu-categories/{menu-category-id}/dishes/assign`                        | Assign a dish to a menu category         |
| DELETE | `/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/unassign`            | Unassign a dish from a menu category     |

---

## Assign Dish

Assigns an existing active dish to an existing active menu category. A dish can only be assigned once per category — attempting to assign the same dish again returns an error.

**`POST /api/v1/menu-categories/{menu-category-id}/dishes/assign`**

### Path Parameters

| Parameter          | Type | Description          |
|--------------------|------|----------------------|
| `menu-category-id` | long | Menu category ID     |

### Request Body

```json
{
  "dish_id": 3
}
```

| Field     | Type | Required | Constraints                          |
|-----------|------|----------|--------------------------------------|
| `dish_id` | long | yes      | must be an existing active dish      |

### Response `201 Created`

```json
{
  "success": true,
  "id": 10
}
```

> `id` is the ID of the assignment record (`menu_category_dishes.id`).

---

## Unassign Dish

Soft-deletes the assignment between a menu category and a dish. The dish itself is not deleted — only the assignment is deactivated.

**`DELETE /api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/unassign`**

### Path Parameters

| Parameter          | Type | Description                           |
|--------------------|------|---------------------------------------|
| `menu-category-id` | long | Menu category ID                      |
| `dish-id`          | long | ID of the dish to unassign            |

### Response `200 OK`

```json
{
  "success": true,
  "id": 10
}
```

> `id` is the ID of the assignment record that was soft-deleted.

---

## Error Responses

### 404 Not Found

Returned when the menu category or dish does not exist, is already deleted, or the dish is not currently assigned to the category.

```json
{
  "status": 404,
  "message": "Dish 3 is not assigned to MenuCategory 1"
}
```

### 400 Bad Request

Returned when request validation fails (e.g. `dish_id` is null).

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "dish_id": "must not be null"
  }
}
```

### 409 Conflict / 500 Internal Server Error

Returned when attempting to assign a dish that is already assigned to the category.

```json
{
  "status": 500,
  "message": "Dish 3 is already assigned to MenuCategory 1"
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
