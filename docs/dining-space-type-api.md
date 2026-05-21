# Dining Space Types API

Base URL: `/api/v1/dining-space-types`

Dining space types categorize the seating environment of a restaurant (e.g., Indoor, Outdoor, Terrace, VIP). Names and descriptions are locale-specific and are embedded in every response via the `locales` array. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                                          | Description                       |
|--------|---------------------------------------------------------------|-----------------------------------|
| POST   | `/api/v1/dining-space-types`                                  | Create a dining space type        |
| GET    | `/api/v1/dining-space-types`                                  | List all dining space types       |
| GET    | `/api/v1/dining-space-types/{id}`                             | Get a dining space type           |
| PUT    | `/api/v1/dining-space-types/{id}`                             | Update a dining space type        |
| DELETE | `/api/v1/dining-space-types/{id}`                             | Delete a dining space type        |
| POST   | `/api/v1/dining-space-types/{dining-space-type-id}/locales`   | Create a dining space type locale |
| PUT    | `/api/v1/dining-space-types/{dining-space-type-id}/locales/{id}` | Update a dining space type locale |
| DELETE | `/api/v1/dining-space-types/{dining-space-type-id}/locales/{id}` | Delete a dining space type locale |

---

## Authentication

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Data Model

### Dining Space Type

| Field        | Type    | Required | Constraints            | Description                                              |
|--------------|---------|----------|------------------------|----------------------------------------------------------|
| `id`         | Long    | —        | read-only              | Auto-generated identifier                                |
| `code`       | String  | Yes      | max 50 chars, unique   | Short identifier set at creation (e.g., `INDOOR`, `VIP`) |
| `sort_order` | Integer | Yes      | not null, default `0`  | Display order                                            |
| `locales`    | Array   | —        | read-only              | All locale translations for this dining space type       |

### Dining Space Type Locale

| Field         | Type    | Required | Constraints    | Description                             |
|---------------|---------|----------|----------------|-----------------------------------------|
| `id`          | Long    | —        | read-only      | Auto-generated identifier               |
| `locale_id`   | Long    | Yes      | must exist     | ID of an existing active locale         |
| `name`        | String  | Yes      | max 255 chars  | Localized name of the dining space type |
| `description` | String  | No       | unlimited      | Localized description                   |
| `sort_order`  | Integer | Yes      | not null       | Display order for this locale entry     |

---

## Create Dining Space Type

`POST /api/v1/dining-space-types`

Creates a dining space type along with its locale-specific translations in one request. All provided `locale_id` values must reference existing, active locales.

### Request Body

```json
{
  "code": "INDOOR",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Indoor",
      "description": "Enclosed dining area inside the restaurant.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "ইনডোর",
      "description": "রেস্তোরাঁর ভেতরে আবদ্ধ খাবার এলাকা।",
      "sort_order": 2
    }
  ]
}
```

### Request Fields

| Field        | Type    | Required | Validation              |
|--------------|---------|----------|-------------------------|
| `code`       | String  | Yes      | Not blank, max 50 chars |
| `sort_order` | Integer | Yes      | Not null                |
| `locales`    | Array   | No       | See locale fields below |

**Locale fields (`locales[]`):**

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 255 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

## Get Dining Space Type

`GET /api/v1/dining-space-types/{id}`

Returns a single dining space type with all its locale translations.

### Path Parameters

| Parameter | Type | Description                  |
|-----------|------|------------------------------|
| `id`      | Long | ID of the dining space type  |

### Response `200 OK`

```json
{
  "dining_space_type": {
    "id": 1,
    "code": "INDOOR",
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Indoor",
        "description": "Enclosed dining area inside the restaurant.",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "ইনডোর",
        "description": "রেস্তোরাঁর ভেতরে আবদ্ধ খাবার এলাকা।",
        "sort_order": 2
      }
    ]
  }
}
```

---

## List All Dining Space Types

