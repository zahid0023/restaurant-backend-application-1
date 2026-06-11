CREATE TABLE IF NOT EXISTS menu_types
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

CREATE TABLE IF NOT EXISTS menu_type_locales
(
    id          bigserial PRIMARY KEY,

    menu_id     bigint                   NOT NULL REFERENCES menu_types (id) ON DELETE CASCADE,
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

    UNIQUE (menu_id, locale_id)
);

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1
                       FROM pg_constraint
                       WHERE conrelid = 'menu_types'::regclass
                         AND contype = 'u'
                         AND conname LIKE '%code%') THEN
            ALTER TABLE menu_types
                ADD CONSTRAINT menu_types_code_unique UNIQUE (code);
        END IF;
    END
$$;

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO menu_types (code, sort_order, created_by, updated_by)
        VALUES ('BREAKFAST', 1, sys_id, sys_id),
               ('LUNCH', 2, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        INSERT INTO menu_type_locales (menu_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT mt.id,
               l.id,
               v.name,
               v.description,
               mt.sort_order,
               sys_id,
               sys_id
        FROM menu_types mt
                 JOIN (VALUES
                           -- =========================
                           -- BREAKFAST
                           -- =========================
                           ('BREAKFAST', 'en', 'Breakfast', 'Morning menu served in the early hours'),
                           ('BREAKFAST', 'bn', 'সকালের নাস্তা', 'সকালের প্রথম প্রহরে পরিবেশিত মেনু'),

                           -- =========================
                           -- LUNCH
                           -- =========================
                           ('LUNCH', 'en', 'Lunch', 'Midday menu served during afternoon hours'),
                           ('LUNCH', 'bn', 'দুপুরের খাবার',
                            'দুপুরের সময় পরিবেশিত মেনু')) v(code, locale_code, name, description) ON mt.code = v.code
                 JOIN locales l ON l.code = v.locale_code;
    END
$$;
