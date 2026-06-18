# Unit API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Units

Unit endpoints are nested under unit types. All paths include `{unit-type-id}` which must refer to an existing active unit type.

---

### Create Unit

Creates a new unit with optional embedded locale translations.

**`POST /api/v1/unit-types/{unit-type-id}/units`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |

#### Request Body

```json
{
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

**`GET /api/v1/unit-types/{unit-type-id}/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
| `id` | long | Unit ID |

#### Response `200 OK`

```json
{
  "unit": {
    "id": 1,
    "unit_type_id": 1,
    "code": "KG",
    "is_base": true,
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Kilogram",
        "description": "Base unit of mass",
        "sort_order": 1
      }
    ]
  }
}
```

---

### List Units

Returns a paginated list of all active units for the specified unit type.

**`GET /api/v1/unit-types/{unit-type-id}/units`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |

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
      "unit_type": {
        "id": 1,
        "code": "WEIGHT",
        "sort_order": 1,
        "locales": [
          { "id": 1, "locale_id": 1, "name": "Weight", "description": "Units that measure mass", "sort_order": 1 }
        ]
      },
      "code": "KG",
      "is_base": true,
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Kilogram", "description": "Base unit of mass", "sort_order": 1 }
      ]
    },
    {
      "id": 2,
      "unit_type": {
        "id": 1,
        "code": "WEIGHT",
        "sort_order": 1,
        "locales": [
          { "id": 1, "locale_id": 1, "name": "Weight", "description": "Units that measure mass", "sort_order": 1 }
        ]
      },
      "code": "G",
      "is_base": false,
      "sort_order": 2,
      "locales": []
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

Updates the mutable fields of an existing unit. `code` is immutable after creation. Locale translations are managed separately via the Unit Locales API.

**`PUT /api/v1/unit-types/{unit-type-id}/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
| `id` | long | Unit ID |

#### Request Body

```json
{
  "is_base": true,
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
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

**`DELETE /api/v1/unit-types/{unit-type-id}/units/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
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

Manage locale-specific translations for a unit. Both `{unit-type-id}` and `{unit-id}` must refer to existing active records.

---

### Create Unit Locale

**`POST /api/v1/unit-types/{unit-type-id}/units/{unit-id}/locales`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
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

### Update Unit Locale

**`PUT /api/v1/unit-types/{unit-type-id}/units/{unit-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
| `unit-id` | long | Unit ID |
| `id` | long | Unit locale ID |

#### Request Body

```json
{
  "name": "Kilogram",
  "description": "Base unit of mass in the metric system",
  "sort_order": 1
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
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

**`DELETE /api/v1/unit-types/{unit-type-id}/units/{unit-id}/locales/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `unit-type-id` | long | Unit type ID |
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
    "is_base": "must not be null",
    "sort_order": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
