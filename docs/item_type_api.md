# Item Type API

Base URL: `/api/v1/item-types`

Item types classify inventory items (e.g. ingredient, packaging, asset). Type names and descriptions are locale-specific and embedded in every response via the `locales` array. All records support soft-delete — deleted records are hidden from all responses.

---

## Endpoints

| Method | Path                                                | Description                  |
|--------|-----------------------------------------------------|------------------------------|
| POST   | `/api/v1/item-types`                                | Create an item type          |
| GET    | `/api/v1/item-types`                                | List all item types          |
| GET    | `/api/v1/item-types/{id}`                           | Get an item type             |
| PUT    | `/api/v1/item-types/{id}`                           | Update an item type          |
| DELETE | `/api/v1/item-types/{id}`                           | Delete an item type          |
| POST   | `/api/v1/item-types/{item-type-id}/locales`         | Create an item type locale   |
| PUT    | `/api/v1/item-types/{item-type-id}/locales/{id}`    | Update an item type locale   |
| DELETE | `/api/v1/item-types/{item-type-id}/locales/{id}`    | Delete an item type locale   |

---

## Data Model

### Item Type

| Field          | Type    | Required | Constraints              | Description                              |
|----------------|---------|----------|--------------------------|------------------------------------------|
| `id`           | Long    | —        | read-only                | Auto-generated identifier                |
| `code`         | String  | Yes      | max 50 chars, not blank  | Unique type code (e.g. `INGREDIENT`)     |
| `is_consumable`| Boolean | Yes      | not null                 | Whether the type is consumed in use      |
| `sort_order`   | Integer | Yes      | not null                 | Display order                            |
| `locales`      | Array   | —        | read-only                | All locale translations for this type    |

### Item Type Locale

| Field         | Type    | Required | Constraints              | Description                              |
|---------------|---------|----------|--------------------------|------------------------------------------|
| `id`          | Long    | —        | read-only                | Auto-generated identifier                |
| `locale_id`   | Long    | Yes      | must exist               | ID of an existing active locale          |
| `name`        | String  | Yes      | max 255 chars, not blank | Localized name of the item type          |
| `description` | String  | No       | defaults to `""`         | Localized description                    |
| `sort_order`  | Integer | Yes      | not null                 | Display order for this locale entry      |

---

## Create Item Type

`POST /api/v1/item-types`

Creates an item type along with its locale-specific translations in one request. All provided `locale_id` values must reference existing, active locales.

### Request Body

```json
{
  "code": "INGREDIENT",
  "is_consumable": true,
  "sort_order": 1,
  "locales": [
    {
      "locale_id": 1,
      "name": "Ingredient",
      "description": "Items used in recipes or production",
      "sort_order": 1
    },
    {
      "locale_id": 2,
      "name": "উপাদান",
      "description": "রেসিপি বা উৎপাদনে ব্যবহৃত উপকরণ",
      "sort_order": 1
    }
  ]
}
```

### Request Fields

| Field          | Type    | Required | Validation               |
|----------------|---------|----------|--------------------------|
| `code`         | String  | Yes      | Not blank, max 50 chars  |
| `is_consumable`| Boolean | Yes      | Not null                 |
| `sort_order`   | Integer | Yes      | Not null                 |
| `locales`      | Array   | No       | See locale fields below  |

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

## Get Item Type

`GET /api/v1/item-types/{id}`

Returns a single item type with all its locale translations.

### Path Parameters

| Parameter | Type | Description       |
|-----------|------|-------------------|
| `id`      | Long | ID of the item type |

### Response `200 OK`

```json
{
  "item_type": {
    "id": 1,
    "code": "INGREDIENT",
    "is_consumable": true,
    "sort_order": 1,
    "locales": [
      {
        "id": 1,
        "locale_id": 1,
        "name": "Ingredient",
        "description": "Items used in recipes or production",
        "sort_order": 1
      },
      {
        "id": 2,
        "locale_id": 2,
        "name": "উপাদান",
        "description": "রেসিপি বা উৎপাদনে ব্যবহৃত উপকরণ",
        "sort_order": 1
      }
    ]
  }
}
```

---

## List All Item Types

`GET /api/v1/item-types`

Returns a paginated list of active (non-deleted) item types. Each item includes all locale translations.

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
      "code": "INGREDIENT",
      "is_consumable": true,
      "sort_order": 1,
      "locales": [
        {
          "id": 1,
          "locale_id": 1,
          "name": "Ingredient",
          "description": "Items used in recipes or production",
          "sort_order": 1
        },
        {
          "id": 2,
          "locale_id": 2,
          "name": "উপাদান",
          "description": "রেসিপি বা উৎপাদনে ব্যবহৃত উপকরণ",
          "sort_order": 1
        }
      ]
    },
    {
      "id": 2,
      "code": "PACKAGING",
      "is_consumable": false,
      "sort_order": 2,
      "locales": [
        {
          "id": 3,
          "locale_id": 1,
          "name": "Packaging",
          "description": "Materials used for packaging products",
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

## Update Item Type

`PUT /api/v1/item-types/{id}`

Updates `is_consumable` and `sort_order`. The `code` field is set at creation time and cannot be changed. Locale translations are managed via the item type locale endpoints.

### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `id`      | Long | ID of the item type |

### Request Body

```json
{
  "is_consumable": true,
  "sort_order": 2
}
```

### Request Fields

| Field          | Type    | Required | Validation |
|----------------|---------|----------|------------|
| `is_consumable`| Boolean | Yes      | Not null   |
| `sort_order`   | Integer | Yes      | Not null   |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Delete Item Type

`DELETE /api/v1/item-types/{id}`

Soft-deletes the item type. The record is not removed from the database but will no longer appear in any response.

### Path Parameters

| Parameter | Type | Description         |
|-----------|------|---------------------|
| `id`      | Long | ID of the item type |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

## Item Type Locales

Item type locale endpoints manage per-locale translations for an item type. The `{item-type-id}` path parameter must reference an existing, active item type.

---

### Create Item Type Locale

`POST /api/v1/item-types/{item-type-id}/locales`

Adds a new locale translation to an existing item type.

#### Path Parameters

| Parameter      | Type | Description           |
|----------------|------|-----------------------|
| `item-type-id` | Long | ID of the item type   |

#### Request Body

```json
{
  "locale_id": 1,
  "name": "Ingredient",
  "description": "Items used in recipes or production",
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

### Update Item Type Locale

`PUT /api/v1/item-types/{item-type-id}/locales/{id}`

Updates an existing locale translation for an item type. The locale itself cannot be changed.

#### Path Parameters

| Parameter      | Type | Description                  |
|----------------|------|------------------------------|
| `item-type-id` | Long | ID of the item type          |
| `id`           | Long | ID of the item type locale   |

#### Request Body

```json
{
  "name": "Ingredient",
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

### Delete Item Type Locale

`DELETE /api/v1/item-types/{item-type-id}/locales/{id}`

Soft-deletes a locale translation. The record is not removed from the database but will no longer appear in any response.

#### Path Parameters

| Parameter      | Type | Description                  |
|----------------|------|------------------------------|
| `item-type-id` | Long | ID of the item type          |
| `id`           | Long | ID of the item type locale   |

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
  "message": "ItemType not found with id: 99"
}
```

| HTTP Status | Error Code                 | Cause                                                              |
|-------------|----------------------------|--------------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields or invalid sort field                      |
| 404         | `ENTITY_NOT_FOUND`         | Item type or locale not found, or already deleted                  |
| 409         | `DATA_INTEGRITY_VIOLATION` | Constraint violation (e.g. duplicate code or duplicate locale)     |
