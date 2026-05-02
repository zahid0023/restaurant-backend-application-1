CREATE TABLE IF NOT EXISTS unit_type_locales
(
    id             bigserial                PRIMARY KEY,
    unit_type_id   bigint                   NOT NULL REFERENCES unit_types (id) ON DELETE CASCADE,
    locale_id      bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name           varchar(255)             NOT NULL,
    description    text                     NOT NULL DEFAULT '',
    sort_order     int                      NOT NULL DEFAULT 0,

    created_by     bigint                   NOT NULL,
    created_at     timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by     bigint                   NOT NULL,
    updated_at     timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version        bigint                            DEFAULT 0 NOT NULL,
    is_active      boolean                  NOT NULL DEFAULT true,
    is_deleted     boolean                  NOT NULL DEFAULT false,
    deleted_by     bigint,
    deleted_at     timestamp with time zone,
    UNIQUE (unit_type_id, locale_id)
);
