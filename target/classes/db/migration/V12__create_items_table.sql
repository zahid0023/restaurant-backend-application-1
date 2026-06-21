CREATE TABLE IF NOT EXISTS items
(
    id           bigserial PRIMARY KEY,

    code         varchar(20)                  NOT NULL,
    item_type_id bigint                       NOT NULL REFERENCES item_types (id),
    unit_type_id bigint                       NOT NULL REFERENCES unit_types (id) ON DELETE RESTRICT,
    sort_order   int                          NOT NULL DEFAULT 0,

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

CREATE TABLE IF NOT EXISTS item_locales
(
    id          bigserial PRIMARY KEY,

    item_id     bigint                       NOT NULL REFERENCES items (id) ON DELETE CASCADE,
    locale_id   bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)                 NOT NULL,
    description text,
    sort_order  int                          NOT NULL DEFAULT 0,

    created_by  bigint references users (id) NOT NULL,
    created_at  timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint references users (id) NOT NULL,
    updated_at  timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     bigint                                DEFAULT 0 NOT NULL,
    is_active   boolean                      NOT NULL DEFAULT true,
    is_deleted  boolean                      NOT NULL DEFAULT false,
    deleted_by  bigint,
    deleted_at  timestamp with time zone,

    UNIQUE (item_id, locale_id)
);
