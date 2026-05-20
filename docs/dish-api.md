# Dish API

Base URL: `/api/v1`

Dishes represent menu items within a menu category. Dish names and descriptions are locale-specific and embedded in every response via the `locales` array. Variants (with their recipes and ingredients) can be created inline when creating a dish. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                                                                       | Description               |
|--------|--------------------------------------------------------------------------------------------|---------------------------|
| POST   | `/api/v1/menu-categories/{menu-category-id}/dishes`                                        | Create a dish             |
| GET    | `/api/v1/menu-categories/{menu-category-id}/dishes/{id}`                                   | Get a dish                |
| GET    | `/api/v1/menu-categories/{menu-category-id}/dishes`                                        | List all dishes           |
| PUT    | `/api/v1/menu-categories/{menu-category-id}/dishes/{id}`                                   | Update a dish             |
| DELETE | `/api/v1/menu-categories/{menu-category-id}/dishes/{id}`                                   | Delete a dish             |
| POST   | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales`      | Create a dish locale      |
| PUT    | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales/{id}` | Update a dish locale      |
| DELETE | `/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales/{id}` | Delete a dish locale      |

---

## Data Model

### Dish

| Field               | Type    | Required | Constraints            | Description                                        |
|---------------------|---------|----------|------------------------|----------------------------------------------------|
| `id`                | Long    | —        | read-only              | Auto-generated identifier                          |
| `menu_category_id`  | Long    | —        | read-only              | ID of the parent menu category                     |
| `code`              | String  | Yes      | max 50 chars, not blank, create-only | Unique dish code (e.g., `BURGER_CLASSIC`) |
| `sort_order`        | Integer | Yes      | not null               | Display order                                      |
| `is_veg`            | Boolean | No       | —                      | Whether the dish is vegetarian                     |
| `locales`           | Array   | —        | read-only              | All locale translations for this dish              |

### Dish Locale

| Field         | Type    | Required | Constraints              | Description                          |
|---------------|---------|----------|--------------------------|--------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier            |
| `locale_id`   | Long    | Yes      | not null, must exist     | ID of an existing active locale      |
| `name`        | String  | Yes      | not blank, max 255 chars | Localized name of the dish           |
| `description` | String  | No       | —                        | Localized description                |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale entry  |

---

## Create Dish

`POST /api/v1/menu-categories/{menu-category-id}/dishes`

Creates a dish along with optional locale translations and variants. Variants are created inline — each variant must include a `recipe` with at least one ingredient. All provided `locale_id` values must reference existing active locales. All provided `item_id` and `unit_id` values must reference existing active records.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |

### Request Body

```json
{
  "code": "BURGER_CLASSIC",
  "sort_order": 1,
  "is_veg": false,
  "locales": [
    {
      "locale_id": 1,
      "name": "Classic Burger",
      "description": "A juicy beef patty with fresh vegetables.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "ক্লাসিক বার্গার",
      "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।",
      "sort_order": 2
    }
  ],
  "variants": [
    {
      "code": "SINGLE",
      "sort_order": 1,
      "price": 5.99,
      "is_default": true,
      "is_available": true,
      "is_featured": false,
      "locales": [
        {
          "locale_id": 1,
          "name": "Single",
          "description": "Single patty burger",
          "sort_order": 1
        }
      ],
      "recipe": {
        "code": "BURGER_CLASSIC_SINGLE",
        "ingredients": [
          {
            "item_id": 3,
            "quantity": 1.000,
            "unit_id": 1
          },
          {
            "item_id": 7,
            "quantity": 0.100,
            "unit_id": 2
          }
        ]
      }
    }
  ]
}
```

### Request Fields

| Field        | Type    | Required | Validation              |
|--------------|---------|----------|-------------------------|
| `code`       | String  | Yes      | Not blank, max 50 chars |
| `sort_order` | Integer | Yes      | Not null                |
| `is_veg`     | Boolean | No       | —                       |
| `locales`    | Array   | No       | See locale fields below |
| `variants`   | Array   | No       | See variant fields in [Dish Variant API](dish-variant-api.md) |

**Locale fields (`locales[]`):**

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 255 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Dish

`GET /api/v1/menu-categories/{menu-category-id}/dishes/{id}`

Returns a single dish with all its locale translations.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |
| `id`               | Long | ID of the dish           |

### Response `200 OK`

```json
{
  "dish": {
    "id": 1,
    "menu_category_id": 2,
    "code": "BURGER_CLASSIC",
    "sort_order": 1,
    "is_veg": false,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Classic Burger",
        "description": "A juicy beef patty with fresh vegetables.",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "ক্লাসিক বার্গার",
        "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।",
        "sort_order": 2
      }
    ]
  }
}
```

---

## List All Dishes

`GET /api/v1/menu-categories/{menu-category-id}/dishes`

Returns a paginated list of active (non-deleted) dishes for a menu category.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |

### Query Parameters

| Parameter  | Type    | Default | Constraints                              | Description              |
|------------|---------|---------|------------------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                                     | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                                   | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `sortOrder`, `createdAt`   | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                            | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "menu_category_id": 2,
      "code": "BURGER_CLASSIC",
      "sort_order": 1,
      "is_veg": false
    },
    {
      "id": 2,
      "menu_category_id": 2,
      "code": "VEGGIE_WRAP",
      "sort_order": 2,
      "is_veg": true
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

## Update Dish

`PUT /api/v1/menu-categories/{menu-category-id}/dishes/{id}`

Updates `sort_order` and `is_veg`. The `code` field is set at creation and cannot be changed. Locale translations are managed via the dish locale endpoints. Variants are managed via the [Dish Variant API](dish-variant-api.md).

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |
| `id`               | Long | ID of the dish           |

### Request Body

```json
{
  "sort_order": 2,
  "is_veg": true
}
```

### Request Fields

| Field        | Type    | Required | Validation |
|--------------|---------|----------|------------|
| `sort_order` | Integer | Yes      | Not null   |
| `is_veg`     | Boolean | No       | —          |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dish

`DELETE /api/v1/menu-categories/{menu-category-id}/dishes/{id}`

Soft-deletes the dish. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-category-id` | Long | ID of the menu category  |
| `id`               | Long | ID of the dish           |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Locales

Dish locale endpoints manage per-locale translations for a dish. The `{dish-id}` path parameter must reference an existing, active dish.

---

### Create Dish Locale

`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales`

Adds a new locale translation to an existing dish.

#### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Klassischer Burger",
  "description": "Ein saftiger Rindfleisch-Patty mit frischem Gemüse.",
  "sort_order": 3
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 255 chars |
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

### Update Dish Locale

`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales/{id}`

Updates an existing locale translation for a dish. The `locale_id` cannot be changed.

#### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `id`               | Long | ID of the dish locale    |

#### Request Body

```json
{
  "name": "Klassischer Burger",
  "description": "Updated description.",
  "sort_order": 3
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `name`        | String  | Yes      | Not blank, max 255 chars |
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

### Delete Dish Locale

`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales/{id}`

Soft-deletes a dish locale. The record is not removed from the database but will no longer appear in any response.

#### Path Parameters

| Parameter          | Type | Description              |
|--------------------|------|--------------------------|
| `menu-id`          | Long | ID of the menu           |
| `menu-category-id` | Long | ID of the menu category  |
| `dish-id`          | Long | ID of the dish           |
| `id`               | Long | ID of the dish locale    |

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
  "message": "Dish not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                    |
|-------------|----------------------------|--------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields, blank code, or invalid sort field               |
| 404         | `ENTITY_NOT_FOUND`         | Dish, menu category, locale, or dish locale not found or already deleted |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                               |
