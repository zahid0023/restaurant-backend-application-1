# Menu Type API Documentation

Base URL: `/api/v1`

Most endpoints require a valid JWT bearer token. Public endpoints are marked explicitly.

```
Authorization: Bearer <token>
```

---

## Endpoints

| Method | Path                                   | Auth     | Description                                  |
|--------|----------------------------------------|----------|----------------------------------------------|
| GET    | `/api/v1/menus/public`                 | Public   | Full menu tree (types → categories → dishes) |
| POST   | `/api/v1/menus`                        | Required | Create a menu type                           |
| GET    | `/api/v1/menus/{id}`                   | Required | Get a menu type                              |
| GET    | `/api/v1/menus`                        | Required | List all menu types                          |
| PUT    | `/api/v1/menus/{id}`                   | Required | Update a menu type                           |
| DELETE | `/api/v1/menus/{id}`                   | Required | Delete a menu type                           |
| POST   | `/api/v1/menus/{menu-id}/locales`      | Required | Create a menu type locale                    |
| PUT    | `/api/v1/menus/{menu-id}/locales/{id}` | Required | Update a menu type locale                    |
| DELETE | `/api/v1/menus/{menu-id}/locales/{id}` | Required | Delete a menu type locale                    |

---

## Public Menu

### Get Public Menu

**`GET /api/v1/menus/public`**

**Public — no authentication required.**

Returns all active menu types ordered by `sort_order`, each with their categories and the dishes assigned to each
category. Intended for public-facing menus (e.g. website menu pages).

#### Response `200 OK`

```json
[
  {
    "id": 1,
    "locales": [
      {
        "locale_id": 1,
        "name": "Lunch Menu",
        "description": "Available from 12:00 to 15:00"
      },
      {
        "locale_id": 2,
        "name": "দুপুরের মেনু",
        "description": "১২:০০ থেকে ১৫:০০ পর্যন্ত পরিবেশিত"
      }
    ],
    "categories": [
      {
        "id": 1,
        "locales": [
          {
            "locale_id": 1,
            "name": "Starters",
            "description": "Light appetizers to begin your meal"
          }
        ],
        "dishes": [
          {
            "id": 11,
            "locales": [
              {
                "locale_id": 1,
                "name": "Spring Rolls",
                "description": "Crispy vegetable spring rolls"
              }
            ],
            "cheapest_variant_id": 5,
            "price": 4.99,
            "img": "https://cdn.shadcnstudio.com/ss-assets/template/landing-page/bistro/image-18.png",
            "img_alt": "plate-1"
          }
        ]
      }
    ]
  }
]
```

#### Notes

- No pagination — all active menu types are returned in a single list.
- Menu types are ordered by `sort_order` ascending.
- Soft-deleted menu types, categories, and dishes are excluded.

---

## Menu Types

### Create Menu Type

Creates a new menu type with optional embedded locale translations.

**`POST /api/v1/menus`**

#### Request Body

```json
{
  "code": "LUNCH",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Lunch Menu",
      "description": "Available from 12:00 to 15:00",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "দুপুরের মেনু",
      "description": "১২:০০ থেকে ১৫:০০ পর্যন্ত পরিবেশিত",
      "sort_order": 1
    }
  ]
}
```

| Field                   | Type    | Required | Constraints                                       |
|-------------------------|---------|----------|---------------------------------------------------|
| `code`                  | string  | yes      | max 50 chars, not blank, immutable after creation |
| `sort_order`            | integer | yes      |                                                   |
| `locales`               | array   | no       | see locale fields below                           |
| `locales[].locale_id`   | long    | yes      | must be an existing active locale                 |
| `locales[].name`        | string  | yes      | max 255 chars, not blank                          |
| `locales[].description` | string  | no       |                                                   |
| `locales[].sort_order`  | integer | yes      |                                                   |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Menu Type by ID

