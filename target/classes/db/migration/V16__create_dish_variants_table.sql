CREATE TABLE IF NOT EXISTS dish_variants
(
    id           bigserial PRIMARY KEY,

    dish_id      bigint                       NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,

    code         varchar(50)                  NOT NULL,
    sort_order   int                          NOT NULL DEFAULT 0,

    price        numeric(18, 2)               NOT NULL,
    is_default   boolean                      NOT NULL DEFAULT false,

    is_available boolean                      NOT NULL DEFAULT true,
    is_featured  boolean                      NOT NULL DEFAULT false,

    created_by   bigint references users (id) NOT NULL,
    created_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint references users (id) NOT NULL,
    updated_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                                DEFAULT 0 NOT NULL,
    is_active    boolean                      NOT NULL DEFAULT true,
    is_deleted   boolean                      NOT NULL DEFAULT false,
    deleted_by   bigint,
    deleted_at   timestamp with time zone
);

CREATE TABLE IF NOT EXISTS dish_variant_locales
(
    id              bigserial PRIMARY KEY,

    dish_variant_id bigint                       NOT NULL REFERENCES dish_variants (id) ON DELETE CASCADE,
    locale_id       bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name            varchar(100)                 NOT NULL,
    description     text,
    sort_order      int                          NOT NULL DEFAULT 0,

    created_by      bigint references users (id) NOT NULL,
    created_at      timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by      bigint references users (id) NOT NULL,
    updated_at      timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version         bigint                                DEFAULT 0 NOT NULL,
    is_active       boolean                      NOT NULL DEFAULT true,
    is_deleted      boolean                      NOT NULL DEFAULT false,
    deleted_by      bigint,
    deleted_at      timestamp with time zone,

    UNIQUE (dish_variant_id, locale_id)
);

