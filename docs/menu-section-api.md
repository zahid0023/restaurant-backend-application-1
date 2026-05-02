# Menu Section API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Menu Sections

Menu sections are nested under a menu. The `{menu-id}` in all paths must refer to an existing active menu.

---

### Create Menu Section

Creates a new menu section with optional embedded locale translations.

**`POST /api/v1/menus/{menu-id}/sections`**

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
      "description": "Appetizers and small plates",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Başlangıçlar",
      "description": "Mezeler ve küçük tabaklar",
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

### Get Menu Section by ID

**`GET /api/v1/menus/{menu-id}/sections/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu section ID |

#### Response `200 OK`

```json
{
  "menu_section": {
    "id": 1,
    "menu_id": 1,
    "code": "STARTERS",
    "sort_order": 1
  }
}
```

---

### List Menu Sections

Returns a paginated list of all active sections for a given menu.

**`GET /api/v1/menus/{menu-id}/sections`**

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
      "code": "MAINS",
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

### Update Menu Section

Updates the fields of an existing menu section. Locale translations are managed separately via the Menu Section Locales API.

**`PUT /api/v1/menus/{menu-id}/sections/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu section ID |

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

### Delete Menu Section

Soft-deletes a menu section (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/sections/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `id` | long | Menu section ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Menu Section Locales

Manage locale-specific translations for a menu section. Both `{menu-id}` and `{section-id}` must refer to existing active resources.

---

### Create Menu Section Locale

**`POST /api/v1/menus/{menu-id}/sections/{section-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Starters",
  "description": "Appetizers and small plates",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per menu section |
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

### Get Menu Section Locale by ID

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu section locale ID |

#### Response `200 OK`

```json
{
  "menu_section_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Starters",
    "description": "Appetizers and small plates",
    "sort_order": 1
  }
}
```

---

### List Menu Section Locales

Returns a paginated list of all active locale translations for a given menu section.

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |

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
      "name": "Başlangıçlar",
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

### Update Menu Section Locale

**`PUT /api/v1/menus/{menu-id}/sections/{section-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu section locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Starters",
  "description": "Appetizers and small plates - updated",
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

### Delete Menu Section Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/sections/{section-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu section locale ID |

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
  "message": "MenuSection not found with id: 99"
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
