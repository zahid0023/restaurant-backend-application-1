CREATE TABLE IF NOT EXISTS recipes
(
    id           bigserial                PRIMARY KEY,
    menu_item_id bigint                   NOT NULL UNIQUE REFERENCES menu_items (id) ON DELETE CASCADE,
    code         varchar(50),

    created_by   bigint                   NOT NULL,
    created_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint                   NOT NULL,
    updated_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                            DEFAULT 0 NOT NULL,
    is_active    boolean                  NOT NULL DEFAULT true,
    is_deleted   boolean                  NOT NULL DEFAULT false,
    deleted_by   bigint,
    deleted_at   timestamp with time zone
);
