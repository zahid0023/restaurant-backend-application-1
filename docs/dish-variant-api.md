# Dish Variant API

Base URL: `/api/v1`

Dish variants represent size or style options for a dish (e.g. Small, Large). Each variant has optional locale
translations and optional ingredients embedded inline on create. Images may be uploaded at creation time or managed
separately via the dedicated image endpoints. Locale translations and ingredients are embedded in the full `GET /{id}`
response. All records support soft-delete.

---

## Endpoints

| Method | Path                                                              | Description                       |
|--------|-------------------------------------------------------------------|-----------------------------------|
| POST   | `/api/v1/dishes/{dish-id}/variants`                               | Create a dish variant             |
| GET    | `/api/v1/dishes/{dish-id}/variants/{id}`                          | Get a dish variant                |
| GET    | `/api/v1/dishes/{dish-id}/variants`                               | List all dish variants            |
| PUT    | `/api/v1/dishes/{dish-id}/variants/{id}`                          | Update a dish variant             |
| DELETE | `/api/v1/dishes/{dish-id}/variants/{id}`                          | Delete a dish variant             |
| POST   | `/api/v1/dishes/{dish-id}/variants/{variant-id}/locales`          | Create a dish variant locale      |
| PUT    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/locales/{id}`     | Update a dish variant locale      |
| DELETE | `/api/v1/dishes/{dish-id}/variants/{variant-id}/locales/{id}`     | Delete a dish variant locale      |
| POST   | `/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients`      | Create a dish variant ingredient  |
| GET    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}` | Get a dish variant ingredient     |
| GET    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients`      | List all dish variant ingredients |
| PUT    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}` | Update a dish variant ingredient  |
| DELETE | `/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}` | Delete a dish variant ingredient  |
| POST   | `/api/v1/dishes/{dish-id}/variants/{variant-id}/images`           | Upload images for a dish variant  |
| GET    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/images`           | List images for a dish variant    |
| GET    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`      | Get a dish variant image          |
| PUT    | `/api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`      | Update a dish variant image       |
| DELETE | `/api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`      | Delete a dish variant image       |

---

## Data Models

### Dish Variant

| Field         | Type       | Required | Constraints                    | Description                                                               |
|---------------|------------|----------|--------------------------------|---------------------------------------------------------------------------|
| `id`          | Long       | —        | read-only                      | Auto-generated identifier                                                 |
| `dish_id`     | Long       | —        | read-only, list response only  | ID of the parent dish (present in paginated list; omitted in `GET /{id}`) |
| `code`        | String     | Yes      | not blank, max 50, create-only | Unique variant code (e.g., `SMALL`)                                       |
| `sort_order`  | Integer    | Yes      | not null                       | Display order                                                             |
| `price`       | BigDecimal | Yes      | not null, >= 0.00              | Variant price                                                             |
| `is_default`  | Boolean    | Yes      | not null                       | Whether this is the default variant                                       |
| `is_veg`      | Boolean    | Yes      | not null                       | Whether this variant is vegetarian                                        |
| `locales`     | Array      | —        | read-only                      | All locale translations for this variant                                  |
| `ingredients` | Array      | —        | read-only, `GET /{id}` only    | Embedded ingredients (only in single-get response, not in list)           |

### Dish Variant Locale

| Field         | Type    | Required | Constraints              | Description                         |
|---------------|---------|----------|--------------------------|-------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier           |
| `locale_id`   | Long    | Yes      | not null, must exist     | ID of an existing active locale     |
| `name`        | String  | Yes      | not blank, max 100 chars | Localized name of the variant       |
| `description` | String  | No       | —                        | Localized description               |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale entry |

### Dish Variant Image

Returned by image endpoints. Images are **not** embedded in the standard variant `GET /{id}` response; use the dedicated
image endpoints.

