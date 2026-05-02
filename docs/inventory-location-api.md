# Inventory Location API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Inventory Locations

### Create Inventory Location

Creates a new inventory location with optional embedded locale translations.

**`POST /api/v1/inventory-locations`**

#### Request Body

```json
{
  "code": "WAREHOUSE_A",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Warehouse A",
      "description": "Main storage warehouse",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Depo A",
      "description": "Ana depolama deposu",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |
| `locales` | array | no | see locale fields below |
| `locales[].locale_id` | long | yes | must be an existing active locale |
| `locales[].name` | string | yes | max 255 chars |
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

### Get Inventory Location by ID

**`GET /api/v1/inventory-locations/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory location ID |

#### Response `200 OK`

```json
{
  "inventory_location": {
    "id": 1,
    "code": "WAREHOUSE_A",
    "sort_order": 1
  }
}
```

---

### List Inventory Locations

Returns a paginated list of all active inventory locations.

**`GET /api/v1/inventory-locations`**

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
      "code": "WAREHOUSE_A",
      "sort_order": 1
    },
    {
      "id": 2,
      "code": "WAREHOUSE_B",
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

### Update Inventory Location

Updates the fields of an existing inventory location. Locale translations are managed separately via the Inventory Location Locales API.

**`PUT /api/v1/inventory-locations/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory location ID |

#### Request Body

```json
{
  "code": "WAREHOUSE_A",
  "sort_order": 2
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `code` | string | yes | max 50 chars |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Inventory Location

Soft-deletes an inventory location (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/inventory-locations/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory location ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Inventory Location Locales

Manage locale-specific translations for an inventory location. The `{inventory-location-id}` in all paths must refer to an existing active inventory location.

---

### Create Inventory Location Locale

**`POST /api/v1/inventory-locations/{inventory-location-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `inventory-location-id` | long | Inventory location ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Warehouse A",
  "description": "Main storage warehouse",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale; unique per inventory location |
| `name` | string | yes | max 255 chars |
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

### Get Inventory Location Locale by ID

**`GET /api/v1/inventory-locations/{inventory-location-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `inventory-location-id` | long | Inventory location ID |
| `id` | long | Inventory location locale ID |

#### Response `200 OK`

```json
{
  "inventory_location_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Warehouse A",
    "description": "Main storage warehouse",
    "sort_order": 1
  }
}
```

---

### List Inventory Location Locales

Returns a paginated list of all active locales for a given inventory location.

**`GET /api/v1/inventory-locations/{inventory-location-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `inventory-location-id` | long | Inventory location ID |

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
      "name": "Warehouse A",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Depo A",
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

### Update Inventory Location Locale

**`PUT /api/v1/inventory-locations/{inventory-location-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `inventory-location-id` | long | Inventory location ID |
| `id` | long | Inventory location locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Warehouse A",
  "description": "Main storage warehouse - updated",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
| `name` | string | yes | max 255 chars |
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

### Delete Inventory Location Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/inventory-locations/{inventory-location-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `inventory-location-id` | long | Inventory location ID |
| `id` | long | Inventory location locale ID |

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
  "message": "InventoryLocation not found with id: 99"
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
