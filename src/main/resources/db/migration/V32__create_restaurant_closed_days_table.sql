CREATE TABLE IF NOT EXISTS restaurant_closed_days
(
    id          bigserial PRIMARY KEY,

    day_of_week VARCHAR(9)                   NOT NULL UNIQUE, -- one record per weekday (MONDAY..SUNDAY)

    note        varchar(255)                 NULL,            -- optional reason, e.g. 'Weekly off'

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
