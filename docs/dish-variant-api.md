# Dish Variant API

Base URL: `/api/v1`

Dish variants represent size or style options for a dish (e.g. Small, Large). Each variant has a mandatory recipe created inline. Locale translations and recipes are embedded in the full response. All records support soft-delete.

---

## Endpoints

| Method | Path                                                                                                             | Description                    |
|--------|------------------------------------------------------------------------------------------------------------------|--------------------------------|
| POST   | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`                          | Create a dish variant          |
| GET    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`                     | Get a dish variant             |
| GET    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`                          | List all dish variants         |
| PUT    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`                     | Update a dish variant          |
| DELETE | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`                     | Delete a dish variant          |
| POST   | `/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales`                     | Create a dish variant locale   |
| PUT    | `/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`                | Update a dish variant locale   |
| DELETE | `/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`                | Delete a dish variant locale   |

---

## Data Model

### Dish Variant

| Field          | Type       | Required | Constraints                   | Description                             |
|----------------|------------|----------|-------------------------------|-----------------------------------------|
| `id`           | Long       | —        | read-only                     | Auto-generated identifier               |
| `dish_id`      | Long       | —        | read-only                     | ID of the parent dish                   |
| `code`         | String     | Yes      | not blank, max 50, create-only | Unique variant code (e.g., `SMALL`)    |
| `sort_order`   | Integer    | Yes      | not null                      | Display order                           |
| `price`        | BigDecimal | Yes      | not null                      | Variant price                           |
| `is_default`   | Boolean    | Yes      | not null                      | Whether this is the default variant     |
| `is_available` | Boolean    | Yes      | not null                      | Whether this variant is available       |
| `is_featured`  | Boolean    | Yes      | not null                      | Whether this variant is featured        |
| `locales`      | Array      | —        | read-only                     | All locale translations for this variant|
| `recipe`       | Object     | —        | read-only                     | The recipe for this variant             |

### Dish Variant Locale

| Field         | Type    | Required | Constraints              | Description                              |
|---------------|---------|----------|--------------------------|------------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier                |
| `locale_id`   | Long    | Yes      | not null, must exist     | ID of an existing active locale          |
| `name`        | String  | Yes      | not blank, max 100 chars | Localized name of the variant            |
| `description` | String  | No       | —                        | Localized description                    |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale entry      |

---

## Create Dish Variant

`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`

Creates a dish variant with optional locale translations and a required recipe. The `recipe` must include at least one ingredient.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |

### Request Body

```json
{
  "code": "SMALL",
  "sort_order": 1,
  "price": 5.99,
  "is_default": true,
  "is_available": true,
  "is_featured": false,
  "locales": [
    {
      "locale_id": 1,
      "name": "Small",
      "description": "Perfect for one person",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "ছোট",
      "description": "একজনের জন্য উপযুক্ত",
      "sort_order": 2
    }
  ],
  "recipe": {
    "code": "BURGER_SMALL_V1",
    "ingredients": [
      {
        "item_id": 3,
        "quantity": 1.000,
        "unit_id": 1
      },
      {
        "item_id": 7,
        "quantity": 0.100
      }
    ]
  }
}
```

### Request Fields

| Field          | Type       | Required | Validation              |
|----------------|------------|----------|-------------------------|
| `code`         | String     | Yes      | Not blank, max 50 chars |
| `sort_order`   | Integer    | Yes      | Not null                |
| `price`        | BigDecimal | Yes      | Not null                |
| `is_default`   | Boolean    | Yes      | Not null                |
| `is_available` | Boolean    | Yes      | Not null                |
| `is_featured`  | Boolean    | Yes      | Not null                |
| `locales`      | Array      | No       | See locale fields below |
| `recipe`       | Object     | Yes      | Not null, see recipe fields below |

**Locale fields (`locales[]`):**

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 100 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

**Recipe fields (`recipe`):**

| Field                          | Type       | Required | Validation                        |
|--------------------------------|------------|----------|-----------------------------------|
| `code`                         | String     | No       | Max 50 chars                      |
| `ingredients`                  | Array      | Yes      | Not empty, min 1 item             |
| `ingredients[].item_id`        | Long       | Yes      | Not null, must exist              |
| `ingredients[].quantity`       | BigDecimal | No       | —                                 |
| `ingredients[].unit_id`        | Long       | Yes      | Not null, must exist              |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Dish Variant

`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`

Returns a single dish variant with its locale translations and recipe (including ingredients).

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `id`               | Long | ID of the dish variant   |

### Response `200 OK`

```json
{
  "dish_variant": {
    "id": 1,
    "dish_id": 1,
    "code": "SMALL",
    "sort_order": 1,
    "price": 5.99,
    "is_default": true,
    "is_available": true,
    "is_featured": false,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Small",
        "description": "Perfect for one person",
        "sort_order": 1
      }
    ],
    "recipe": {
      "id": 1,
      "dish_variant_id": 1,
      "code": "BURGER_SMALL_V1",
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
          "quantity": 0.100,
          "unit_id": null
        }
      ]
    }
  }
}
```

---

## List All Dish Variants

`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`

Returns a paginated list of active variants for a dish.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |

### Query Parameters

| Parameter  | Type    | Default | Constraints                                         | Description              |
|------------|---------|---------|-----------------------------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                                                | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                                              | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `sortOrder`, `price`, `createdAt`     | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                                       | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_id": 1,
      "code": "SMALL",
      "sort_order": 1,
      "price": 5.99,
      "is_default": true,
      "is_available": true,
      "is_featured": false
    },
    {
      "id": 2,
      "dish_id": 1,
      "code": "LARGE",
      "sort_order": 2,
      "price": 8.99,
      "is_default": false,
      "is_available": true,
      "is_featured": true
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

## Update Dish Variant

`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`

Updates mutable fields. The `code` is set at creation and cannot be changed. Locale translations are managed via the dish variant locale endpoints. The recipe is managed via the [Dish Recipe API](dish-recipe-api.md).

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `id`               | Long | ID of the dish variant   |

### Request Body

```json
{
  "sort_order": 1,
  "price": 6.49,
  "is_default": true,
  "is_available": true,
  "is_featured": false
}
```

### Request Fields

| Field          | Type       | Required | Validation |
|----------------|------------|----------|------------|
| `sort_order`   | Integer    | Yes      | Not null   |
| `price`        | BigDecimal | Yes      | Not null   |
| `is_default`   | Boolean    | Yes      | Not null   |
| `is_available` | Boolean    | Yes      | Not null   |
| `is_featured`  | Boolean    | Yes      | Not null   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dish Variant

`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`

Soft-deletes the dish variant.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `id`               | Long | ID of the dish variant   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Variant Locales

Manage per-locale translations for a dish variant. POST/PUT/DELETE only — locale translations are embedded in the variant response and do not have dedicated GET endpoints.

---

### Create Dish Variant Locale

`POST /api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales`

#### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `variant-id`       | Long | ID of the dish variant   |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Klein",
  "description": "Für eine Person",
  "sort_order": 3
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 100 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 3
}
```

