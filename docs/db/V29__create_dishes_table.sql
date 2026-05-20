CREATE TABLE IF NOT EXISTS dishes
(
    id               bigserial                PRIMARY KEY,
    menu_category_id bigint                   NOT NULL REFERENCES menu_categories (id) ON DELETE CASCADE,
    code             varchar(50)              NOT NULL,
    sort_order       int                      NOT NULL DEFAULT 0,
    is_available     boolean                  NOT NULL DEFAULT true,
    is_featured      boolean                  NOT NULL DEFAULT false,
    is_veg           boolean,

    created_by       bigint                   NOT NULL,
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL,
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                            DEFAULT 0 NOT NULL,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone
);
