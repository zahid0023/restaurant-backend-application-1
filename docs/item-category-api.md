# Item Category API

Base URL: `/api/v1/item-categories`

Item categories form a self-referential tree. A category can optionally reference another category as its parent. Categories are standalone — they are not tied to any item type. Items are assigned to categories via the Item-Category Assignment API.

`code` is immutable after creation.

---

## Endpoints

| Method | Path                                                        | Description                        |
|--------|-------------------------------------------------------------|------------------------------------|
| POST   | `/api/v1/item-categories`                                   | Create an item category            |
| GET    | `/api/v1/item-categories/{id}`                              | Get an item category               |
| GET    | `/api/v1/item-categories/root`                              | List root item categories          |
| GET    | `/api/v1/item-categories/{item-category-id}/sub-categories` | List sub-categories                |
| PUT    | `/api/v1/item-categories/{id}`                              | Update an item category            |
| DELETE | `/api/v1/item-categories/{id}`                              | Delete an item category            |
| POST   | `/api/v1/item-categories/{item-category-id}/locales`        | Create an item category locale     |
| PUT    | `/api/v1/item-categories/{item-category-id}/locales/{id}`   | Update an item category locale     |
| DELETE | `/api/v1/item-categories/{item-category-id}/locales/{id}`   | Delete an item category locale     |
| POST   | `/api/v1/item-item-categories`                              | Assign an item to a category       |
| DELETE | `/api/v1/item-item-categories/{item-id}/{item-category-id}` | Unassign an item from a category   |

---
    
## Data Model

### Item Category

| Field           | Type    | Required | Constraints                         | Description                                      |
|-----------------|---------|----------|-------------------------------------|--------------------------------------------------|
| `id`            | Long    | —        | read-only                           | Auto-generated identifier                        |
| `code`          | String  | Yes      | max 50 chars, unique, immutable     | Unique category code                             |
| `sort_order`    | Integer | Yes      | not null                            | Display order                                    |
| `parent_id`     | Long    | No       | must exist, immutable after create  | Parent category ID; null for root categories     |
| `locales`       | Array   | —        | read-only                           | Locale translations                              |
| `sub_categories`| Array   | —        | read-only, getById only             | Immediate child categories (one level)           |
| `items`         | Array   | —        | read-only, getById only             | Items assigned to this category                  |

### Item Category Locale

| Field         | Type    | Required | Constraints              | Description                        |
|---------------|---------|----------|--------------------------|------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier          |
| `locale_id`   | Long    | Yes      | must exist               | ID of an existing active locale    |
| `name`        | String  | Yes      | max 255 chars, not blank | Localized name                     |
| `description` | String  | No       | defaults to `""`         | Localized description              |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale      |

---

## Create Item Category

`POST /api/v1/item-categories`

Creates a new item category with optional parent and optional locale translations.

### Request Body

```json
{
  "code": "MEAT",
  "sort_order": 1,
  "parent_id": null,
  "locales": [
    {
      "locale_id": 1,
      "name": "Meat",
      "description": "Animal meat ingredients",
      "sort_order": 1
    }
  ]
}
```

### Request Fields

| Field         | Type    | Required | Validation                                         |
|---------------|---------|----------|----------------------------------------------------|
| `code`        | String  | Yes      | Not blank, max 50 chars, unique                    |
| `sort_order`  | Integer | Yes      | Not null                                           |
| `parent_id`   | Long    | No       | Must reference an existing active item category    |
| `locales`     | Array   | No       | See locale fields below                            |

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

## Get Item Category

`GET /api/v1/item-categories/{id}`

Returns a single item category with locale translations, immediate sub-categories, and assigned items.

### Path Parameters

| Parameter | Type | Description          |
|-----------|------|----------------------|
| `id`      | Long | ID of the item category |

### Response `200 OK`

```json
{
  "item_category": {
    "id": 1,
    "code": "MEAT",
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Meat",
        "description": "Animal meat ingredients",
        "sort_order": 1
      }
    ],
    "sub_categories": [
      {
        "id": 3,
        "code": "BEEF",
        "sort_order": 1,
        "locales": [
          {
            "id": 5,
            "locale_id": 1,
            "name": "Beef",
            "description": "Cattle meat",
            "sort_order": 1
          }
        ]
      }
    ],
    "items": [
      {
        "id": 1,
        "code": "CHICKEN_BREAST",
        "unit_type": {
          "id": 2,
          "code": "WEIGHT",
          "sort_order": 1,
          "locales": [
            { "id": 3, "locale_id": 1, "name": "Weight", "description": "", "sort_order": 1 }
          ]
        },
        "sort_order": 1,
        "locales": [
          {
            "id": 1,
            "locale_id": 1,
            "name": "Chicken Breast",
            "description": "Boneless chicken breast",
            "sort_order": 1
          }
        ]
      }
    ]
  }
}
```

