CREATE TABLE IF NOT EXISTS restaurant_operating_hours
(
    id          bigserial PRIMARY KEY,                 -- unique row identifier

    day_of_week VARCHAR(9)                   NOT NULL, -- weekday of this time slot (MONDAY..SUNDAY)

    sequence_no smallint                     NOT NULL
        CHECK (sequence_no > 0),                       -- order of shifts within same day (1,2,3...)

    open_time   time                         NOT NULL, -- opening time for this slot

    close_time  time                         NOT NULL, -- closing time for this slot (can cross midnight)

    created_by  bigint references users (id) NOT NULL,
    created_at  timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint references users (id) NOT NULL,
    updated_at  timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     bigint                                DEFAULT 0 NOT NULL,
    is_active   boolean                      NOT NULL DEFAULT true,
    is_deleted  boolean                      NOT NULL DEFAULT false,
    deleted_by  bigint,
    deleted_at  timestamp with time zone
);

-- Partial unique index: only enforce uniqueness on active (non-deleted) records,
-- so a soft-deleted slot can be re-created for the same day + sequence.
CREATE UNIQUE INDEX IF NOT EXISTS uq_operating_hours_day_sequence_active
    ON restaurant_operating_hours (day_of_week, sequence_no)
    WHERE is_deleted = false;