# Restaurant Basic Info API

Base path: `/api/v1/restaurant-basic-info`

The restaurant basic info is a **single, seeded record** (id = 1). It cannot be created or deleted via the API — only
read and updated.

---

## Authentication

Most endpoints require a valid JWT token in the `Authorization` header.

```
Authorization: Bearer <token>
```

The public endpoint (`GET /public`) does **not** require authentication.

---

## Endpoints

### 1. Get Restaurant Info (Public)

Retrieves the restaurant's basic information without authentication. Identical response to the authenticated GET.

```
GET /api/v1/restaurant-basic-info/public
```

**Auth required:** no

**Request body:** none

**Response `200 OK`:** same as [Get Restaurant Info (Authenticated)](#2-get-restaurant-info-authenticated) below.

---

### 2. Get Restaurant Info (Authenticated)

Retrieves the restaurant's basic information including full country, city, and all locale translations.

```
GET /api/v1/restaurant-basic-info
```

**Auth required:** yes

**Request body:** none

**Response `200 OK`:**

```json
{
  "restaurant_basic_info": {
    "id": 1,
    "estd": 2025,
    "lat": 41.0082,
    "lon": 28.9784,
    "country": {
      "id": 1,
      "code": "TR",
      "iso3_code": "TUR",
      "phone_code": "+90",
      "sort_order": 0,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Turkey",
          "description": null,
          "sort_order": 0
        }
      ]
    },
    "city": {
      "id": 1,
      "country_id": 1,
      "code": "IST",
      "sort_order": 0,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Istanbul",
          "description": null,
          "sort_order": 0
        }
      ]
    },
    "phone": "+90 555 000 0000",
    "email": "info@restaurant.com",
    "logo_url": "https://cdn.example.com/logo.png",
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "sort_order": 0,
        "name": "The Grand Restaurant",
        "short_description": "Fine dining in the heart of the city.",
        "address": "123 Main Street, Istanbul"
      }
    ]
  }
}
```

**Response fields:**

| Field                         | Type    | Description                              |
|-------------------------------|---------|------------------------------------------|
| `id`                          | long    | Always `1`                               |
| `estd`                        | short   | Year the restaurant was established      |
| `lat`                         | double  | Latitude                                 |
| `lon`                         | double  | Longitude                                |
| `country`                     | object  | Full country object                      |
| `country.id`                  | long    | Country ID                               |
| `country.code`                | string  | 2-letter country code                    |
| `country.iso3_code`           | string  | 3-letter ISO country code                |
| `country.phone_code`          | string  | International dialling code              |
| `country.sort_order`          | integer | Country display ordering weight          |
| `country.locales`             | array   | Country name/description per locale      |
| `city`                        | object  | Full city object                         |
| `city.id`                     | long    | City ID                                  |
| `city.country_id`             | long    | Parent country ID                        |
| `city.code`                   | string  | City code                                |
| `city.sort_order`             | integer | City display ordering weight             |
| `city.locales`                | array   | City name/description per locale         |
| `phone`                       | string  | Contact phone number                     |
| `email`                       | string  | Contact email address                    |
| `logo_url`                    | string  | URL to the restaurant's logo (read-only) |
| `locales`                     | array   | Restaurant translations per locale       |
| `locales[].id`                | long    | Locale entry ID                          |
| `locales[].locale_id`         | long    | Locale ID                                |
| `locales[].sort_order`        | integer | Display ordering weight                  |
| `locales[].name`              | string  | Restaurant name in this locale           |
| `locales[].short_description` | string  | Short description in this locale         |
| `locales[].address`           | string  | Full address in this locale              |

---

### 3. Update Restaurant Info

Updates the restaurant's mutable fields.

```
PUT /api/v1/restaurant-basic-info
```

**Request body:**

| Field        | Type   | Required | Constraints   | Description                             |
|--------------|--------|----------|---------------|-----------------------------------------|
| `estd`       | short  | yes      | not null      | Year the restaurant was established     |
| `country_id` | long   | yes      | not null      | ID of the country                       |
| `city_id`    | long   | yes      | not null      | ID of the city (must belong to country) |
| `lat`        | double | no       |               | Latitude                                |
| `lon`        | double | no       |               | Longitude                               |
| `phone`      | string | no       | max 50 chars  | Contact phone number                    |
| `email`      | string | no       | max 255 chars | Contact email address                   |

```json
{
  "estd": 2025,
  "country_id": 1,
  "city_id": 1,
  "lat": 41.0082,
  "lon": 28.9784,
  "phone": "+90 555 000 0000",
  "email": "info@restaurant.com"
}
```

**Response `200 OK`:**

```json
{
  "success": true,
  "id": 1
}
```

---

### 4. Upload Logo

Uploads a logo image to the specified image hosting provider and saves the resulting URL as `logo_url`.

```
POST /api/v1/restaurant-basic-info/logo
Content-Type: multipart/form-data
```

**Request parameters:**

| Parameter   | Type | Where       | Required | Description                                          |
|-------------|------|-------------|----------|------------------------------------------------------|
| `config_id` | long | query param | yes      | ID of the image hosting config to use for the upload |
| `file`      | file | form field  | yes      | The logo image file                                  |

**Flow:**

1. Loads the restaurant entity
2. Loads the hosting config by `config_id`
3. Uploads the file via `ImageUploadService` using the config's provider and credentials
4. Sets `logo_url` on the restaurant to the returned URL

**Response `200 OK`:**

```json
{
  "success": true,
  "id": 1
}
```

> After a successful upload, the new `logo_url` is returned in the `GET /api/v1/restaurant-basic-info` response.

---

## Locale Sub-Resource

Manage per-locale translations for the restaurant.

Base path: `/api/v1/restaurant-basic-info/1/locales`

> The restaurant id in the path is always `1`.

---

### 5. Add Locale

Adds a translation for a specific locale. Each locale can only be added once.

```
POST /api/v1/restaurant-basic-info/1/locales
```

**Request body:**

| Field               | Type    | Required | Constraints    | Description                      |
|---------------------|---------|----------|----------------|----------------------------------|
| `locale_id`         | long    | yes      | not null       | ID of the locale                 |
| `name`              | string  | yes      | max 255 chars  | Restaurant name in this locale   |
| `sort_order`        | integer | yes      | not null       | Display ordering weight          |
| `short_description` | string  | no       | max 1024 chars | Short description in this locale |
| `address`           | string  | no       |                | Full address in this locale      |

```json
{
  "locale_id": 1,
  "name": "The Grand Restaurant",
  "sort_order": 0,
  "short_description": "Fine dining in the heart of the city.",
  "address": "123 Main Street, Istanbul"
}
```

**Response `201 Created`:**

```json
{
  "success": true,
  "id": 1
}
```

---

### 6. Update Locale

Updates an existing locale entry. The locale itself (`locale_id`) cannot be changed.

```
PUT /api/v1/restaurant-basic-info/1/locales/{id}
```

**Path parameters:**

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `id`      | long | ID of the locale entry |

**Request body:**

| Field               | Type    | Required | Constraints    | Description                      |
|---------------------|---------|----------|----------------|----------------------------------|
| `name`              | string  | yes      | max 255 chars  | Restaurant name in this locale   |
| `sort_order`        | integer | yes      | not null       | Display ordering weight          |
| `short_description` | string  | no       | max 1024 chars | Short description in this locale |
| `address`           | string  | no       |                | Full address in this locale      |

```json
{
  "name": "The Grand Restaurant",
  "sort_order": 0,
  "short_description": "Fine dining in the heart of the city.",
  "address": "123 Main Street, Istanbul"
}
```

**Response `200 OK`:**

```json
{
  "success": true,
  "id": 1
}
```

---

### 7. Delete Locale

Soft-deletes a locale entry.

```
DELETE /api/v1/restaurant-basic-info/1/locales/{id}
```

**Path parameters:**

| Parameter | Type | Description            |
|-----------|------|------------------------|
| `id`      | long | ID of the locale entry |

**Request body:** none

**Response `200 OK`:**

```json
{
  "success": true,
  "id": 1
}
```

---

## Error Responses

| Status | Scenario                                                         |
|--------|------------------------------------------------------------------|
| `400`  | Validation failure (missing required field, constraint violated) |
| `401`  | Missing or invalid JWT token (not applicable to `/public`)       |
| `404`  | Country, city, locale entry, or image hosting config not found   |
| `409`  | Locale already exists for this restaurant                        |
