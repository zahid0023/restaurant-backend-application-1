# Menu Item API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Menu Items

Menu items are nested under a menu section. Both `{menu-id}` and `{section-id}` in all paths must refer to existing active resources.

---

### Create Menu Item

Creates a new menu item with optional embedded locale translations.

**`POST /api/v1/menus/{menu-id}/sections/{section-id}/items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |

#### Request Body

```json
{
  "code": "ITEM_001",
  "sort_order": 1,
  "price": 12.99,
  "is_available": true,
  "is_featured": false,
  "is_veg": true,
  "locales": [
    {
      "locale_id": 1,
      "name": "Grilled Chicken",
      "description": "Tender grilled chicken with herbs",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Izgara Tavuk",
      "description": "Otlu ızgara tavuk",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |
| `price` | decimal | yes | greater than 0, max 16 integer digits, 2 fraction digits |
| `is_available` | boolean | no | defaults to `true` |
| `is_featured` | boolean | no | defaults to `false` |
| `is_veg` | boolean | no | nullable |
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

### Get Menu Item by ID

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu item ID |

#### Response `200 OK`

```json
{
  "menu_item": {
    "id": 1,
    "menu_section_id": 1,
    "code": "ITEM_001",
    "sort_order": 1,
    "price": 12.99,
    "is_available": true,
    "is_featured": false,
    "is_veg": true
  }
}
```

---

### List Menu Items

Returns a paginated list of all active items for a given menu section.

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items`**

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
| `sort_by` | string | `id` | `id`, `code`, `sortOrder`, `price`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "menu_section_id": 1,
      "code": "ITEM_001",
      "sort_order": 1,
      "price": 12.99,
      "is_available": true,
      "is_featured": false,
      "is_veg": true
    },
    {
      "id": 2,
      "menu_section_id": 1,
      "code": "ITEM_002",
      "sort_order": 2,
      "price": 8.50,
      "is_available": true,
      "is_featured": true,
      "is_veg": false
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

### Update Menu Item

Updates the fields of an existing menu item. Locale translations are managed separately via the Menu Item Locales API.

**`PUT /api/v1/menus/{menu-id}/sections/{section-id}/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu item ID |

#### Request Body

```json
{
  "code": "ITEM_001",
  "sort_order": 1,
  "price": 14.99,
  "is_available": true,
  "is_featured": true,
  "is_veg": true
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars, not blank |
| `sort_order` | integer | yes | |
| `price` | decimal | yes | greater than 0, max 16 integer digits, 2 fraction digits |
| `is_available` | boolean | no | defaults to `true` |
| `is_featured` | boolean | no | defaults to `false` |
| `is_veg` | boolean | no | nullable |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Menu Item

Soft-deletes a menu item (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/sections/{section-id}/items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `id` | long | Menu item ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Menu Item Locales

Manage locale-specific translations for a menu item. All path parameters must refer to existing active resources.

---

### Create Menu Item Locale

**`POST /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Grilled Chicken",
  "description": "Tender grilled chicken with herbs",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per menu item |
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

### Get Menu Item Locale by ID

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `id` | long | Menu item locale ID |

#### Response `200 OK`

```json
{
  "menu_item_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Grilled Chicken",
    "description": "Tender grilled chicken with herbs",
    "sort_order": 1
  }
}
```

---

### List Menu Item Locales

Returns a paginated list of all active locale translations for a given menu item.

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |

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
      "name": "Grilled Chicken",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Izgara Tavuk",
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

### Update Menu Item Locale

**`PUT /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `id` | long | Menu item locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Grilled Chicken",
  "description": "Tender grilled chicken with herbs - updated",
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

### Delete Menu Item Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `id` | long | Menu item locale ID |

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
  "message": "MenuItem not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "price": "must be greater than 0",
    "code": "must not be blank"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
