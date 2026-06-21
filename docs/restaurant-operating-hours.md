# Restaurant Operating Hours API

Base URL: `/api/v1/restaurant-operating-hours`

Most endpoints require JWT authentication. The public schedule endpoint does not.

---

## Overview

The schedule is managed as a single unit through the following endpoints:

| Method | Path               | Auth | Description                            |
|--------|--------------------|------|----------------------------------------|
| `GET`  | `/public/schedule` | No   | Get the current full schedule (public) |
| `GET`  | `/schedule`        | Yes  | Get the current full schedule          |
| `POST` | `/schedule`        | Yes  | Create the schedule for the first time |
| `PUT`  | `/schedule`        | Yes  | Replace the entire schedule            |

Both `POST` and `PUT` accept the same request body containing `operating` (time slots) and `closing` (closed days)
together.

---

## Request Body

```json
{
  "operating": [
    ...slots...
  ],
  "closing": [
    ...closed
    days...
  ]
}
```

Both fields are optional lists (omit or send `[]` if there are no slots / no closed days).

### Operating slot fields

| Field             | Type             | Required | Notes                                                                        |
|-------------------|------------------|----------|------------------------------------------------------------------------------|
| `day_of_week`     | string           | Yes      | `MONDAY` `TUESDAY` `WEDNESDAY` `THURSDAY` `FRIDAY` `SATURDAY` `SUNDAY`       |
| `sequence_no`     | integer (> 0)    | Yes      | Order of shifts within the same day. Must be unique per day.                 |
| `open_time`       | `HH:mm`          | Yes      | Start time of this slot                                                      |
| `close_time`      | `HH:mm`          | Yes      | End time of this slot                                                        |
| `label`           | string (max 255) | No       | Period name e.g. `"Breakfast"`, `"Lunch"`, `"Dinner"`, `"Break"`             |
| `closes_next_day` | boolean          | No       | `true` for overnight slots (e.g. 22:00–02:00). Default: `false`              |
| `is_closed`       | boolean          | No       | `true` marks this as a break/closure period within the day. Default: `false` |

### Closing day fields

| Field         | Type             | Required | Notes                                                                |
|---------------|------------------|----------|----------------------------------------------------------------------|
| `day_of_week` | string           | Yes      | The weekday to mark as fully closed. Must be unique within the list. |
| `note`        | string (max 255) | No       | Reason e.g. `"Weekly off"`, `"Public holiday"`                       |

---

## Validation Rules (applied to both POST and PUT)

- A day cannot appear in both `operating` and `closing`
- No duplicate `day_of_week` within the `closing` list
- No duplicate `(day_of_week, sequence_no)` within the `operating` list
- `open_time` and `close_time` cannot be equal
- If `closes_next_day = false`, `close_time` must be after `open_time`
- `is_closed = true` and `closes_next_day = true` cannot be combined on the same slot

---

## Endpoints

### GET /public/schedule

Returns the full current schedule. No authentication required.