| Field             | Type    | Context  | Description                                            |
|-------------------|---------|----------|--------------------------------------------------------|
| `id`              | Long    | response | Auto-generated identifier                              |
| `config_id`       | Long    | response | ID of the hosting config used for upload               |
| `dish_variant_id` | Long    | response | ID of the parent dish variant (list responses)         |
| `external_id`     | String  | response | Provider-assigned asset ID (e.g. Cloudinary public ID) |
| `url`             | String  | response | Public URL of the uploaded image                       |
| `caption`         | String  | response | Optional image caption                                 |
| `sort_order`      | Integer | response | Display order                                          |

---

### Dish Variant Ingredient

| Field          | Type       | Context          | Constraints                       | Description                                                                |
|----------------|------------|------------------|-----------------------------------|----------------------------------------------------------------------------|
| `id`           | Long       | response         | read-only                         | Auto-generated identifier                                                  |
| `dish_variant` | Object     | `GET /{id}`      | read-only                         | Full dish variant object (embedded only in single-get ingredient response) |
| `item_id`      | Long       | request (create) | not null, must exist, create-only | ID of the ingredient item — set on create, cannot be changed               |
| `item`         | Object     | response         | read-only                         | Full item object (`id`, `code`, `sort_order`, `locales[]`)                 |
| `quantity`     | BigDecimal | request/response | not null, >= 0.000                | Amount of the item used                                                    |
| `unit_id`      | Long       | request          | not null, must exist              | ID of the unit — provided on create and update                             |
| `unit`         | Object     | response         | read-only                         | Full unit object with nested `unit_type`                                   |
| `sort_order`   | Integer    | request/response | not null                          | Display order                                                              |

---

## Create Dish Variant

`POST /api/v1/dishes/{dish-id}/variants`

**Content-Type:** `multipart/form-data`

Creates a dish variant with optional locale translations, optional ingredients, and optionally uploads images in the
same request. Images can also be added later via the dedicated image endpoints.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `dish-id` | Long | ID of the dish |

### Request Parts & Params

| Name          | Kind           | Type       | Required               | Description                                                      |
|---------------|----------------|------------|------------------------|------------------------------------------------------------------|
| `config-id`   | Query param    | Long       | Conditional (see note) | ID of the `RestaurantImageHostingConfig` to use for image upload |
| `data`        | Multipart part | JSON       | Yes                    | Main variant payload (see fields below)                          |
| `images`      | Multipart part | File[]     | No                     | One or more image files to upload alongside the variant          |
| `image-metas` | Multipart part | JSON Array | Conditional (see note) | Metadata for each image, matched by original filename            |

> **Conditional validation:** if `images` is provided and non-empty, both `config-id` and `image-metas` become
> required. Omitting either will result in `400 Bad Request`.

### `data` Part (JSON)

```json
{
  "code": "SMALL",
  "sort_order": 1,
  "price": 5.99,
  "is_default": true,
  "is_veg": false,
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
  "ingredients": [
    {
      "item_id": 3,
      "quantity": 1.000,
      "unit_id": 1,
      "sort_order": 1
    },
    {
      "item_id": 7,
      "quantity": 0.100,
      "unit_id": 2,
      "sort_order": 2
    }
  ]
}
```

| Field         | Type       | Required | Validation                  |
|---------------|------------|----------|-----------------------------|
| `code`        | String     | Yes      | Not blank, max 50 chars     |
| `sort_order`  | Integer    | Yes      | Not null                    |
| `price`       | BigDecimal | Yes      | Not null, >= 0.00           |
| `is_default`  | Boolean    | Yes      | Not null                    |
| `is_veg`      | Boolean    | Yes      | Not null                    |
| `locales`     | Array      | No       | See locale fields below     |
| `ingredients` | Array      | No       | See ingredient fields below |

**Locale fields (`locales[]`):**

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 100 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

**Ingredient fields (`ingredients[]`):**

