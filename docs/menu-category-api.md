# Menu Category API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Menu Categories

### Create Menu Category

Creates a new menu category with optional embedded locale translations.

**`POST /api/v1/menu-categories`**

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
| `locales[].description` | string | no | |
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

**`GET /api/v1/menu-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Menu category ID |

#### Response `200 OK`

```json
{
  "menu_category": {
    "id": 1,
    "code": "STARTERS",
    "sort_order": 1,
    "locales": [
      { "id": 1, "locale_id": 1, "name": "Starters", "description": "Cold and warm starters", "sort_order": 1 },
      { "id": 2, "locale_id": 2, "name": "Mezeler", "description": "Soğuk ve sıcak mezeler", "sort_order": 1 }
    ]
  }
}
```

---

### List Menu Categories

Returns a paginated list of all active menu categories.

**`GET /api/v1/menu-categories`**

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
      "code": "STARTERS",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Starters", "description": "Cold and warm starters", "sort_order": 1 },
        { "id": 2, "locale_id": 2, "name": "Mezeler", "description": "Soğuk ve sıcak mezeler", "sort_order": 1 }
      ]
    },
    {
      "id": 2,
      "code": "MAIN_COURSE",
      "sort_order": 2,
      "locales": [
        { "id": 3, "locale_id": 1, "name": "Main Course", "description": "Main dishes", "sort_order": 1 }
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

### Update Menu Category

Updates the mutable fields of an existing menu category. `code` is immutable and cannot be changed. Locale translations are managed separately via the Menu Category Locales API.

**`PUT /api/v1/menu-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Menu category ID |

#### Request Body

```json
{
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
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

**`DELETE /api/v1/menu-categories/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
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

Manage locale-specific translations for a menu category. The `{menu-category-id}` must refer to an existing active menu category.

---

### Create Menu Category Locale

**`POST /api/v1/menu-categories/{menu-category-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-category-id` | long | Menu category ID |

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
| `description` | string | no | |
| `sort_order` | integer | yes | |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Update Menu Category Locale

Updates the translation fields. The locale cannot be changed; use delete + create to switch locale.

**`PUT /api/v1/menu-categories/{menu-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-category-id` | long | Menu category ID |
| `id` | long | Menu category locale ID |

#### Request Body

```json
{
  "name": "Starters",
  "description": "Cold and warm starters - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `name` | string | yes | max 255 chars, not blank |
| `description` | string | no | |
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

**`DELETE /api/v1/menu-categories/{menu-category-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-category-id` | long | Menu category ID |
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
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