---

### Update Dish Variant Locale

`PUT /api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`

Updates an existing locale translation. The `locale_id` cannot be changed.

#### Path Parameters

| Parameter          | Type | Description                   |
|--------------------|------|-------------------------------|
| `menu-category-id` | Long | ID of the menu category       |
| `dish-id`          | Long | ID of the dish                |
| `variant-id`       | Long | ID of the dish variant        |
| `id`               | Long | ID of the dish variant locale |

#### Request Body

```json
{
  "name": "Klein",
  "description": "Aktualisierte Beschreibung.",
  "sort_order": 3
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `name`        | String  | Yes      | Not blank, max 100 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

### Delete Dish Variant Locale

`DELETE /api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`

Soft-deletes a dish variant locale.

#### Path Parameters

| Parameter          | Type | Description                   |
|--------------------|------|-------------------------------|
| `menu-category-id` | Long | ID of the menu category       |
| `dish-id`          | Long | ID of the dish                |
| `variant-id`       | Long | ID of the dish variant        |
| `id`               | Long | ID of the dish variant locale |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
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
  "message": "DishVariant not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                         |
|-------------|----------------------------|-------------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields, blank code, empty ingredients, or invalid sort field |
| 404         | `ENTITY_NOT_FOUND`         | Variant, dish, locale, item, or unit not found or already deleted             |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                                    |