| Field        | Type       | Required | Validation           |
|--------------|------------|----------|----------------------|
| `item_id`    | Long       | Yes      | Not null, must exist |
| `quantity`   | BigDecimal | Yes      | Not null, >= 0.000   |
| `unit_id`    | Long       | Yes      | Not null, must exist |
| `sort_order` | Integer    | Yes      | Not null             |

### `image-metas` Part (JSON Array — required when images are provided)

```json
[
  {
    "client_image_id": "variant-small.jpg",
    "caption": "Small variant front view",
    "is_default": true,
    "sort_order": 1
  }
]
```

| Field             | Type    | Required | Description                                                      |
|-------------------|---------|----------|------------------------------------------------------------------|
| `client_image_id` | String  | Yes      | Must match the original filename of the corresponding image file |
| `caption`         | String  | No       | Image caption                                                    |
| `is_default`      | Boolean | No       | Default: `false`                                                 |
| `sort_order`      | Integer | No       | Default: `0`                                                     |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Dish Variant

`GET /api/v1/dishes/{dish-id}/variants/{id}`

Returns a single dish variant with its locale translations and ingredients.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `dish-id` | Long | ID of the dish         |
| `id`      | Long | ID of the dish variant |

### Response `200 OK`

The response embeds full `item` and `unit` objects in each ingredient. The `dish` field is omitted (not included at this
level).

```json
{
  "dish_variant": {
    "id": 1,
    "code": "SMALL",
    "sort_order": 1,
    "price": 5.99,
    "is_default": true,
    "is_veg": false,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Small",
        "description": "Perfect for one person",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "ছোট",
        "description": "একজনের জন্য উপযুক্ত",
        "sort_order": 2
      }
    ],
    "ingredients": [
      {
        "id": 1,
        "item": {
          "id": 3,
          "code": "TOMATO",
          "sort_order": 1,
          "locales": [
            {
              "id": 1,
              "locale_id": 1,
              "name": "Tomato",
              "description": "Fresh tomato",
              "sort_order": 1
            }
          ]
        },
        "quantity": 1.000,
        "unit": {
          "id": 1,
          "unit_type": {
            "id": 1,
            "code": "WEIGHT",
            "sort_order": 1,
            "locales": [
              {
                "id": 1,
                "locale_id": 1,
                "name": "Weight",
                "description": "",
                "sort_order": 1
              }
            ]
          },
          "code": "KG",
          "is_base": true,
          "sort_order": 1,
          "locales": [
            {
              "id": 1,
              "locale_id": 1,
              "name": "Kilogram",
              "description": "",
              "sort_order": 1
            }
          ]
        },
        "sort_order": 1
      },
      {
        "id": 2,
        "item": {
          "id": 7,
          "code": "CHEESE",
          "sort_order": 2,
          "locales": [
            {
              "id": 2,
              "locale_id": 1,
              "name": "Cheese",
              "description": "",
              "sort_order": 1
            }
          ]
        },
        "quantity": 0.100,
        "unit": {
          "id": 1,
          "unit_type": {
            "id": 1,
            "code": "WEIGHT",
            "sort_order": 1,
            "locales": [
              {
                "id": 1,
                "locale_id": 1,
                "name": "Weight",
                "description": "",
                "sort_order": 1
              }
            ]
          },
          "code": "KG",
          "is_base": true,
          "sort_order": 1,
          "locales": [
            {
              "id": 1,
              "locale_id": 1,
              "name": "Kilogram",
              "description": "",
              "sort_order": 1
            }
          ]
        },
        "sort_order": 2
      }
    ]
  }
}
```

---

## List All Dish Variants

`GET /api/v1/dishes/{dish-id}/variants`

Returns a paginated list of active variants for a dish.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `dish-id` | Long | ID of the dish |

### Query Parameters

