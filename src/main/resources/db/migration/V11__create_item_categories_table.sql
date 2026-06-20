CREATE TABLE IF NOT EXISTS item_categories
(
    id         bigserial PRIMARY KEY,

    parent_id  bigint REFERENCES item_categories (id),

    code       varchar(50)              NOT NULL,
    sort_order int                      NOT NULL DEFAULT 0,

    created_by bigint                   NOT NULL REFERENCES users (id),
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint                   NOT NULL REFERENCES users (id),
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                   NOT NULL DEFAULT 0,
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean                  NOT NULL DEFAULT false,
    deleted_by bigint REFERENCES users (id),
    deleted_at timestamp with time zone,

    UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS item_category_locales
(
    id               bigserial PRIMARY KEY,

    item_category_id bigint                   NOT NULL REFERENCES item_categories (id) ON DELETE CASCADE,
    locale_id        bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name             varchar(255)             NOT NULL,
    description      text                     NOT NULL DEFAULT '',
    sort_order       int                      NOT NULL DEFAULT 0,

    created_by       bigint                   NOT NULL REFERENCES users (id),
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL REFERENCES users (id),
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                   NOT NULL DEFAULT 0,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint REFERENCES users (id),
    deleted_at       timestamp with time zone,

    UNIQUE (item_category_id, locale_id)
);