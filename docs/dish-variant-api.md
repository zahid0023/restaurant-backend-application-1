# Dish Variant API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Dish Variants

Dish variants are nested under a dish. All path ancestors (`{menu-id}`, `{menu-category-id}`, `{dish-id}`) must refer to existing active records.

---

### Create Dish Variant

Creates a new dish variant with optional embedded locale translations.

**`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |

#### Request Body

```json
{
  "code": "SMALL",
  "sort_order": 1,
  "price": 9.99,
  "is_default": true,
  "locales": [
    {
      "locale_id": 1,
      "name": "Small",
      "description": "Perfect for one person",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Küçük",
      "description": "Bir kişilik porsiyon",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |
| `price` | decimal | yes | precision 18, scale 2 |
| `is_default` | boolean | yes | |
| `locales` | array | no | see locale fields below |
| `locales[].locale_id` | long | yes | must be an existing active locale |
| `locales[].name` | string | yes | max 100 chars, not blank |
| `locales[].description` | string | no | defaults to `""` |
| `locales[].sort_order` | integer | yes | |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Dish Variant by ID

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `id` | long | Dish variant ID |

#### Response `200 OK`

```json
{
  "dish_variant": {
    "id": 1,
    "dish_id": 1,
    "code": "SMALL",
    "sort_order": 1,
    "price": 9.99,
    "is_default": true
  }
}
```

---

### List Dish Variants

Returns a paginated list of all active variants for a given dish.

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `code`, `sortOrder`, `price`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dish_id": 1,
      "code": "SMALL",
      "sort_order": 1,
      "price": 9.99,
      "is_default": true
    },
    {
      "id": 2,
      "dish_id": 1,
      "code": "LARGE",
      "sort_order": 2,
      "price": 14.99,
      "is_default": false
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

### Update Dish Variant

Updates the fields of an existing dish variant. Locale translations are managed separately via the Dish Variant Locales API.

**`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `id` | long | Dish variant ID |

#### Request Body

```json
{
  "code": "SMALL",
  "sort_order": 1,
  "price": 10.99,
  "is_default": true
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |
| `price` | decimal | yes | precision 18, scale 2 |
| `is_default` | boolean | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Dish Variant

Soft-deletes a dish variant (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `id` | long | Dish variant ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Variant Locales

Manage locale-specific translations for a dish variant. All path ancestors must refer to existing active records.

---

### Create Dish Variant Locale

**`POST /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales`**

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
  "locale_id": 1,
  "name": "Small",
  "description": "Perfect for one person",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per variant |
| `name` | string | yes | max 100 chars, not blank |
| `description` | string | no | defaults to `""` |
| `sort_order` | integer | yes | |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Dish Variant Locale by ID

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish variant locale ID |

#### Response `200 OK`

```json
{
  "dish_variant_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Small",
    "description": "Perfect for one person",
    "sort_order": 1
  }
}
```

---

### List Dish Variant Locales

Returns a paginated list of all active locale translations for a given dish variant.

**`GET /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales`**

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
| `sort_by` | string | `id` | `id`, `name`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "locale_id": 1,
      "name": "Small",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Küçük",
      "sort_order": 1
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

> Note: The list response returns a summary shape (`id`, `locale_id`, `name`, `sort_order`). Use the Get by ID endpoint to retrieve `description`.

---

### Update Dish Variant Locale

**`PUT /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish variant locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Small",
  "description": "Perfect for one person - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
| `name` | string | yes | max 100 chars, not blank |
| `description` | string | no | defaults to `""` |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Dish Variant Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `menu-category-id` | long | Menu category ID |
| `dish-id` | long | Dish ID |
| `variant-id` | long | Dish variant ID |
| `id` | long | Dish variant locale ID |

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
  "message": "DishVariant not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "code": "must not be blank",
    "price": "must not be null",
    "is_default": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
