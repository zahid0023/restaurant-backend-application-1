CREATE TABLE IF NOT EXISTS menu_category_locales
(
    id                 bigserial                PRIMARY KEY,
    menu_category_id   bigint                   NOT NULL REFERENCES menu_categories (id) ON DELETE CASCADE,
    locale_id          bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,
    name               varchar(255)             NOT NULL,
    description        text,
    sort_order         int                      NOT NULL DEFAULT 0,

    created_by         bigint                   NOT NULL,
    created_at         timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by         bigint                   NOT NULL,
    updated_at         timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version            bigint                            DEFAULT 0 NOT NULL,
    is_active          boolean                  NOT NULL DEFAULT true,
    is_deleted         boolean                  NOT NULL DEFAULT false,
    deleted_by         bigint,
    deleted_at         timestamp with time zone,

    UNIQUE (menu_category_id, locale_id)
);