| Parameter  | Type    | Default | Constraints                                     | Description              |
|------------|---------|---------|-------------------------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                                            | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                                          | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `sortOrder`, `price`, `createdAt` | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                                   | Sort direction           |

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
      "is_veg": false,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Small",
          "description": "Perfect for one person",
          "sort_order": 1
        }
      ]
    },
    {
      "id": 2,
      "dish_id": 1,
      "code": "LARGE",
      "sort_order": 2,
      "price": 8.99,
      "is_default": false,
      "is_veg": false,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Large",
          "description": "Great for sharing",
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

## Update Dish Variant

`PUT /api/v1/dishes/{dish-id}/variants/{id}`

Updates mutable fields. `code` is immutable. Locale translations are managed via the locale endpoints. Ingredients are
managed via the ingredient endpoints.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `dish-id` | Long | ID of the dish         |
| `id`      | Long | ID of the dish variant |

### Request Body

```json
{
  "sort_order": 1,
  "price": 6.49,
  "is_default": true,
  "is_veg": false
}
```

### Request Fields

| Field        | Type       | Required | Validation        |
|--------------|------------|----------|-------------------|
| `sort_order` | Integer    | Yes      | Not null          |
| `price`      | BigDecimal | Yes      | Not null, >= 0.00 |
| `is_default` | Boolean    | Yes      | Not null          |
| `is_veg`     | Boolean    | Yes      | Not null          |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dish Variant

`DELETE /api/v1/dishes/{dish-id}/variants/{id}`

Soft-deletes the dish variant. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `dish-id` | Long | ID of the dish         |
| `id`      | Long | ID of the dish variant |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Variant Locales

Manage per-locale translations for a dish variant. POST/PUT/DELETE only — translations are embedded in the variant
`GET /{id}` response and do not have dedicated GET endpoints. The `locale_id` is set on create and cannot be changed.

---

### Create Dish Variant Locale

`POST /api/v1/dishes/{dish-id}/variants/{variant-id}/locales`

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |

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

`PUT /api/v1/dishes/{dish-id}/variants/{variant-id}/locales/{id}`

Updates an existing locale translation. `locale_id` cannot be changed; use delete + create to switch locale.

#### Path Parameters

| Parameter    | Type | Description                   |
|--------------|------|-------------------------------|
| `dish-id`    | Long | ID of the dish                |
| `variant-id` | Long | ID of the dish variant        |
| `id`         | Long | ID of the dish variant locale |

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

`DELETE /api/v1/dishes/{dish-id}/variants/{variant-id}/locales/{id}`

Soft-deletes a dish variant locale.

#### Path Parameters

| Parameter    | Type | Description                   |
|--------------|------|-------------------------------|
| `dish-id`    | Long | ID of the dish                |
| `variant-id` | Long | ID of the dish variant        |
| `id`         | Long | ID of the dish variant locale |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

## Dish Variant Ingredients

Manage ingredients for a dish variant. Each ingredient links an item with a quantity and unit. The `item_id` is set on
create and cannot be changed; delete and recreate to replace an ingredient.

---

### Create Dish Variant Ingredient

`POST /api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients`

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |

#### Request Body

```json
{
  "item_id": 5,
  "quantity": 0.250,
  "unit_id": 2,
  "sort_order": 1
}
```

#### Request Fields

| Field        | Type       | Required | Validation           |
|--------------|------------|----------|----------------------|
| `item_id`    | Long       | Yes      | Not null, must exist |
| `quantity`   | BigDecimal | Yes      | Not null, >= 0.000   |
| `unit_id`    | Long       | Yes      | Not null, must exist |
| `sort_order` | Integer    | Yes      | Not null             |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 5
}
```

---

### Get Dish Variant Ingredient

`GET /api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}`

#### Path Parameters

| Parameter    | Type | Description                       |
|--------------|------|-----------------------------------|
| `dish-id`    | Long | ID of the dish                    |
| `variant-id` | Long | ID of the dish variant            |
| `id`         | Long | ID of the dish variant ingredient |

#### Response `200 OK`

The single-get response embeds the full `dish_variant` (with its parent `dish`), `item`, and `unit` objects.

```json
{
  "dish_variant_ingredient": {
    "id": 5,
    "dish_variant": {
      "id": 1,
      "dish": {
        "id": 1,
        "code": "BURGER_CLASSIC",
        "sort_order": 1,
        "is_featured": false,
        "locales": [
          {
            "id": 1,
            "locale_id": 1,
            "name": "Classic Burger",
            "description": "A juicy beef patty with fresh vegetables.",
            "sort_order": 1
          }
        ]
      },
      "code": "SMALL",
      "sort_order": 1,
      "price": 5.99,
      "is_default": true,
      "is_veg": false,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Small",
          "description": "Perfect for one person",
          "sort_order": 1
        }
      ]
    },
    "item": {
      "id": 5,
      "code": "ONION",
      "sort_order": 3,
      "locales": [
        {
          "id": 5,
          "locale_id": 1,
          "name": "Onion",
          "description": "",
          "sort_order": 1
        }
      ]
    },
    "quantity": 0.250,
    "unit": {
      "id": 2,
      "unit_type": {
        "id": 1,
        "code": "WEIGHT",
        "sort_order": 1,
        "locales": [
          {
            "id": 1,
            "locale_id": 1,
            "name": "Weight",
            "description": "",
            "sort_order": 1
          }
        ]
      },
      "code": "KG",
      "is_base": true,
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Kilogram",
          "description": "",
          "sort_order": 1
        }
      ]
    },
    "sort_order": 1
  }
}
```

---

### List All Dish Variant Ingredients

`GET /api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients`

Returns a paginated list of active ingredients for a dish variant. Items and units are returned as flat IDs (`item_id`,
`unit_id`) in the list — use the single-get endpoint to retrieve full nested objects.

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |

#### Query Parameters

| Parameter  | Type    | Default | Constraints                    | Description              |
|------------|---------|---------|--------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                           | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                         | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                  | Sort direction           |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_variant_id": 1,
      "item_id": 3,
      "quantity": 1.000,
      "unit_id": 1,
      "sort_order": 1
    },
    {
      "id": 2,
      "dish_variant_id": 1,
      "item_id": 7,
      "quantity": 0.100,
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

---

### Update Dish Variant Ingredient

`PUT /api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}`

Updates `quantity`, `unit_id`, and `sort_order`. `item_id` is immutable; delete and recreate to replace the ingredient
item.

#### Path Parameters

| Parameter    | Type | Description                       |
|--------------|------|-----------------------------------|
| `dish-id`    | Long | ID of the dish                    |
| `variant-id` | Long | ID of the dish variant            |
| `id`         | Long | ID of the dish variant ingredient |

#### Request Body

```json
{
  "quantity": 0.300,
  "unit_id": 2,
  "sort_order": 1
}
```

#### Request Fields

| Field        | Type       | Required | Validation           |
|--------------|------------|----------|----------------------|
| `quantity`   | BigDecimal | Yes      | Not null, >= 0.000   |
| `unit_id`    | Long       | Yes      | Not null, must exist |
| `sort_order` | Integer    | Yes      | Not null             |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 5
}
```

