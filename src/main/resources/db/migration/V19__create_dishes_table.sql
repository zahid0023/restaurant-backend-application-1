CREATE TABLE IF NOT EXISTS dishes
(
    id         bigserial PRIMARY KEY,

    code       varchar(50)              NOT NULL UNIQUE,
    sort_order int                      NOT NULL DEFAULT 0,

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

CREATE TABLE IF NOT EXISTS dishes_locales
(
    id          bigserial PRIMARY KEY,

    dish_id     bigint                   NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,
    locale_id   bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)             NOT NULL,
    description text,
    sort_order  int                      NOT NULL DEFAULT 0,

    created_by  bigint                   NOT NULL,
    created_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint                   NOT NULL,
    updated_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     bigint                            DEFAULT 0 NOT NULL,
    is_active   boolean                  NOT NULL DEFAULT true,
    is_deleted  boolean                  NOT NULL DEFAULT false,
    deleted_by  bigint,
    deleted_at  timestamp with time zone,

    UNIQUE (dish_id, locale_id)
);

