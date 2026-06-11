# Dish API

Base URL: `/api/v1`

Dishes are standalone resources. Dish names and descriptions are locale-specific and embedded in every response via the `locales` array. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                          | Description          |
|--------|-----------------------------------------------|----------------------|
| POST   | `/api/v1/dishes`                              | Create a dish        |
| GET    | `/api/v1/dishes/{id}`                         | Get a dish           |
| GET    | `/api/v1/dishes`                              | List all dishes      |
| PUT    | `/api/v1/dishes/{id}`                         | Update a dish        |
| DELETE | `/api/v1/dishes/{id}`                         | Delete a dish        |
| POST   | `/api/v1/dishes/{dish-id}/locales`            | Create a dish locale |
| PUT    | `/api/v1/dishes/{dish-id}/locales/{id}`       | Update a dish locale |
| DELETE | `/api/v1/dishes/{dish-id}/locales/{id}`       | Delete a dish locale |

---

## Data Model

### Dish

| Field        | Type    | Required | Constraints                            | Description                               |
|--------------|---------|----------|----------------------------------------|-------------------------------------------|
| `id`         | Long    | —        | read-only                              | Auto-generated identifier                 |
| `code`       | String  | Yes      | max 50 chars, not blank, create-only   | Unique dish code (e.g., `BURGER_CLASSIC`) |
| `sort_order` | Integer | Yes      | not null                               | Display order                             |
| `locales`    | Array   | —        | read-only                              | All locale translations for this dish     |

### Dish Locale

| Field         | Type    | Required | Constraints              | Description                         |
|---------------|---------|----------|--------------------------|-------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier           |
| `locale_id`   | Long    | Yes      | not null, must exist     | ID of an existing active locale     |
| `name`        | String  | Yes      | not blank, max 255 chars | Localized name of the dish          |
| `description` | String  | No       | —                        | Localized description               |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale entry |

---

## Create Dish

`POST /api/v1/dishes`

Creates a dish with optional embedded locale translations.

### Request Body

```json
{
  "code": "BURGER_CLASSIC",
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Classic Burger",
      "description": "A juicy beef patty with fresh vegetables.",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "ক্লাসিক বার্গার",
      "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।",
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

## Get Dish

`GET /api/v1/dishes/{id}`

Returns a single dish with all its locale translations.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `id`      | Long | ID of the dish |

### Response `200 OK`

```json
{
  "dish": {
    "id": 1,
    "code": "BURGER_CLASSIC",
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Classic Burger",
        "description": "A juicy beef patty with fresh vegetables.",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "ক্লাসিক বার্গার",
        "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।",
        "sort_order": 2
      }
    ]
  }
}
```

---

## List All Dishes

`GET /api/v1/dishes`

Returns a paginated list of all active (non-deleted) dishes.

### Query Parameters

| Parameter  | Type    | Default | Constraints                            | Description              |
|------------|---------|---------|----------------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "code": "BURGER_CLASSIC",
      "sort_order": 1,
      "locales": [
        { "id": 1, "locale_id": 1, "name": "Classic Burger", "description": "A juicy beef patty with fresh vegetables.", "sort_order": 1 },
        { "id": 2, "locale_id": 2, "name": "ক্লাসিক বার্গার", "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।", "sort_order": 2 }
      ]
    },
    {
      "id": 2,
      "code": "VEGGIE_WRAP",
      "sort_order": 2,
      "locales": [
        { "id": 3, "locale_id": 1, "name": "Veggie Wrap", "description": "Fresh vegetables in a soft wrap.", "sort_order": 1 }
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

## Update Dish

`PUT /api/v1/dishes/{id}`

Updates `sort_order`. The `code` is immutable and cannot be changed. Locale translations are managed via the dish locale endpoints.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `id`      | Long | ID of the dish |

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

## Delete Dish

`DELETE /api/v1/dishes/{id}`

Soft-deletes the dish. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `id`      | Long | ID of the dish |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Dish Locales

Dish locale endpoints manage per-locale translations for a dish. The `{dish-id}` must reference an existing active dish.

---

### Create Dish Locale

`POST /api/v1/dishes/{dish-id}/locales`

Adds a new locale translation to an existing dish.

#### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `dish-id` | Long | ID of the dish |

#### Request Body

```json
{
  "locale_id": 3,
  "name": "Klassischer Burger",
  "description": "Ein saftiger Rindfleisch-Patty mit frischem Gemüse.",
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
  "id": 3
}
```

---

### Update Dish Locale

`PUT /api/v1/dishes/{dish-id}/locales/{id}`

Updates an existing locale translation. The `locale_id` cannot be changed; use delete + create to switch locale.

#### Path Parameters

| Parameter | Type | Description           |
|-----------|------|-----------------------|
| `dish-id` | Long | ID of the dish        |
| `id`      | Long | ID of the dish locale |

#### Request Body

```json
{
  "name": "Klassischer Burger",
  "description": "Updated description.",
  "sort_order": 3
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

### Delete Dish Locale

`DELETE /api/v1/dishes/{dish-id}/locales/{id}`

Soft-deletes a dish locale translation.

#### Path Parameters

| Parameter | Type | Description           |
|-----------|------|-----------------------|
| `dish-id` | Long | ID of the dish        |
| `id`      | Long | ID of the dish locale |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 3
}
```

---

## Error Responses

| HTTP Status | Cause                                                              |
|-------------|--------------------------------------------------------------------|
| 400         | Missing required fields, blank code, or invalid sort field         |
| 404         | Dish or dish locale not found or already deleted                   |
| 401         | JWT token missing or invalid                                       |
| 403         | Authenticated user lacks permission                                |