---

### Delete Dish Variant Ingredient

`DELETE /api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients/{id}`

Soft-deletes a dish variant ingredient.

#### Path Parameters

| Parameter    | Type | Description                       |
|--------------|------|-----------------------------------|
| `dish-id`    | Long | ID of the dish                    |
| `variant-id` | Long | ID of the dish variant            |
| `id`         | Long | ID of the dish variant ingredient |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 5
}
```

---

## Dish Variant Images

Manage images for a dish variant. Images can be uploaded at variant creation time or added/managed separately via these
endpoints. Images are not embedded in the standard variant `GET /{id}` response.

---

### Upload Images

`POST /api/v1/dishes/{dish-id}/variants/{variant-id}/images`

**Content-Type:** `multipart/form-data`

Uploads one or more images and saves them linked to the dish variant.

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |

#### Request Parts & Params

| Name        | Kind           | Type       | Required | Description                                            |
|-------------|----------------|------------|----------|--------------------------------------------------------|
| `config_id` | Query param    | Long       | Yes      | ID of the active `RestaurantImageHostingConfig` to use |
| `images`    | Multipart part | File[]     | Yes      | One or more image files                                |
| `meta`      | Multipart part | JSON Array | Yes      | Metadata for each image, matched by original filename  |

#### `meta` Array Item Schema

```json
[
  {
    "client_image_id": "variant-small.jpg",
    "caption": "Small variant front view",
    "is_default": true,
    "sort_order": 1
  }
]
```

| Field             | Type    | Required | Description                                                      |
|-------------------|---------|----------|------------------------------------------------------------------|
| `client_image_id` | String  | Yes      | Must match the original filename of the corresponding image file |
| `caption`         | String  | No       | Image caption                                                    |
| `is_default`      | Boolean | No       | Default: `false`                                                 |
| `sort_order`      | Integer | No       | Default: `0`                                                     |

#### Response `201 Created`

Returns an array of the created image records.

```json
[
  {
    "id": 1,
    "config_id": 2,
    "dish_variant_id": 1,
    "external_id": "dish-variants/variant-small",
    "url": "https://res.cloudinary.com/demo/image/upload/dish-variants/variant-small.jpg",
    "caption": "Small variant front view",
    "sort_order": 1
  }
]
```

---

### List Images

`GET /api/v1/dishes/{dish-id}/variants/{variant-id}/images`

Returns a paginated list of active images for a dish variant.

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |

#### Query Parameters

| Parameter  | Type    | Default | Constraints                    | Description              |
|------------|---------|---------|--------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                           | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                         | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                  | Sort direction           |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_variant_id": 1,
      "external_id": "dish-variants/variant-small",
      "url": "https://res.cloudinary.com/demo/image/upload/dish-variants/variant-small.jpg",
      "caption": "Small variant front view",
      "sort_order": 1
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

### Get Image by ID

`GET /api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |
| `id`         | Long | ID of the image        |

