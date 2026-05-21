# Dining Spaces API

Base URL: `/api/v1/dining-spaces`

Dining spaces represent individual bookable or non-bookable areas within a restaurant (e.g., a private room, a rooftop section, a bar lounge). Each space references a dining space type and optionally a floor. Names and descriptions are locale-specific and managed via a separate locale sub-resource. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                                             | Description                      |
|--------|------------------------------------------------------------------|----------------------------------|
| POST   | `/api/v1/dining-spaces`                                          | Create a dining space            |
| GET    | `/api/v1/dining-spaces`                                          | List all dining spaces           |
| GET    | `/api/v1/dining-spaces/{id}`                                     | Get a dining space               |
| PUT    | `/api/v1/dining-spaces/{id}`                                     | Update a dining space            |
| DELETE | `/api/v1/dining-spaces/{id}`                                     | Delete a dining space            |
| POST   | `/api/v1/dining-spaces/{dining-space-id}/locales`                | Create a dining space locale     |
| PUT    | `/api/v1/dining-spaces/{dining-space-id}/locales/{id}`           | Update a dining space locale     |
| DELETE | `/api/v1/dining-spaces/{dining-space-id}/locales/{id}`           | Delete a dining space locale     |

---

## Authentication

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Data Model

### Dining Space

| Field                  | Type    | Required | Constraints                         | Description                                            |
|------------------------|---------|----------|-------------------------------------|--------------------------------------------------------|
| `id`                   | Long    | —        | read-only                           | Auto-generated identifier                              |
| `dining_space_type_id` | Long    | Yes      | immutable, must reference active type | ID of the dining space type — set at creation only   |
| `floor_id`             | Long    | No       | immutable, must reference active floor | ID of the floor — set at creation only; `null` if unassigned |
| `code`                 | String  | Yes      | immutable, max 50 chars             | Short identifier — set at creation only (e.g., `MAIN_HALL`) |
| `sort_order`           | Integer | Yes      | not null                            | Display order                                          |
| `capacity`             | Integer | Yes      | not null                            | Maximum number of guests                               |
| `is_bookable`          | Boolean | Yes      | not null                            | Whether the space can be reserved                      |
| `locales`              | Array   | —        | read-only                           | All locale translations for this dining space          |

### Dining Space Locale

| Field         | Type    | Required | Constraints    | Description                              |
|---------------|---------|----------|----------------|------------------------------------------|
| `id`          | Long    | —        | read-only      | Auto-generated identifier                |
| `locale_id`   | Long    | Yes      | must exist     | ID of an existing active locale          |
| `name`        | String  | Yes      | max 255 chars  | Localized name of the dining space       |
| `description` | String  | No       | unlimited      | Localized description                    |
| `sort_order`  | Integer | Yes      | not null       | Display order for this locale entry      |

---

## Create Dining Space

`POST /api/v1/dining-spaces`

Creates a dining space along with optional locale-specific translations. The `dining_space_type_id`, `floor_id`, and `code` are immutable — they cannot be changed after creation.

### Request Body

```json
{
  "dining_space_type_id": 1,
  "floor_id": 1,
  "code": "MAIN_HALL",
  "sort_order": 1,
  "capacity": 80,
  "is_bookable": true,
  "locales": [
    {
      "locale_id": 1,
      "name": "Main Hall",
      "description": "The primary indoor dining area on the ground floor.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "মেইন হল",
      "description": "গ্রাউন্ড ফ্লোরে প্রাথমিক ইনডোর ডাইনিং এলাকা।",
      "sort_order": 2
    }
  ]
}
```

### Request Fields

| Field                  | Type    | Required | Validation                               |
|------------------------|---------|----------|------------------------------------------|
| `dining_space_type_id` | Long    | Yes      | Not null, must reference active type     |
| `floor_id`             | Long    | No       | Must reference active floor if provided  |
| `code`                 | String  | Yes      | Not blank, max 50 chars                  |
| `sort_order`           | Integer | Yes      | Not null                                 |
| `capacity`             | Integer | Yes      | Not null                                 |
| `is_bookable`          | Boolean | Yes      | Not null                                 |
| `locales`              | Array   | No       | See locale fields below                  |

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

## Get Dining Space

`GET /api/v1/dining-spaces/{id}`

Returns a single dining space with all its locale translations.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `id`      | Long | ID of the dining space |

### Response `200 OK`

