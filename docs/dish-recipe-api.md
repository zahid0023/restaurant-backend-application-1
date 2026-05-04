# Dish Recipe API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Dish Recipes

A dish recipe belongs to a dish variant. All path ancestors (`{menu-id}`, `{menu-category-id}`, `{dish-id}`, `{variant-id}`) must refer to existing active records.

---

### Create Dish Recipe

**`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |

#### Request Body

```json
{
  "code": "RECIPE_A"
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | no | max 50 chars |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Dish Recipe by ID

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish recipe ID |

#### Response `200 OK`

```json
{
  "dish_recipe": {
    "id": 1,
    "dish_variant_id": 1,
    "code": "RECIPE_A"
  }
}
```

---

### List Dish Recipes

Returns a paginated list of all active recipes for a given dish variant.

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `code`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_variant_id": 1,
      "code": "RECIPE_A"
    },
    {
      "id": 2,
      "dish_variant_id": 1,
      "code": "RECIPE_B"
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

### Update Dish Recipe

**`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish recipe ID |

#### Request Body

```json
{
  "code": "RECIPE_A_V2"
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | no | max 50 chars |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Dish Recipe

Soft-deletes a dish recipe (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish recipe ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Recipe Ingredients

A dish recipe ingredient links a recipe to an inventory item with an optional quantity. All path ancestors must refer to existing active records.

---

### Create Dish Recipe Ingredient

**`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `recipe-id` | long | Dish recipe ID |

#### Request Body

```json
{
  "item_id": 5,
  "quantity": 0.250
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |
| `quantity` | decimal | no | precision 18, scale 6 |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Dish Recipe Ingredient by ID

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `recipe-id` | long | Dish recipe ID |
| `id` | long | Ingredient ID |

#### Response `200 OK`

```json
{
  "dish_recipe_ingredient": {
    "id": 1,
    "dish_recipe_id": 1,
    "item_id": 5,
    "quantity": 0.250000
  }
}
```

---

### List Dish Recipe Ingredients

Returns a paginated list of all active ingredients for a given dish recipe.

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `recipe-id` | long | Dish recipe ID |

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
      "dish_recipe_id": 1,
      "item_id": 5,
      "quantity": 0.250000
    },
    {
      "id": 2,
      "dish_recipe_id": 1,
      "item_id": 8,
      "quantity": 1.000000
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

### Update Dish Recipe Ingredient

**`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `recipe-id` | long | Dish recipe ID |
| `id` | long | Ingredient ID |

#### Request Body

```json
{
  "item_id": 5,
  "quantity": 0.500
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |
| `quantity` | decimal | no | precision 18, scale 6 |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Dish Recipe Ingredient

Soft-deletes a dish recipe ingredient.

**`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `recipe-id` | long | Dish recipe ID |
| `id` | long | Ingredient ID |

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
  "message": "DishRecipe not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

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
