# Recipe Item API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

Recipe items are the ingredient lines of a recipe. Each recipe item links an inventory item with an optional quantity. All path parameters must refer to existing active resources.

---

### Create Recipe Item

**`POST /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `recipe-id` | long | Recipe ID |

#### Request Body

```json
{
  "item_id": 5,
  "quantity": 0.250000
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active inventory item |
| `quantity` | decimal | no | greater than 0, max 12 integer digits, 6 fraction digits |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Recipe Item by ID

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `recipe-id` | long | Recipe ID |
| `id` | long | Recipe item ID |

#### Response `200 OK`

```json
{
  "recipe_item": {
    "id": 1,
    "recipe_id": 1,
    "item_id": 5,
    "quantity": 0.250000
  }
}
```

---

### List Recipe Items

Returns a paginated list of all active ingredient lines for a given recipe.

**`GET /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `recipe-id` | long | Recipe ID |

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "recipe_id": 1,
      "item_id": 5,
      "quantity": 0.250000
    },
    {
      "id": 2,
      "recipe_id": 1,
      "item_id": 8,
      "quantity": 0.050000
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

### Update Recipe Item

**`PUT /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `recipe-id` | long | Recipe ID |
| `id` | long | Recipe item ID |

#### Request Body

```json
{
  "item_id": 5,
  "quantity": 0.300000
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active inventory item |
| `quantity` | decimal | no | greater than 0, max 12 integer digits, 6 fraction digits |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Recipe Item

Soft-deletes a recipe item (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `menu-id` | long | Menu ID |
| `section-id` | long | Menu section ID |
| `item-id` | long | Menu item ID |
| `recipe-id` | long | Recipe ID |
| `id` | long | Recipe item ID |

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
  "message": "RecipeItem not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "item_id": "must not be null"
  }
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
