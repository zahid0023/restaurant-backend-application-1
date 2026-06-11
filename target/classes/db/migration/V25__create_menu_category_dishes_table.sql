CREATE TABLE IF NOT EXISTS menu_category_dishes
(
    id               bigserial PRIMARY KEY,

    menu_category_id bigint                   NOT NULL REFERENCES menu_categories (id) ON DELETE CASCADE,
    dish_id          bigint                   NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,

    created_by       bigint                   NOT NULL,
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL,
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                            DEFAULT 0 NOT NULL,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone,
    unique (menu_category_id, dish_id)
);