> `sub_categories` contains only immediate children (one level deep). `items` lists all active items assigned to this category.

---

## List Root Item Categories

`GET /api/v1/item-categories/root`

Returns a paginated list of top-level (parentless) active item categories.

### Query Parameters

| Parameter  | Type   | Default | Constraints                            | Description              |
|------------|--------|---------|----------------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "parent_id": null,
      "code": "MEAT",
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Meat",
          "description": "Animal meat ingredients",
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

## List Sub-Categories

`GET /api/v1/item-categories/{item-category-id}/sub-categories`

Returns a paginated list of direct children of a given item category.

### Path Parameters

| Parameter          | Type | Description             |
|--------------------|------|-------------------------|
| `item-category-id` | Long | Parent item category ID |

### Query Parameters

| Parameter  | Type   | Default | Constraints                            | Description              |
|------------|--------|---------|----------------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

### Response `200 OK`

Same shape as [List Root Item Categories](#list-root-item-categories). All entries will have `parent_id` equal to `{item-category-id}`.

---

## Update Item Category

`PUT /api/v1/item-categories/{id}`

Updates `sort_order`. `code` and `parent_id` are immutable after creation. Locale translations are managed via the item category locale endpoints.

### Path Parameters

| Parameter | Type | Description             |
|-----------|------|-------------------------|
| `id`      | Long | ID of the item category |

### Request Body

```json
{
  "sort_order": 2
}
```

### Request Fields

| Field        | Type    | Required | Validation |
|--------------|---------|----------|------------|
| `sort_order` | Integer | Yes      | Not null   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Item Category

`DELETE /api/v1/item-categories/{id}`

Soft-deletes the item category. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description             |
|-----------|------|-------------------------|
| `id`      | Long | ID of the item category |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Item Category Locales

Manage locale-specific translations for an item category. `{item-category-id}` must reference an existing active item category.

---

### Create Item Category Locale

`POST /api/v1/item-categories/{item-category-id}/locales`

#### Path Parameters

| Parameter          | Type | Description             |
|--------------------|------|-------------------------|
| `item-category-id` | Long | ID of the item category |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Meat",
  "description": "Animal meat ingredients",
  "sort_order": 1
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

### Update Item Category Locale

`PUT /api/v1/item-categories/{item-category-id}/locales/{id}`

Updates translation fields. The locale itself cannot be changed.

#### Path Parameters

| Parameter          | Type | Description                    |
|--------------------|------|--------------------------------|
| `item-category-id` | Long | ID of the item category        |
| `id`               | Long | ID of the item category locale |

#### Request Body

```json
{
  "name": "Meat",
  "description": "Fresh and frozen animal meat",
  "sort_order": 1
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

### Delete Item Category Locale

`DELETE /api/v1/item-categories/{item-category-id}/locales/{id}`

Soft-deletes a locale translation.

#### Path Parameters

| Parameter          | Type | Description                    |
|--------------------|------|--------------------------------|
| `item-category-id` | Long | ID of the item category        |
| `id`               | Long | ID of the item category locale |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

## Item-Category Assignments

Assign and unassign items to/from item categories. Uses the `/api/v1/item-item-categories` base path.

---

### Assign Item to Category

`POST /api/v1/item-item-categories`

#### Request Body

```json
{
  "item_id": 3,
  "item_category_id": 1
}
```

#### Request Fields

| Field              | Type | Required | Validation                                      |
|--------------------|------|----------|-------------------------------------------------|
| `item_id`          | Long | Yes      | Not null, must reference an existing active item |
| `item_category_id` | Long | Yes      | Not null, must reference an existing active item category |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Unassign Item from Category

`DELETE /api/v1/item-item-categories/{item-id}/{item-category-id}`

Soft-deletes the assignment between an item and a category.

#### Path Parameters

| Parameter          | Type | Description      |
|--------------------|------|------------------|
| `item-id`          | Long | ID of the item   |
| `item-category-id` | Long | ID of the category |

#### Response `200 OK`

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
  "message": "ItemCategory not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                |
|-------------|----------------------------|----------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                        |
| 404         | `ENTITY_NOT_FOUND`         | Item category or locale not found, or already deleted                |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code or duplicate locale)       |