```json
{
  "dining_space": {
    "id": 1,
    "dining_space_type_id": 1,
    "floor_id": 1,
    "code": "MAIN_HALL",
    "sort_order": 1,
    "capacity": 80,
    "is_bookable": true,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Main Hall",
        "description": "The primary indoor dining area on the ground floor.",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "মেইন হল",
        "description": "গ্রাউন্ড ফ্লোরে প্রাথমিক ইনডোর ডাইনিং এলাকা।",
        "sort_order": 2
      }
    ]
  }
}
```

> `floor_id` is `null` when no floor is assigned.

---

## List All Dining Spaces

`GET /api/v1/dining-spaces`

Returns a paginated list of active (non-deleted) dining spaces. Each item includes all locale translations.

### Query Parameters

| Parameter  | Type   | Default | Constraints                              | Description              |
|------------|--------|---------|------------------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                                     | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                                   | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `code`, `sortOrder`, `createdAt`   | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                            | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "dining_space_type_id": 1,
      "floor_id": 1,
      "code": "MAIN_HALL",
      "sort_order": 1,
      "capacity": 80,
      "is_bookable": true,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Main Hall",
          "description": "The primary indoor dining area on the ground floor.",
          "sort_order": 1
        }
      ]
    },
    {
      "id": 2,
      "dining_space_type_id": 2,
      "floor_id": null,
      "code": "GF_OUTDOOR",
      "sort_order": 2,
      "capacity": 40,
      "is_bookable": true,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Ground Floor Outdoor",
          "description": "Open-air seating on the ground floor front area.",
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

## Update Dining Space

`PUT /api/v1/dining-spaces/{id}`

Updates the mutable fields of an existing dining space. The `code`, `dining_space_type_id`, and `floor_id` are set at creation time and cannot be changed. Locale translations are managed via the dining space locale endpoints.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `id`      | Long | ID of the dining space |

### Request Body

```json
{
  "sort_order": 1,
  "capacity": 100,
  "is_bookable": true
}
```

### Request Fields

| Field         | Type    | Required | Validation |
|---------------|---------|----------|------------|
| `sort_order`  | Integer | Yes      | Not null   |
| `capacity`    | Integer | Yes      | Not null   |
| `is_bookable` | Boolean | Yes      | Not null   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Dining Space

`DELETE /api/v1/dining-spaces/{id}`

Soft-deletes the dining space. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `id`      | Long | ID of the dining space |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dining Space Locales

Dining space locale endpoints manage per-locale translations. The `{dining-space-id}` path parameter must reference an existing, active dining space.

---

### Create Dining Space Locale

`POST /api/v1/dining-spaces/{dining-space-id}/locales`

Adds a new locale translation to an existing dining space.

#### Path Parameters

| Parameter         | Type | Description            |
|-------------------|------|------------------------|
| `dining-space-id` | Long | ID of the dining space |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Main Hall",
  "description": "The primary indoor dining area on the ground floor.",
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

### Update Dining Space Locale

`PUT /api/v1/dining-spaces/{dining-space-id}/locales/{id}`

Updates the name, description, and sort order of an existing locale translation. The locale language cannot be changed — delete and recreate to change the locale.

#### Path Parameters

| Parameter         | Type | Description                   |
|-------------------|------|-------------------------------|
| `dining-space-id` | Long | ID of the dining space        |
| `id`              | Long | ID of the dining space locale |

#### Request Body

```json
{
  "name": "Main Hall",
  "description": "Updated description for the main dining area.",
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
  "id": 1
}
```

---

### Delete Dining Space Locale

`DELETE /api/v1/dining-spaces/{dining-space-id}/locales/{id}`

Soft-deletes a dining space locale. The record is not removed from the database but will no longer appear in any response.

#### Path Parameters

| Parameter         | Type | Description                   |
|-------------------|------|-------------------------------|
| `dining-space-id` | Long | ID of the dining space        |
| `id`              | Long | ID of the dining space locale |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
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
  "message": "DiningSpace not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                                              |
|-------------|----------------------------|------------------------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                                      |
| 401         | `UNAUTHORIZED`             | JWT token is missing or invalid                                                    |
| 403         | `FORBIDDEN`                | Authenticated user lacks permission                                                |
| 404         | `ENTITY_NOT_FOUND`         | Dining space, dining space type, floor, or locale not found, or already deleted    |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code)                                         |