**`GET /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description  |
|-----------|------|--------------|
| `id`      | long | Menu type ID |

#### Response `200 OK`

```json
{
  "menu": {
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
}
```

---

### List Menu Types

Returns a paginated list of all active menu types. Use the `detail` parameter to control how much nested data is
included.

**`GET /api/v1/menus`**

#### Query Parameters

| Parameter  | Type    | Default | Constraints                            | Description             |
|------------|---------|---------|----------------------------------------|-------------------------|
| `page`     | integer | `0`     | min 0                                  | Page index (zero-based) |
| `size`     | integer | `10`    | 1–50                                   | Items per page          |
| `sort_by`  | string  | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by        |
| `sort_dir` | string  | `ASC`   | `ASC`, `DESC`                          | Sort direction          |
| `detail`   | string  | `BASIC` | `BASIC`, `WITH_CATEGORIES`, `FULL`     | Response detail level   |

---

#### `detail=BASIC` (default)

Returns menu type identity and locale translations only.

**`GET /api/v1/menus`** or **`GET /api/v1/menus?detail=BASIC`**

##### Response `200 OK`

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

#### `detail=WITH_CATEGORIES`

Returns each menu type with its assigned categories and their locale translations. Dishes are not included.

**`GET /api/v1/menus?detail=WITH_CATEGORIES`**

##### Response `200 OK`

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
        }
      ],
      "categories": [
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
          "sort_order": 3,
          "locales": [
            {
              "id": 3,
              "locale_id": 1,
              "name": "Main Course",
              "description": "Hearty main course dishes for lunch",
              "sort_order": 1
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

#### `detail=FULL`

Returns each menu type with its categories, and each category with its assigned dishes and their locale translations.

**`GET /api/v1/menus?detail=FULL`**

##### Response `200 OK`

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
        }
      ],
      "categories": [
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
          ],
          "dishes": [
            {
              "id": 11,
              "code": "SPRING_ROLLS",
              "sort_order": 11,
              "locales": [
                {
                  "id": 21,
                  "locale_id": 1,
                  "name": "Spring Rolls",
                  "description": "Crispy vegetable spring rolls",
                  "sort_order": 11
                },
                {
                  "id": 22,
                  "locale_id": 2,
                  "name": "স্প্রিং রোল",
                  "description": "মুচমুচে সবজি স্প্রিং রোল",
                  "sort_order": 11
                }
              ]
            },
            {
              "id": 12,
              "code": "BRUSCHETTA",
              "sort_order": 12,
              "locales": [
                {
                  "id": 23,
                  "locale_id": 1,
                  "name": "Bruschetta",
                  "description": "Toasted bread topped with tomato and herbs",
                  "sort_order": 12
                }
              ]
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

### Update Menu Type

Updates the mutable fields of an existing menu type. `code` is immutable and cannot be changed. Locale translations are
managed separately via the Menu Type Locales endpoints.

**`PUT /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description  |
|-----------|------|--------------|
| `id`      | long | Menu type ID |

#### Request Body

```json
{
  "sort_order": 2
}
```

| Field        | Type    | Required | Constraints |
|--------------|---------|----------|-------------|
| `sort_order` | integer | yes      |             |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Menu Type

Soft-deletes a menu type (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description  |
|-----------|------|--------------|
| `id`      | long | Menu type ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Menu Type Locales

Manage locale-specific translations for a menu type. The `{menu-id}` in all paths must refer to an existing active menu
type.

---

### Create Menu Type Locale

**`POST /api/v1/menus/{menu-id}/locales`**

#### Path Parameters

| Parameter | Type | Description  |
|-----------|------|--------------|
| `menu-id` | long | Menu type ID |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Déjeuner",
  "description": "Disponible de 12h00 à 15h00",
  "sort_order": 3
}
```

| Field         | Type    | Required | Constraints                                             |
|---------------|---------|----------|---------------------------------------------------------|
| `locale_id`   | long    | yes      | must be an existing active locale; unique per menu type |
| `name`        | string  | yes      | max 255 chars, not blank                                |
| `description` | string  | no       |                                                         |
| `sort_order`  | integer | yes      |                                                         |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 3
}
```

---

### Update Menu Type Locale

Updates the translation fields. The locale cannot be changed; use delete + create to switch locale.

**`PUT /api/v1/menus/{menu-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `menu-id` | long | Menu type ID        |
| `id`      | long | Menu type locale ID |

#### Request Body

```json
{
  "name": "Lunch Menu",
  "description": "Available from 12:00 to 15:00 - updated",
  "sort_order": 1
}
```

| Field         | Type    | Required | Constraints              |
|---------------|---------|----------|--------------------------|
| `name`        | string  | yes      | max 255 chars, not blank |
| `description` | string  | no       |                          |
| `sort_order`  | integer | yes      |                          |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Menu Type Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `menu-id` | long | Menu type ID        |
| `id`      | long | Menu type locale ID |

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
  "message": "Menu not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid. Not applicable to `GET /api/v1/menus/public`.

### 403 Forbidden

Returned when the authenticated user lacks permission. Not applicable to `GET /api/v1/menus/public`.
