# Menu Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Menu Categories

Menu categories are nested under a parent menu. The `{menu-id}` in all paths must refer to an existing active menu.

---

### Create Menu Category

Creates a new menu category with optional embedded locale translations.

**`POST /api/v1/menus/{menu-id}/categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |

#### Request Body

```json
{
  "code": "STARTERS",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Starters",
      "description": "Cold and warm starters",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Mezeler",
      "description": "Soğuk ve sıcak mezeler",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |
| `locales` | array | no | see locale fields below |
| `locales[].locale_id` | long | yes | must be an existing active locale |
| `locales[].name` | string | yes | max 255 chars, not blank |
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

### Get Menu Category by ID

**`GET /api/v1/menus/{menu-id}/categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu category ID |

#### Response `200 OK`

```json
{
  "menu_category": {
    "id": 1,
    "menu_id": 1,
    "code": "STARTERS",
    "sort_order": 1
  }
}
```

---

### List Menu Categories

Returns a paginated list of all active categories for a given menu.

**`GET /api/v1/menus/{menu-id}/categories`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "menu_id": 1,
      "code": "STARTERS",
      "sort_order": 1
    },
    {
      "id": 2,
      "menu_id": 1,
      "code": "MAIN_COURSE",
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

### Update Menu Category

**`PUT /api/v1/menus/{menu-id}/categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu category ID |

#### Request Body

```json
{
  "code": "STARTERS",
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Menu Category

Soft-deletes a menu category (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu category ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Menu Category Locales

Manage locale-specific translations for a menu category. Both `{menu-id}` and `{category-id}` must refer to existing active records.

---

### Create Menu Category Locale

**`POST /api/v1/menus/{menu-id}/categories/{category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `category-id` | long | Menu category ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Starters",
  "description": "Cold and warm starters",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per category |
| `name` | string | yes | max 255 chars, not blank |
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

### Get Menu Category Locale by ID

**`GET /api/v1/menus/{menu-id}/categories/{category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `category-id` | long | Menu category ID |
| `id` | long | Menu category locale ID |

#### Response `200 OK`

```json
{
  "menu_category_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Starters",
    "description": "Cold and warm starters",
    "sort_order": 1
  }
}
```

---

### List Menu Category Locales

Returns a paginated list of all active locale translations for a given menu category.

**`GET /api/v1/menus/{menu-id}/categories/{category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `category-id` | long | Menu category ID |

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
      "name": "Starters",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Mezeler",
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

### Update Menu Category Locale

**`PUT /api/v1/menus/{menu-id}/categories/{category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `category-id` | long | Menu category ID |
| `id` | long | Menu category locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Starters",
  "description": "Cold and warm starters - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
| `name` | string | yes | max 255 chars, not blank |
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

### Delete Menu Category Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/categories/{category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `category-id` | long | Menu category ID |
| `id` | long | Menu category locale ID |

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
  "message": "MenuCategory not found with id: 99"
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
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
