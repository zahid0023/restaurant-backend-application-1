CREATE TABLE IF NOT EXISTS restaurant_basic_info
(
    id         bigserial PRIMARY KEY,

    code       varchar(50)                      NOT NULL UNIQUE,
    sort_order integer                          NOT NULL DEFAULT 0,

    country_id bigint references countries (id) NOT NULL,
    city_id    bigint references cities (id)    NOT NULL,

    phone      varchar(50),
    email      varchar(255),
    logo_url   varchar(500),

    created_by bigint references users (id)     NOT NULL,
    created_at timestamp with time zone         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint references users (id)     NOT NULL,
    updated_at timestamp with time zone         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                                    DEFAULT 0 NOT NULL,
    is_active  boolean                          NOT NULL DEFAULT true,
    is_deleted boolean                          NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS restaurant_basic_info_locales
(
    id                       bigserial PRIMARY KEY,

    restaurant_basic_info_id bigint                       NOT NULL REFERENCES restaurant_basic_info (id) ON DELETE CASCADE,
    locale_id                bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,
    sort_order               int                          NOT NULL DEFAULT 0,

    name                     varchar(255)                 NOT NULL,
    estd                     smallint                     NOT NULL,
    short_description        varchar(1024),
    lat                      float,
    lon                      float,
    address                  text,

    created_by               bigint references users (id) NOT NULL,
    created_at               timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by               bigint references users (id) NOT NULL,
    updated_at               timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version                  bigint                                DEFAULT 0 NOT NULL,
    is_active                boolean                      NOT NULL DEFAULT true,
    is_deleted               boolean                      NOT NULL DEFAULT false,
    deleted_by               bigint,
    deleted_at               timestamp with time zone,

    UNIQUE (restaurant_basic_info_id, locale_id)
);