`GET /api/v1/dining-space-types`

Returns a paginated list of active (non-deleted) dining space types. Each item includes all locale translations.

### Query Parameters

| Parameter  | Type   | Default | Constraints                            | Description              |
|------------|--------|---------|----------------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "INDOOR",
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Indoor",
          "description": "Enclosed dining area inside the restaurant.",
          "sort_order": 1
        },
        {
          "id": 2,
          "locale_id": 2,
          "name": "ইনডোর",
          "description": "রেস্তোরাঁর ভেতরে আবদ্ধ খাবার এলাকা।",
          "sort_order": 2
        }
      ]
    },
    {
      "id": 2,
      "code": "OUTDOOR",
      "sort_order": 2,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Outdoor",
          "description": "Open-air dining area outside the restaurant.",
          "sort_order": 1
        }
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

## Update Dining Space Type

`PUT /api/v1/dining-space-types/{id}`

Updates `sort_order`. The `code` field is set at creation time and cannot be changed. Locale translations are managed via the dining space type locale endpoints.

### Path Parameters

| Parameter | Type | Description                  |
|-----------|------|------------------------------|
| `id`      | Long | ID of the dining space type  |

### Request Body

```json
{
  "sort_order": 2
}
```

### Request Fields

| Field        | Type    | Required | Validation |
|--------------|---------|----------|------------|
| `sort_order` | Integer | Yes      | Not null   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dining Space Type

`DELETE /api/v1/dining-space-types/{id}`

Soft-deletes the dining space type. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description                  |
|-----------|------|------------------------------|
| `id`      | Long | ID of the dining space type  |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dining Space Type Locales

Dining space type locale endpoints manage per-locale translations. The `{dining-space-type-id}` path parameter must reference an existing, active dining space type.

---

### Create Dining Space Type Locale

`POST /api/v1/dining-space-types/{dining-space-type-id}/locales`

Adds a new locale translation to an existing dining space type.

#### Path Parameters

| Parameter              | Type | Description                  |
|------------------------|------|------------------------------|
| `dining-space-type-id` | Long | ID of the dining space type  |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Indoor",
  "description": "Enclosed dining area inside the restaurant.",
  "sort_order": 1
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 255 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 3
}
```

---

### Update Dining Space Type Locale

`PUT /api/v1/dining-space-types/{dining-space-type-id}/locales/{id}`

Updates the name, description, and sort order of an existing locale translation. The locale language cannot be changed — delete and recreate to change the locale.

#### Path Parameters

| Parameter              | Type | Description                          |
|------------------------|------|--------------------------------------|
| `dining-space-type-id` | Long | ID of the dining space type          |
| `id`                   | Long | ID of the dining space type locale   |

#### Request Body

```json
{
  "name": "Indoor",
  "description": "Updated description.",
  "sort_order": 1
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `name`        | String  | Yes      | Not blank, max 255 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

### Delete Dining Space Type Locale

`DELETE /api/v1/dining-space-types/{dining-space-type-id}/locales/{id}`

Soft-deletes a dining space type locale. The record is not removed from the database but will no longer appear in any response.

#### Path Parameters

| Parameter              | Type | Description                          |
|------------------------|------|--------------------------------------|
| `dining-space-type-id` | Long | ID of the dining space type          |
| `id`                   | Long | ID of the dining space type locale   |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

## Error Responses

All errors follow a common structure:

```json
{
  "request_id": "abc-123",
  "status": 404,
  "error": "ENTITY_NOT_FOUND",
  "message": "DiningSpaceType not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                         |
|-------------|----------------------------|-------------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                                 |
| 401         | `UNAUTHORIZED`             | JWT token is missing or invalid                                               |
| 403         | `FORBIDDEN`                | Authenticated user lacks permission                                           |
| 404         | `ENTITY_NOT_FOUND`         | Dining space type or locale not found, or already deleted                     |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                                    |