#### Response `200 OK`

```json
{
  "dish_variant_image": {
    "id": 1,
    "config_id": 2,
    "dish_variant_id": 1,
    "external_id": "dish-variants/variant-small",
    "url": "https://res.cloudinary.com/demo/image/upload/dish-variants/variant-small.jpg",
    "caption": "Small variant front view",
    "sort_order": 1
  }
}
```

---

### Update Image

`PUT /api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`

Updates the caption and/or sort order. Does **not** re-upload the file.

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |
| `id`         | Long | ID of the image        |

#### Request Body

```json
{
  "caption": "Updated caption",
  "sort_order": 2
}
```

| Field        | Type    | Required | Validation |
|--------------|---------|----------|------------|
| `caption`    | String  | No       | —          |
| `sort_order` | Integer | Yes      | Not null   |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Image

`DELETE /api/v1/dishes/{dish-id}/variants/{variant-id}/images/{id}`

Soft-deletes the image record.

#### Path Parameters

| Parameter    | Type | Description            |
|--------------|------|------------------------|
| `dish-id`    | Long | ID of the dish         |
| `variant-id` | Long | ID of the dish variant |
| `id`         | Long | ID of the image        |

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
  "message": "DishVariant not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                                                      |
|-------------|----------------------------|------------------------------------------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields, blank code, invalid sort field, or quantity below minimum                         |
| 400         | `BAD_REQUEST`              | `images` provided without `config-id`, or `images` provided without `image-metas` (or `image-metas` empty) |
| 401         | `UNAUTHORIZED`             | JWT token missing or invalid                                                                               |
| 403         | `FORBIDDEN`                | Authenticated user lacks permission                                                                        |
| 404         | `ENTITY_NOT_FOUND`         | Variant, dish, locale, ingredient, item, unit, image, or config not found or already deleted               |
| 409         | `DATA_INTEGRITY_VIOLATION` | Duplicate code or duplicate (dish_variant_id, item_id) pair                                                |
