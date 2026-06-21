CREATE TABLE IF NOT EXISTS unit_types
(
    id         bigserial PRIMARY KEY,
    code       varchar(50)                  NOT NULL UNIQUE,
    sort_order int                          NOT NULL DEFAULT 0,

    created_by bigint references users (id) NOT NULL,
    created_at timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint references users (id) NOT NULL,
    updated_at timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                                DEFAULT 0 NOT NULL,
    is_active  boolean                      NOT NULL DEFAULT true,
    is_deleted boolean                      NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS unit_type_locales
(
    id           bigserial PRIMARY KEY,
    unit_type_id bigint                       NOT NULL REFERENCES unit_types (id) ON DELETE CASCADE,
    locale_id    bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name         varchar(255)                 NOT NULL,
    description  text                         NOT NULL DEFAULT '',
    sort_order   int                          NOT NULL DEFAULT 0,

    created_by   bigint references users (id) NOT NULL,
    created_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint references users (id) NOT NULL,
    updated_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                                DEFAULT 0 NOT NULL,
    is_active    boolean                      NOT NULL DEFAULT true,
    is_deleted   boolean                      NOT NULL DEFAULT false,
    deleted_by   bigint,
    deleted_at   timestamp with time zone,
    UNIQUE (unit_type_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO unit_types (code, sort_order, created_by, updated_by)
        VALUES ('WEIGHT', 1, sys_id, sys_id),
               ('VOLUME', 2, sys_id, sys_id),
               ('COUNT',  3, sys_id, sys_id),
               ('UNIT',   4, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        INSERT INTO unit_type_locales (unit_type_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT ut.id,
               l.id,
               v.name,
               v.description,
               ut.sort_order,
               sys_id,
               sys_id
        FROM unit_types ut
                 JOIN (VALUES
                           -- =========================
                           -- WEIGHT
                           -- =========================
                           ('WEIGHT', 'en', 'Weight', 'Mass measurement units'),

                           -- =========================
                           -- VOLUME
                           -- =========================
                           ('VOLUME', 'en', 'Volume', 'Liquid capacity measurement units'),

                           -- =========================
                           -- COUNT
                           -- =========================
                           ('COUNT', 'en', 'Count', 'Discrete item counting units like pcs, box, pack'),

                           -- =========================
                           -- UNIT
                           -- =========================
                           ('UNIT', 'en', 'Unit', 'Generic measurement unit category')
                      ) v(code, locale_code, name, description) ON ut.code = v.code
                 JOIN locales l ON l.code = v.locale_code;
    END
$$;
