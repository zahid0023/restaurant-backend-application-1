# Menu API Documentation

Base URL: `/api/v1/menus/public`

All endpoints are **public** — no authentication required.

---

## Overview

The public menu API lets clients browse the menu in three steps:

```
Step 1 — GET /api/v1/menus/public/menu-types
         → returns all menu types (Lunch, Dinner, etc.)

Step 2 — GET /api/v1/menus/public/menu-types/{menu-type-id}
         → returns categories for the selected menu type (Starters, Mains, etc.)

Step 3 — GET /api/v1/menus/public/menu-categories/{menu-category-id}/dishes
         → returns dishes for the selected category (with variants and ingredients)
```

---

## Endpoints

| Method | Path                                                             | Description                     |
|--------|------------------------------------------------------------------|---------------------------------|
| GET    | `/api/v1/menus/public/menu-types`                                | List all menu types             |
| GET    | `/api/v1/menus/public/menu-types/{menu-type-id}`                 | List categories for a menu type |
| GET    | `/api/v1/menus/public/menu-categories/{menu-category-id}/dishes` | List dishes for a menu category |

---

## List Menu Types

Returns a paginated list of active menu types with their locale translations.

**`GET /api/v1/menus/public/menu-types`**

### Query Parameters

| Parameter  | Type    | Default | Constraints                    | Description             |
|------------|---------|---------|--------------------------------|-------------------------|
| `page`     | integer | `0`     | min 0                          | Page index (zero-based) |
| `size`     | integer | `10`    | 1–50                           | Items per page          |
| `sort_by`  | string  | `id`    | `id`, `sortOrder`, `createdAt` | Field to sort by        |
| `sort_dir` | string  | `ASC`   | `ASC`, `DESC`                  | Sort direction          |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "LUNCH",
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Lunch Menu",
          "description": "Available from 12:00 to 15:00",
          "sort_order": 1
        },
        {
          "id": 2,
          "locale_id": 2,
          "name": "দুপুরের মেনু",
          "description": "১২:০০ থেকে ১৫:০০ পর্যন্ত পরিবেশিত",
          "sort_order": 1
        }
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

---

## List Categories by Menu Type

Returns a paginated list of active menu categories belonging to the given menu type.

**`GET /api/v1/menus/public/menu-types/{menu-type-id}`**

### Path Parameters

| Parameter      | Type | Description  |
|----------------|------|--------------|
| `menu-type-id` | long | Menu type ID |

### Query Parameters

| Parameter  | Type    | Default | Constraints                    | Description             |
|------------|---------|---------|--------------------------------|-------------------------|
| `page`     | integer | `0`     | min 0                          | Page index (zero-based) |
| `size`     | integer | `10`    | 1–50                           | Items per page          |
| `sort_by`  | string  | `id`    | `id`, `sortOrder`, `createdAt` | Field to sort by        |
| `sort_dir` | string  | `ASC`   | `ASC`, `DESC`                  | Sort direction          |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "STARTERS",
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Starters",
          "description": "Light appetizers to begin your meal",
          "sort_order": 1
        }
      ]
    },
    {
      "id": 2,
      "code": "MAIN_COURSE",
      "sort_order": 2,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Main Course",
          "description": "Hearty main course dishes",
          "sort_order": 1
        }
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

## List Dishes by Menu Category

Returns a paginated list of dishes assigned to the given menu category. Each dish includes its locale translations,
variants, and variant ingredients.

**`GET /api/v1/menus/public/menu-categories/{menu-category-id}/dishes`**

### Path Parameters

| Parameter          | Type | Description      |
|--------------------|------|------------------|
| `menu-category-id` | long | Menu category ID |

### Query Parameters

| Parameter  | Type    | Default | Constraints                    | Description             |
|------------|---------|---------|--------------------------------|-------------------------|
| `page`     | integer | `0`     | min 0                          | Page index (zero-based) |
| `size`     | integer | `10`    | 1–50                           | Items per page          |
| `sort_by`  | string  | `id`    | `id`, `sortOrder`, `createdAt` | Field to sort by        |
| `sort_dir` | string  | `ASC`   | `ASC`, `DESC`                  | Sort direction          |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 11,
      "code": "SPRING_ROLLS",
      "sort_order": 1,
      "is_featured": false,
      "locales": [
        {
          "id": 21,
          "locale_id": 1,
          "name": "Spring Rolls",
          "description": "Crispy vegetable spring rolls",
          "sort_order": 1
        },
        {
          "id": 22,
          "locale_id": 2,
          "name": "স্প্রিং রোল",
          "description": "মুচমুচে সবজি স্প্রিং রোল",
          "sort_order": 1
        }
      ],
      "variants": [
        {
          "id": 5,
          "code": "REGULAR",
          "price": 4.99,
          "sort_order": 1,
          "locales": [
            {
              "locale_id": 1,
              "name": "Regular",
              "description": null,
              "sort_order": 1
            }
          ],
          "ingredients": [
            {
              "id": 1,
              "item_id": 3,
              "quantity": 100.0,
              "unit_id": 2
            }
          ]
        }
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

---

## Error Responses

### 404 Not Found

Returned when the requested resource does not exist or has been soft-deleted.

```json
{
  "status": 404,
  "message": "MenuType not found with id: 99"
}
```

### 400 Bad Request

Returned when query parameter validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "size": "must be between 1 and 50"
  }
}
```
