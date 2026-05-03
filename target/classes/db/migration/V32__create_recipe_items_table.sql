CREATE TABLE IF NOT EXISTS recipe_items
(
    id         bigserial                PRIMARY KEY,
    recipe_id  bigint                   NOT NULL REFERENCES recipes (id) ON DELETE CASCADE,
    item_id    bigint                   NOT NULL REFERENCES items (id),
    quantity   numeric(18, 6),

    created_by bigint                   NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint                   NOT NULL,
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                            DEFAULT 0 NOT NULL,
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean                  NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);
