# Cities API

Base URL: `/api/v1/cities`

Cities represent geographic cities belonging to a country. City names and descriptions are locale-specific and managed via the [City Locales sub-resource](#city-locales-sub-resource). All records support soft-delete — deleted records are hidden from all responses.

> **Related:** Locale-specific translations for a city are managed individually via the [City Locales sub-resource](#city-locales-sub-resource).

---

## Endpoints

| Method | Path                                            | Description                   |
|--------|-------------------------------------------------|-------------------------------|
| POST   | `/api/v1/cities`                                | Create a city                 |
| GET    | `/api/v1/cities`                                | List all cities               |
| GET    | `/api/v1/cities/{id}`                           | Get a city                    |
| PUT    | `/api/v1/cities/{id}`                           | Update a city                 |
| DELETE | `/api/v1/cities/{id}`                           | Delete a city                 |
| POST   | `/api/v1/cities/{city-id}/locales`              | Add a locale to a city        |
| GET    | `/api/v1/cities/{city-id}/locales`              | List locales for a city       |
| GET    | `/api/v1/cities/{city-id}/locales/{id}`         | Get a specific locale entry   |
| PUT    | `/api/v1/cities/{city-id}/locales/{id}`         | Update a locale entry         |
| DELETE | `/api/v1/cities/{city-id}/locales/{id}`         | Delete a locale entry         |

---

## Data Model

### City

| Field        | Type    | Required | Constraints             | Description                              |
|--------------|---------|----------|-------------------------|------------------------------------------|
| `id`         | Long    | —        | read-only               | Auto-generated identifier                |
| `country_id` | Long    | Yes      | must exist              | ID of the country this city belongs to   |
| `code`       | String  | No       | max 50 chars            | Short city code (e.g., `DHA`, `CGP`)     |
| `sort_order` | Integer | Yes      | not null, default `0`   | Display order                            |
| `locales`    | Array   | No       | —                       | Locale-specific name and description     |

### City Locale (embedded in create)

| Field         | Type    | Required | Constraints           | Description                           |
|---------------|---------|----------|-----------------------|---------------------------------------|
| `locale_id`   | Long    | Yes      | must exist            | ID of an existing active locale       |
| `name`        | String  | Yes      | max 255 chars         | Localized name of the city            |
| `description` | String  | No       | unlimited             | Localized description                 |
| `sort_order`  | Integer | Yes      | not null              | Display order for this locale entry   |

---

## Create City

`POST /api/v1/cities`

Creates a city along with its locale-specific translations in one request. All provided `locale_id` values must reference existing, active locales.

### Request Body

```json
{
  "country_id": 1,
  "code": "DHA",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Dhaka",
      "description": "Capital city of Bangladesh.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "ঢাকা",
      "description": "বাংলাদেশের রাজধানী।",
      "sort_order": 2
    }
  ]
}
```

### Request Fields

| Field        | Type    | Required | Validation              |
|--------------|---------|----------|-------------------------|
| `country_id` | Long    | Yes      | Not null, must exist    |
| `code`       | String  | No       | max 50 chars            |
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

## Get City

`GET /api/v1/cities/{id}`

### Path Parameters

| Parameter | Type | Description     |
|-----------|------|-----------------|
| `id`      | Long | ID of the city  |

### Response `200 OK`

```json
{
  "city": {
    "id": 1,
    "country_id": 1,
    "code": "DHA",
    "sort_order": 1
  }
}
```

---

## List All Cities

`GET /api/v1/cities`

Returns a paginated list of active (non-deleted) cities.

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
      "country_id": 1,
      "code": "DHA",
      "sort_order": 1
    },
    {
      "id": 2,
      "country_id": 1,
      "code": "CGP",
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

## Update City

`PUT /api/v1/cities/{id}`

Replaces `code` and `sort_order`. The `country_id` is fixed at creation and cannot be changed. To manage locale translations use the [locales sub-resource](#city-locales-sub-resource).

### Path Parameters

| Parameter | Type | Description     |
|-----------|------|-----------------|
| `id`      | Long | ID of the city  |

### Request Body

```json
{
  "code": "DHA",
  "sort_order": 1
}
```

### Request Fields

| Field        | Type    | Required | Validation   |
|--------------|---------|----------|--------------|
| `code`       | String  | No       | max 50 chars |
| `sort_order` | Integer | Yes      | Not null     |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete City

`DELETE /api/v1/cities/{id}`

Soft-deletes the city. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description     |
|-----------|------|-----------------|
| `id`      | Long | ID of the city  |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## City Locales Sub-resource

Manages locale-specific translations for a city individually. Use these endpoints to add, update, or remove a single locale entry after the city has been created.

Base path: `/api/v1/cities/{city-id}/locales`

### Add Locale

`POST /api/v1/cities/{city-id}/locales`

#### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `city-id` | Long | ID of the city |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Dhaka",
  "description": "Hauptstadt von Bangladesch.",
  "sort_order": 3
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
  "id": 5
}
```

---

### Get Locale

`GET /api/v1/cities/{city-id}/locales/{id}`

#### Path Parameters

| Parameter | Type | Description             |
|-----------|------|-------------------------|
| `city-id` | Long | ID of the city          |
| `id`      | Long | ID of the locale entry  |

#### Response `200 OK`

```json
{
  "city_locale": {
    "id": 5,
    "locale_id": 3,
    "name": "Dhaka",
    "description": "Hauptstadt von Bangladesch.",
    "sort_order": 3
  }
}
```

---

### List Locales

`GET /api/v1/cities/{city-id}/locales`

Returns a paginated list of active locale entries for the city.

#### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `city-id` | Long | ID of the city |

#### Query Parameters

| Parameter  | Type   | Default | Constraints                            | Description              |
|------------|--------|---------|----------------------------------------|--------------------------|
| `page`     | int    | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | int    | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String | `id`    | `id`, `name`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "locale_id": 1,
      "name": "Dhaka",
      "description": "Capital city of Bangladesh.",
      "sort_order": 1
    },
    {
      "id": 5,
      "locale_id": 3,
      "name": "Dhaka",
      "description": "Hauptstadt von Bangladesch.",
      "sort_order": 3
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

### Update Locale

`PUT /api/v1/cities/{city-id}/locales/{id}`

#### Path Parameters

| Parameter | Type | Description             |
|-----------|------|-------------------------|
| `city-id` | Long | ID of the city          |
| `id`      | Long | ID of the locale entry  |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Dhaka",
  "description": "Aktualisierte deutsche Beschreibung.",
  "sort_order": 3
}
```

#### Request Fields

| Field         | Type    | Required | Validation               |
|---------------|---------|----------|--------------------------|
| `locale_id`   | Long    | Yes      | Not null, must exist     |
| `name`        | String  | Yes      | Not blank, max 255 chars |
| `description` | String  | No       | —                        |
| `sort_order`  | Integer | Yes      | Not null                 |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 5
}
```

---

### Delete Locale

`DELETE /api/v1/cities/{city-id}/locales/{id}`

Soft-deletes the locale entry.

#### Path Parameters

| Parameter | Type | Description             |
|-----------|------|-------------------------|
| `city-id` | Long | ID of the city          |
| `id`      | Long | ID of the locale entry  |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 5
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
  "message": "City not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                          |
|-------------|----------------------------|----------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                  |
| 404         | `ENTITY_NOT_FOUND`         | City, country, or locale not found, or already deleted         |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate city-locale pair)         |
