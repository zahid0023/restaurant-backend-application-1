CREATE TABLE IF NOT EXISTS item_categories
(
    id             bigserial                PRIMARY KEY,
    item_type_id   bigint                   NOT NULL REFERENCES item_types (id) ON DELETE RESTRICT,
    code           varchar(50)              NOT NULL,
    sort_order     int                      NOT NULL DEFAULT 0,

    created_by     bigint                   NOT NULL,
    created_at     timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by     bigint                   NOT NULL,
    updated_at     timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version        bigint                            DEFAULT 0 NOT NULL,
    is_active      boolean                  NOT NULL DEFAULT true,
    is_deleted     boolean                  NOT NULL DEFAULT false,
    deleted_by     bigint,
    deleted_at     timestamp with time zone
);
