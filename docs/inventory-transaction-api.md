# Inventory Transaction API Documentation

Base URL: `/api/v1`

All endpoints require a valid JWT bearer token.

```
Authorization: Bearer <token>
```

---

## Transaction Types

| Type | Direction | Description |
|---|---|---|
| `PURCHASE` | IN (+) | Stock received from supplier |
| `TRANSFER_IN` | IN (+) | Stock received from another location |
| `RETURN` | IN (+) | Customer return added back to stock |
| `SALE` | OUT (−) | Stock sold to customer |
| `CONSUME` | OUT (−) | Stock used in recipe or production |
| `TRANSFER_OUT` | OUT (−) | Stock sent to another location |
| `RETURN_TO_SUPPLIER` | OUT (−) | Stock returned to supplier |
| `ADJUSTMENT` | unrestricted | Manual stock correction |

> **Sign convention:** `quantity` must be **positive** for IN types and **negative** for OUT types. `ADJUSTMENT` accepts any non-zero value.

---

## Endpoints

### Create Inventory Transaction

**`POST /api/v1/inventory-transactions`**

#### Request Body

```json
{
  "item_id": 1,
  "location_id": 2,
  "transaction_type": "PURCHASE",
  "quantity": 100.000000,
  "unit_cost": 2.500000,
  "total_cost": 250.000000,
  "notes": "Weekly produce delivery"
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |
| `location_id` | long | yes | must be an existing active inventory location |
| `transaction_type` | string | yes | one of the values in the Transaction Types table above |
| `quantity` | decimal | yes | positive for IN types, negative for OUT types |
| `unit_cost` | decimal | no | cost per unit |
| `total_cost` | decimal | no | total cost of the movement |
| `notes` | string | no | free-text notes |

#### Response `201 Created`

```json
{
  "success": true,
  "id": 1
}
```

---

### Get Inventory Transaction by ID

**`GET /api/v1/inventory-transactions/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory transaction ID |

#### Response `200 OK`

```json
{
  "inventory_transaction": {
    "id": 1,
    "item_id": 1,
    "location_id": 2,
    "transaction_type": "PURCHASE",
    "quantity": 100.000000,
    "unit_cost": 2.500000,
    "total_cost": 250.000000,
    "notes": "Weekly produce delivery"
  }
}
```

---

### List Inventory Transactions

Returns a paginated list of all active inventory transactions.

**`GET /api/v1/inventory-transactions`**

#### Query Parameters

| Parameter | Type | Default | Constraints | Description |
|---|---|---|---|---|
| `page` | integer | `0` | min 0 | Page index (zero-based) |
| `size` | integer | `10` | 1–50 | Items per page |
| `sort_by` | string | `id` | `id`, `transactionType`, `createdAt` | Field to sort by |
| `sort_dir` | string | `ASC` | `ASC`, `DESC` | Sort direction |

#### Response `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "item_id": 1,
      "location_id": 2,
      "transaction_type": "PURCHASE",
      "quantity": 100.000000
    },
    {
      "id": 2,
      "item_id": 3,
      "location_id": 2,
      "transaction_type": "SALE",
      "quantity": -5.000000
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

> Note: The list response returns a summary shape (`id`, `item_id`, `location_id`, `transaction_type`, `quantity`). Use the Get by ID endpoint to retrieve `unit_cost`, `total_cost`, and `notes`.

---

### Update Inventory Transaction

**`PUT /api/v1/inventory-transactions/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory transaction ID |

#### Request Body

```json
{
  "item_id": 1,
  "location_id": 2,
  "transaction_type": "PURCHASE",
  "quantity": 120.000000,
  "unit_cost": 2.500000,
  "total_cost": 300.000000,
  "notes": "Weekly produce delivery - corrected quantity"
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `item_id` | long | yes | must be an existing active item |
| `location_id` | long | yes | must be an existing active inventory location |
| `transaction_type` | string | yes | one of the values in the Transaction Types table above |
| `quantity` | decimal | yes | positive for IN types, negative for OUT types |
| `unit_cost` | decimal | no | cost per unit |
| `total_cost` | decimal | no | total cost of the movement |
| `notes` | string | no | free-text notes |

#### Response `200 OK`

```json
{
  "success": true,
  "id": 1
}
```

---

### Delete Inventory Transaction

Soft-deletes an inventory transaction (sets `is_active = false`, `is_deleted = true`).

**`DELETE /api/v1/inventory-transactions/{id}`**

#### Path Parameters

| Parameter | Type | Description |
|---|---|---|
| `id` | long | Inventory transaction ID |

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
  "message": "InventoryTransaction not found with id: 99"
}
```

### 400 Bad Request

Returned when request validation fails or the quantity sign is incorrect for the transaction type.

```json
{
  "status": 400,
  "message": "SALE requires a negative quantity (stock OUT)"
}
```

### 401 Unauthorized

Returned when the JWT token is missing or invalid.

### 403 Forbidden

Returned when the authenticated user lacks permission.
