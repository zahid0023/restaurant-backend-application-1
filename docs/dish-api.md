# Dish API

Base URL: `/api/v1`

Dishes are standalone resources. Dish names and descriptions are locale-specific and embedded in every response via the
`locales` array. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                    | Auth     | Description            |
|--------|-----------------------------------------|----------|------------------------|
| POST   | `/api/v1/dishes`                        | Required | Create a dish          |
| GET    | `/api/v1/dishes/{id}`                   | Required | Get a dish             |
| GET    | `/api/v1/dishes`                        | Required | List all dishes        |
| GET    | `/api/v1/dishes/public/featured`        | Public   | List featured dishes   |
| PUT    | `/api/v1/dishes/{id}`                   | Required | Update a dish          |
| PATCH  | `/api/v1/dishes/{id}/featured`          | Required | Set dish featured flag |
| DELETE | `/api/v1/dishes/{id}`                   | Required | Delete a dish          |
| POST   | `/api/v1/dishes/{dish-id}/locales`      | Required | Create a dish locale   |
| PUT    | `/api/v1/dishes/{dish-id}/locales/{id}` | Required | Update a dish locale   |
| DELETE | `/api/v1/dishes/{dish-id}/locales/{id}` | Required | Delete a dish locale   |

---

## Data Model

### Dish

| Field         | Type    | Required | Constraints                           | Description                                       |
|---------------|---------|----------|---------------------------------------|---------------------------------------------------|
| `id`          | Long    | —        | read-only                             | Auto-generated identifier                         |
| `code`        | String  | Yes      | max 50 chars, not blank, create-only  | Unique dish code (e.g., `BURGER_CLASSIC`)         |
| `sort_order`  | Integer | Yes      | not null                              | Display order                                     |
| `is_featured` | Boolean | —        | defaults to `false`, toggle via PATCH | Whether this dish appears in the featured section |
| `locales`     | Array   | —        | read-only                             | All locale translations for this dish             |

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

Creates a dish with optional embedded locale translations. `is_featured` is not accepted in this request — it defaults
to `false` and can be changed via `PATCH /{id}/featured`.

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
    "is_featured": true,
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
      "is_featured": true,
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
    },
    {
      "id": 2,
      "code": "VEGGIE_WRAP",
      "sort_order": 2,
      "is_featured": false,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Veggie Wrap",
          "description": "Fresh vegetables in a soft wrap.",
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

## Update Dish

`PUT /api/v1/dishes/{id}`

Updates `sort_order`. The `code` is immutable and cannot be changed. Locale translations are managed via the dish locale
endpoints.

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

## List Featured Dishes

`GET /api/v1/dishes/public/featured`

**Public — no authentication required.**

Returns a paginated list of all active dishes where `is_featured = true`. Each item is a flat object containing the
dish's locale translations and its cheapest active variant's details (id, price, and a default image). Intended for
public-facing website sections (e.g. homepage banners, chef's picks).

### Query Parameters

| Parameter  | Type    | Default | Constraints                            | Description              |
|------------|---------|---------|----------------------------------------|--------------------------|
| `page`     | Integer | `0`     | >= 0                                   | Zero-based page index    |
| `size`     | Integer | `10`    | 1 – 50                                 | Number of items per page |
| `sort_by`  | String  | `id`    | `id`, `code`, `sortOrder`, `createdAt` | Field to sort by         |
| `sort_dir` | String  | `ASC`   | `ASC`, `DESC`                          | Sort direction           |

### Response Fields

| Field                   | Type       | Description                                    |
|-------------------------|------------|------------------------------------------------|
| `id`                    | Long       | Dish ID                                        |
| `locales`               | Array      | Locale translations for this dish              |
| `locales[].locale_id`   | Long       | Locale ID                                      |
| `locales[].name`        | String     | Localized dish name                            |
| `locales[].description` | String     | Localized dish description                     |
| `cheapest_variant_id`   | Long       | ID of the active variant with the lowest price |
| `price`                 | BigDecimal | Price of the cheapest active variant           |
| `img`                   | String     | Default dish image URL                         |
| `img_alt`               | String     | Default image alt text                         |

### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "locales": [
        {
          "locale_id": 1,
          "name": "Classic Burger",
          "description": "A juicy beef patty with fresh vegetables."
        },
        {
          "locale_id": 2,
          "name": "ক্লাসিক বার্গার",
          "description": "তাজা সবজি সহ রসালো বিফ প্যাটি।"
        }
      ],
      "cheapest_variant_id": 1,
      "price": 8.99,
      "img": "https://cdn.shadcnstudio.com/ss-assets/template/landing-page/bistro/image-18.png",
      "img_alt": "plate-1"
    }
  ],
  "current_page": 0,
  "total_pages": 1,
  "total_elements": 1,
  "page_size": 10,
  "has_next": false,
  "has_previous": false
}
```

---

## Set Dish Featured

`PATCH /api/v1/dishes/{id}/featured`

Marks a dish as featured or removes it from the featured section. `is_featured` defaults to `false` on creation and can
only be changed through this endpoint.

### Path Parameters

| Parameter | Type | Description    |
|-----------|------|----------------|
| `id`      | Long | ID of the dish |

### Request Body

```json
{
  "is_featured": true
}
```

### Request Fields

| Field         | Type    | Required | Validation |
|---------------|---------|----------|------------|
| `is_featured` | Boolean | Yes      | Not null   |

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

| HTTP Status | Cause                                                                          |
|-------------|--------------------------------------------------------------------------------|
| 400         | Missing required fields, blank code, or invalid sort field                     |
| 404         | Dish or dish locale not found or already deleted                               |
| 401         | JWT token missing or invalid (not applicable to `GET /public/featured`)        |
| 403         | Authenticated user lacks permission (not applicable to `GET /public/featured`) |
