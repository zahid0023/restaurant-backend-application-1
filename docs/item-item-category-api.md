# Item-Item Category API

Base URL: `/api/v1/item-item-categories`

Manages the many-to-many assignment relationship between items and item categories. An item can be assigned to multiple categories and a category can contain multiple items. Assignments are soft-deleted on unassign.

---

## Endpoints

| Method | Path                                                        | Description                      |
|--------|-------------------------------------------------------------|----------------------------------|
| POST   | `/api/v1/item-item-categories`                              | Assign an item to a category     |
| DELETE | `/api/v1/item-item-categories/{item-id}/{item-category-id}` | Unassign an item from a category |

---

## Assign Item to Category

`POST /api/v1/item-item-categories`

Creates an assignment between an item and an item category. Returns an error if the item is already assigned to the given category.

### Request Body

```json
{
  "item_id": 3,
  "item_category_id": 2
}
```

### Request Fields

| Field              | Type | Required | Validation                                      |
|--------------------|------|----------|-------------------------------------------------|
| `item_id`          | Long | Yes      | Not null, must reference an existing active item |
| `item_category_id` | Long | Yes      | Not null, must reference an existing active item category |

### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

> `id` is the ID of the newly created assignment record.

---

## Unassign Item from Category

`DELETE /api/v1/item-item-categories/{item-id}/{item-category-id}`

Soft-deletes the assignment between an item and a category.

### Path Parameters

| Parameter          | Type | Description                |
|--------------------|------|----------------------------|
| `item-id`          | Long | ID of the item to unassign |
| `item-category-id` | Long | ID of the item category    |

### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

> `id` is the ID of the assignment record that was soft-deleted.

---

## Error Responses

All errors follow a common structure:

```json
{
  "request_id": "abc-123",
  "status": 404,
  "error": "ENTITY_NOT_FOUND",
  "message": "PlatformItem 3 is not assigned to PlatformItemCategory 2"
}
```

| HTTP Status | Error Code                 | Cause                                                        |
|-------------|----------------------------|--------------------------------------------------------------|
| 400         | `INVALID_ARGUMENT`         | Missing required fields                                      |
| 404         | `ENTITY_NOT_FOUND`         | Item, item category, or assignment not found or already deleted |
| 409         | `DATA_INTEGRITY_VIOLATION` | Item is already assigned to the given category               |