**Response `200 OK`** — same shape as [GET /schedule](#get-schedule) below.

---

### GET /schedule

Returns the full current schedule. Requires JWT authentication.

**Response `200 OK`**

```json
{
  "closing": [
    {
      "id": 1,
      "day_of_week": "MONDAY",
      "note": "Weekly off"
    }
  ],
  "operating": {
    "TUESDAY": [
      {
        "id": 2,
        "day_of_week": "TUESDAY",
        "sequence_no": 1,
        "open_time": "09:00",
        "close_time": "14:00",
        "label": "Lunch",
        "closes_next_day": false,
        "is_closed": false
      },
      {
        "id": 3,
        "day_of_week": "TUESDAY",
        "sequence_no": 2,
        "open_time": "14:00",
        "close_time": "17:00",
        "label": "Break",
        "closes_next_day": false,
        "is_closed": true
      },
      {
        "id": 4,
        "day_of_week": "TUESDAY",
        "sequence_no": 3,
        "open_time": "17:00",
        "close_time": "22:00",
        "label": "Dinner",
        "closes_next_day": false,
        "is_closed": false
      }
    ],
    "FRIDAY": [
      {
        "id": 5,
        "day_of_week": "FRIDAY",
        "sequence_no": 1,
        "open_time": "20:00",
        "close_time": "02:00",
        "label": "Dinner",
        "closes_next_day": true,
        "is_closed": false
      }
    ]
  }
}
```

**Notes:**

- `operating` is a map keyed by `day_of_week`, ordered `MONDAY` → `SUNDAY`
- Slots within each day are ordered by `sequence_no` ascending
- Days with no slots are absent from `operating`
- Clients determine open/closed status locally from this response — no extra API call needed

---

### POST /schedule

Creates the schedule for the first time. Fails if any schedule data already exists — use `PUT /schedule` to replace.

**Request body**

```json
{
  "closing": [
    {
      "day_of_week": "MONDAY",
      "note": "Weekly off"
    }
  ],
  "operating": [
    {
      "day_of_week": "TUESDAY",
      "sequence_no": 1,
      "open_time": "09:00",
      "close_time": "14:00",
      "label": "Lunch"
    },
    {
      "day_of_week": "TUESDAY",
      "sequence_no": 2,
      "open_time": "14:00",
      "close_time": "17:00",
      "label": "Break",
      "is_closed": true
    },
    {
      "day_of_week": "TUESDAY",
      "sequence_no": 3,
      "open_time": "17:00",
      "close_time": "22:00",
      "label": "Dinner"
    },
    {
      "day_of_week": "WEDNESDAY",
      "sequence_no": 1,
      "open_time": "09:00",
      "close_time": "22:00",
      "label": "All Day"
    },
    {
      "day_of_week": "THURSDAY",
      "sequence_no": 1,
      "open_time": "09:00",
      "close_time": "22:00",
      "label": "All Day"
    },
    {
      "day_of_week": "FRIDAY",
      "sequence_no": 1,
      "open_time": "20:00",
      "close_time": "02:00",
      "label": "Dinner",
      "closes_next_day": true
    },
    {
      "day_of_week": "SATURDAY",
      "sequence_no": 1,
      "open_time": "10:00",
      "close_time": "23:00",
      "label": "All Day"
    },
    {
      "day_of_week": "SUNDAY",
      "sequence_no": 1,
      "open_time": "10:00",
      "close_time": "20:00",
      "label": "All Day"
    }
  ]
}
```

**Response `201 Created`**

```json
{
  "success": true,
  "id": null
}
```

**Error responses**

| Status      | Reason                                        |
|-------------|-----------------------------------------------|
| `400`       | Any validation rule violated                  |
| `409 / 400` | Schedule already exists — use `PUT /schedule` |

---

### PUT /schedule

Replaces the entire schedule. Soft-deletes all existing operating slots and closed days, then saves the new ones. Safe
to call at any time.

**Request body** — same shape as `POST /schedule`

```json
{
  "closing": [
    {
      "day_of_week": "MONDAY",
      "note": "Weekly off"
    },
    {
      "day_of_week": "TUESDAY",
      "note": "Weekly off"
    }
  ],
  "operating": [
    {
      "day_of_week": "WEDNESDAY",
      "sequence_no": 1,
      "open_time": "09:00",
      "close_time": "22:00"
    },
    {
      "day_of_week": "FRIDAY",
      "sequence_no": 1,
      "open_time": "20:00",
      "close_time": "03:00",
      "label": "Dinner",
      "closes_next_day": true
    }
  ]
}
```

**Response `200 OK`**

```json
{
  "success": true,
  "id": null
}
```

**Error responses**

| Status | Reason                       |
|--------|------------------------------|
| `400`  | Any validation rule violated |

---

## Common Scenarios

### Overnight slot (e.g. Friday late night, closes at 02:00 Saturday)

```json
{
  "day_of_week": "FRIDAY",
  "sequence_no": 2,
  "open_time": "22:00",
  "close_time": "02:00",
  "label": "Late Night",
  "closes_next_day": true
}
```

### Break period (closed mid-day)

```json
{
  "day_of_week": "WEDNESDAY",
  "sequence_no": 2,
  "open_time": "14:00",
  "close_time": "17:00",
  "label": "Break",
  "is_closed": true
}
```

### Fully closed day

```json
{
  "closing": [
    {
      "day_of_week": "SUNDAY",
      "note": "Weekly off"
    }
  ],
  "operating": []
}
```

### Determine open/closed status (client-side)

```
GET /public/schedule  →  check closing[] for today's weekday
               →  if found → CLOSED all day
               →  else find today's slots in operating where is_closed = false
               →  check if now falls within any open slot:
                    normal slot:    open_time <= now <= close_time
                    overnight slot: now >= open_time  OR  now <= close_time
               →  also check yesterday's slots where closes_next_day = true and now <= close_time
```

---

## Database

| Table                        | Migration | Key Constraint                                                                |
|------------------------------|-----------|-------------------------------------------------------------------------------|
| `restaurant_operating_hours` | V30, V31  | Partial unique index on `(day_of_week, sequence_no) WHERE is_deleted = false` |
| `restaurant_closed_days`     | V32       | Unique constraint on `day_of_week`                                            |
