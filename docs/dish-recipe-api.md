# Dish Recipe API

Base URL: `/api/v1`

A dish recipe defines the ingredients for a dish variant. Each variant has exactly one active recipe. A recipe is created inline when creating a variant (see [Dish Variant API](dish-variant-api.md)) and can also be created, updated, or deleted independently via these endpoints. Ingredients are embedded in every recipe response. All records support soft-delete.

---

## Endpoints

| Method | Path                                                                                                                                  | Description                      |
|--------|---------------------------------------------------------------------------------------------------------------------------------------|----------------------------------|
| POST   | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`                          | Create a dish recipe             |
| GET    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`                     | Get a dish recipe                |
| GET    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`                          | List all dish recipes            |
| PUT    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`                     | Update a dish recipe             |
| DELETE | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`                     | Delete a dish recipe             |

---

## Data Model

### Dish Recipe

| Field            | Type   | Required | Constraints             | Description                              |
|------------------|--------|----------|-------------------------|------------------------------------------|
| `id`             | Long   | —        | read-only               | Auto-generated identifier                |
| `dish_variant_id`| Long   | —        | read-only               | ID of the parent dish variant            |
| `code`           | String | No       | max 50 chars            | Optional recipe code                     |
| `ingredients`    | Array  | —        | read-only               | All ingredients for this recipe          |

### Dish Recipe Ingredient

| Field            | Type       | Required | Constraints           | Description                              |
|------------------|------------|----------|-----------------------|------------------------------------------|
| `id`             | Long       | —        | read-only             | Auto-generated identifier                |
| `dish_recipe_id` | Long       | —        | read-only             | ID of the parent recipe                  |
| `item_id`        | Long       | Yes      | not null, must exist  | ID of the inventory item                 |
| `quantity`       | BigDecimal | No       | —                     | Amount of the item required              |
| `unit_id`        | Long       | No       | must exist if provided| ID of the unit of measurement            |

---

## Create Dish Recipe

`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`

Creates a new recipe for a dish variant. The `ingredients` array is required and must contain at least one entry. All provided `item_id` and `unit_id` values must reference existing active records.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |

### Request Body

```json
{
  "code": "BURGER_SMALL_V2",
  "ingredients": [
    {
      "item_id": 3,
      "quantity": 1.000,
      "unit_id": 1
    },
    {
      "item_id": 7,
      "quantity": 0.150
    }
  ]
}
```

### Request Fields

| Field                    | Type       | Required | Validation                    |
|--------------------------|------------|----------|-------------------------------|
| `code`                   | String     | No       | Max 50 chars                  |
| `ingredients`            | Array      | Yes      | Not empty, min 1 item         |
| `ingredients[].item_id`  | Long       | Yes      | Not null, must exist          |
| `ingredients[].quantity` | BigDecimal | No       | —                             |
| `ingredients[].unit_id`  | Long       | Yes      | Not null, must exist          |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Dish Recipe

`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`

Returns a single recipe with all its ingredients.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |
| `id`               | Long | ID of the dish recipe    |

### Response `200 OK`

```json
{
  "dish_recipe": {
    "id": 1,
    "dish_variant_id": 1,
    "code": "BURGER_SMALL_V2",
    "ingredients": [
      {
        "id": 1,
        "dish_recipe_id": 1,
        "item_id": 3,
        "quantity": 1.000,
        "unit_id": 1
      },
      {
        "id": 2,
        "dish_recipe_id": 1,
        "item_id": 7,
        "quantity": 0.150,
        "unit_id": null
      }
    ]
  }
}
```

---

## List All Dish Recipes

`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`

Returns a paginated list of active recipes for a dish variant.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |

### Query Parameters

| Parameter  | Type    | Default | Constraints                  | Description              |
|------------|---------|---------|------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                         | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                       | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `createdAt`    | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_variant_id": 1,
      "code": "BURGER_SMALL_V1"
    },
    {
      "id": 2,
      "dish_variant_id": 1,
      "code": "BURGER_SMALL_V2"
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

## Update Dish Recipe

`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`

Updates the `code` of an existing recipe. Ingredients are managed via their own controller (not yet implemented as standalone endpoints — create a new recipe to change ingredients).

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |
| `id`               | Long | ID of the dish recipe    |

### Request Body

```json
{
  "code": "BURGER_SMALL_V3"
}
```

### Request Fields

| Field  | Type   | Required | Validation   |
|--------|--------|----------|--------------|
| `code` | String | No       | Max 50 chars |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dish Recipe

`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`

Soft-deletes the dish recipe.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |
| `id`               | Long | ID of the dish recipe    |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Error Responses

All errors follow a common structure:

```json
{
  "request_id": "abc-123",
  "status": 404,
  "error": "ENTITY_NOT_FOUND",
  "message": "DishRecipe not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                              |
|-------------|----------------------------|--------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields, empty ingredients, or invalid sort field  |
| 404         | `ENTITY_NOT_FOUND`         | Recipe, variant, dish, item, or unit not found or already deleted  |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                         |
