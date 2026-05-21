# Floors API

Base URL: `/api/v1/floors`

Floors represent physical levels or sections of a restaurant (e.g., Ground Floor, Rooftop, VIP Lounge). Floor names and descriptions are locale-specific and are embedded in every response via the `locales` array. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                        | Description              |
|--------|---------------------------------------------|--------------------------|
| POST   | `/api/v1/floors`                            | Create a floor           |
| GET    | `/api/v1/floors`                            | List all floors          |
| GET    | `/api/v1/floors/{id}`                       | Get a floor              |
| PUT    | `/api/v1/floors/{id}`                       | Update a floor           |
| DELETE | `/api/v1/floors/{id}`                       | Delete a floor           |
| POST   | `/api/v1/floors/{floor-id}/locales`         | Create a floor locale    |
| PUT    | `/api/v1/floors/{floor-id}/locales/{id}`    | Update a floor locale    |
| DELETE | `/api/v1/floors/{floor-id}/locales/{id}`    | Delete a floor locale    |

---

## Authentication

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Data Model

### Floor

| Field        | Type    | Required | Constraints            | Description                                    |
|--------------|---------|----------|------------------------|------------------------------------------------|
| `id`         | Long    | —        | read-only              | Auto-generated identifier                      |
| `code`       | String  | Yes      | max 50 chars, unique   | Short identifier set at creation (e.g., `GF`, `FF`, `VIP`) |
| `sort_order` | Integer | Yes      | not null, default `0`  | Display order                                  |
| `locales`    | Array   | —        | read-only              | All locale translations for this floor         |

### Floor Locale

| Field         | Type    | Required | Constraints            | Description                              |
|---------------|---------|----------|------------------------|------------------------------------------|
| `id`          | Long    | —        | read-only              | Auto-generated identifier                |
| `locale_id`   | Long    | Yes      | must exist             | ID of an existing active locale          |
| `name`        | String  | Yes      | max 255 chars          | Localized name of the floor              |
| `description` | String  | No       | unlimited              | Localized description                    |
| `sort_order`  | Integer | Yes      | not null               | Display order for this locale entry      |

---

## Create Floor

`POST /api/v1/floors`

Creates a floor along with its locale-specific translations in one request. All provided `locale_id` values must reference existing, active locales.

### Request Body

```json
{
  "code": "GF",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Ground Floor",
      "description": "Main dining area on the ground level.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "গ্রাউন্ড ফ্লোর",
      "description": "মূল খাবার এলাকা।",
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

## Get Floor

`GET /api/v1/floors/{id}`

Returns a single floor with all its locale translations.

### Path Parameters

| Parameter | Type | Description      |
|-----------|------|------------------|
| `id`      | Long | ID of the floor  |

### Response `200 OK`

```json
{
  "floor": {
    "id": 1,
    "code": "GF",
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Ground Floor",
        "description": "Main dining area on the ground level.",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "গ্রাউন্ড ফ্লোর",
        "description": "মূল খাবার এলাকা।",
        "sort_order": 2
      }
    ]
  }
}
```

---

## List All Floors

`GET /api/v1/floors`

Returns a paginated list of active (non-deleted) floors. Each item includes all locale translations.

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
      "code": "GF",
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Ground Floor",
          "description": "Main dining area on the ground level.",
          "sort_order": 1
        },
        {
          "id": 2,
          "locale_id": 2,
          "name": "গ্রাউন্ড ফ্লোর",
          "description": "মূল খাবার এলাকা।",
          "sort_order": 2
        }
      ]
    },
    {
      "id": 2,
      "code": "FF",
      "sort_order": 2,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "First Floor",
          "description": "Dining area on the first level.",
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

## Update Floor

`PUT /api/v1/floors/{id}`

Updates `sort_order`. The `code` field is set at creation time and cannot be changed. Locale translations are managed via the floor locale endpoints.

### Path Parameters

| Parameter | Type | Description      |
|-----------|------|------------------|
| `id`      | Long | ID of the floor  |

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

## Delete Floor

`DELETE /api/v1/floors/{id}`

Soft-deletes the floor. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description      |
|-----------|------|------------------|
| `id`      | Long | ID of the floor  |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Floor Locales

Floor locale endpoints manage per-locale translations for a floor. The `{floor-id}` path parameter must reference an existing, active floor.

---

### Create Floor Locale

`POST /api/v1/floors/{floor-id}/locales`

Adds a new locale translation to an existing floor.

#### Path Parameters

| Parameter  | Type | Description      |
|------------|------|------------------|
| `floor-id` | Long | ID of the floor  |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Ground Floor",
  "description": "Main dining area on the ground level.",
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

### Update Floor Locale

`PUT /api/v1/floors/{floor-id}/locales/{id}`

Updates the name, description, and sort order of an existing floor locale. The locale language cannot be changed — delete and recreate to change the locale.

#### Path Parameters

| Parameter  | Type | Description             |
|------------|------|-------------------------|
| `floor-id` | Long | ID of the floor         |
| `id`       | Long | ID of the floor locale  |

#### Request Body

```json
{
  "name": "Ground Floor",
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

### Delete Floor Locale

`DELETE /api/v1/floors/{floor-id}/locales/{id}`

Soft-deletes a floor locale. The record is not removed from the database but will no longer appear in any response.

#### Path Parameters

| Parameter  | Type | Description             |
|------------|------|-------------------------|
| `floor-id` | Long | ID of the floor         |
| `id`       | Long | ID of the floor locale  |

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
  "message": "Floor not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                    |
|-------------|----------------------------|--------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                            |
| 401         | `UNAUTHORIZED`             | JWT token is missing or invalid                                          |
| 403         | `FORBIDDEN`                | Authenticated user lacks permission                                      |
| 404         | `ENTITY_NOT_FOUND`         | Floor or floor locale not found, or already deleted                      |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                               |
