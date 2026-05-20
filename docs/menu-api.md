# Menu API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Menus

### Create Menu

Creates a new menu with optional embedded locale translations.

**`POST /api/v1/menus`**

#### Request Body

```json
{
  "code": "LUNCH_MENU",
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
      "name": "Öğle Menüsü",
      "description": "12:00 - 15:00 arası geçerlidir",
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

### Get Menu by ID

**`GET /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Menu ID |

#### Response `200 OK`

```json
{
  "menu": {
    "id": 1,
    "code": "LUNCH_MENU",
    "sort_order": 1,
    "locales": [
      { "id": 1, "locale_id": 1, "name": "Lunch Menu", "description": "Available from 12:00 to 15:00", "sort_order": 1 },
      { "id": 2, "locale_id": 2, "name": "Öğle Menüsü", "description": "12:00 - 15:00 arası geçerlidir", "sort_order": 1 }
    ]
  }
}
```

---

### List Menus

Returns a paginated list of all active menus.

**`GET /api/v1/menus`**

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
      "code": "LUNCH_MENU",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Lunch Menu", "description": "Available from 12:00 to 15:00", "sort_order": 1 },
        { "id": 2, "locale_id": 2, "name": "Öğle Menüsü", "description": "12:00 - 15:00 arası geçerlidir", "sort_order": 1 }
      ]
    },
    {
      "id": 2,
      "code": "DINNER_MENU",
      "sort_order": 2,
      "locales": [
        { "id": 3, "locale_id": 1, "name": "Dinner Menu", "description": "Available from 18:00 to 22:00", "sort_order": 1 }
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

### Update Menu

Updates the mutable fields of an existing menu. `code` is immutable and cannot be changed. Locale translations are managed separately via the Menu Locales API.

**`PUT /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Menu ID |

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

### Delete Menu

Soft-deletes a menu (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Menu ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Menu Locales

Manage locale-specific translations for a menu. The `{menu-id}` in all paths must refer to an existing active menu.

---

### Create Menu Locale

**`POST /api/v1/menus/{menu-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Lunch Menu",
  "description": "Available from 12:00 to 15:00",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per menu |
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

### Update Menu Locale

Updates the translation fields. The locale cannot be changed; use delete + create to switch locale.

**`PUT /api/v1/menus/{menu-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu locale ID |

#### Request Body

```json
{
  "name": "Lunch Menu",
  "description": "Available from 12:00 to 15:00 - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
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

### Delete Menu Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu locale ID |

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

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
