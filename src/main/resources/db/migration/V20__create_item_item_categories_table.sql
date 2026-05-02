CREATE TABLE IF NOT EXISTS item_item_categories
(
    id               bigserial                PRIMARY KEY,
    item_id          bigint                   NOT NULL REFERENCES items (id) ON DELETE CASCADE,
    item_category_id bigint                   NOT NULL REFERENCES item_categories (id) ON DELETE CASCADE,

    created_by       bigint                   NOT NULL,
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL,
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                            DEFAULT 0 NOT NULL,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone,

    UNIQUE (item_id, item_category_id)
);
