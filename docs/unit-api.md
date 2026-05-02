# Unit API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Units

### Create Unit

Creates a new unit with optional embedded locales.

**`POST /api/v1/units`**

#### Request Body

```json
{
  "unit_type_id": 1,
  "code": "KG",
  "is_base": true,
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Kilogram",
      "description": "Base unit of mass",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "Kilogram",
      "description": "Kütle temel birimi",
      "sort_order": 1
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `unit_type_id` | long | yes | must be an existing active unit type |
| `code` | string | yes | max 20 chars |
| `is_base` | boolean | yes | |
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

### Get Unit by ID

**`GET /api/v1/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Unit ID |

#### Response `200 OK`

```json
{
  "unit": {
    "id": 1,
    "unit_type_id": 1,
    "code": "KG",
    "is_base": true,
    "sort_order": 1
  }
}
```

---

### List Units

Returns a paginated list of all active units.

**`GET /api/v1/units`**

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
      "unit_type_id": 1,
      "code": "KG",
      "is_base": true,
      "sort_order": 1
    },
    {
      "id": 2,
      "unit_type_id": 1,
      "code": "G",
      "is_base": false,
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

### Update Unit

Updates the fields of an existing unit. Locale translations are managed separately via the Unit Locales API.

**`PUT /api/v1/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Unit ID |

#### Request Body

```json
{
  "unit_type_id": 1,
  "code": "KG",
  "is_base": true,
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `unit_type_id` | long | yes | must be an existing active unit type |
| `code` | string | yes | max 20 chars |
| `is_base` | boolean | yes | |
| `sort_order` | integer | yes | |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Unit

Soft-deletes a unit (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Unit ID |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Unit Locales

Manage locale-specific translations for a unit. The `{unit-id}` in all paths must refer to an existing active unit.

---

### Create Unit Locale

**`POST /api/v1/units/{unit-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-id` | long | Unit ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Kilogram",
  "description": "Base unit of mass",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `locale_id` | long | yes | must be an existing active locale |
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

### Get Unit Locale by ID

**`GET /api/v1/units/{unit-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-id` | long | Unit ID |
| `id` | long | Unit locale ID |

#### Response `200 OK`

```json
{
  "unit_locale": {
    "id": 1,
    "locale_id": 1,
    "name": "Kilogram",
    "description": "Base unit of mass",
    "sort_order": 1
  }
}
```

---

### List Unit Locales

Returns a paginated list of all active locales for a given unit.

**`GET /api/v1/units/{unit-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-id` | long | Unit ID |

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
      "name": "Kilogram",
      "sort_order": 1
    },
    {
      "id": 2,
      "locale_id": 2,
      "name": "Kilogram",
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

### Update Unit Locale

**`PUT /api/v1/units/{unit-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-id` | long | Unit ID |
| `id` | long | Unit locale ID |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Kilogram",
  "description": "Base unit of mass in the metric system",
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

### Delete Unit Locale

Soft-deletes a locale translation.

**`DELETE /api/v1/units/{unit-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-id` | long | Unit ID |
| `id` | long | Unit locale ID |

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
  "message": "Unit not found with id: 99"
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
    "unit_type_id": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
