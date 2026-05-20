CREATE TABLE IF NOT EXISTS units
(
    id           bigserial PRIMARY KEY,
    unit_type_id bigint                       NOT NULL REFERENCES unit_types (id) ON DELETE RESTRICT,
    code         varchar(20)                  NOT NULL UNIQUE,
    is_base      boolean                      NOT NULL DEFAULT false,
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

CREATE TABLE IF NOT EXISTS unit_locales
(
    id          bigserial PRIMARY KEY,
    unit_id     bigint                       NOT NULL REFERENCES units (id) ON DELETE CASCADE,
    locale_id   bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)                 NOT NULL,
    description text                         NOT NULL DEFAULT '',
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
    UNIQUE (unit_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO units (unit_type_id, code, is_base, sort_order, created_by, updated_by)
        SELECT ut.id, v.code, v.is_base, v.sort_order, sys_id, sys_id
        FROM unit_types ut
                 JOIN (VALUES
                           -- =========================
                           -- WEIGHT
                           -- =========================
                           ('WEIGHT', 'KG',   true,  1),
                           ('WEIGHT', 'G',    false, 2),
                           ('WEIGHT', 'MG',   false, 3),
                           ('WEIGHT', 'TON',  false, 4),

                           -- =========================
                           -- VOLUME
                           -- =========================
                           ('VOLUME', 'L',    true,  5),
                           ('VOLUME', 'ML',   false, 6),

                           -- =========================
                           -- COUNT
                           -- =========================
                           ('COUNT', 'PCS',   true,  7),
                           ('COUNT', 'BOX',   false, 8),
                           ('COUNT', 'PACK',  false, 9),
                           ('COUNT', 'DOZEN', false, 10),

                           -- =========================
                           -- UNIT (generic fallback)
                           -- =========================
                           ('UNIT', 'UNIT',   true,  11)
                      ) v(type_code, code, is_base, sort_order) ON ut.code = v.type_code;

        INSERT INTO unit_locales (unit_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT u.id,
               l.id,
               v.name,
               v.description,
               u.sort_order,
               sys_id,
               sys_id
        FROM units u
                 JOIN (VALUES
                           -- =========================
                           -- WEIGHT
                           -- =========================
                           ('KG',    'en', 'Kilogram',    'Metric unit of mass'),
                           ('KG',    'bn', 'কিলোগ্রাম',  'ভরের মেট্রিক একক'),
                           ('G',     'en', 'Gram',        'Metric unit of mass'),
                           ('G',     'bn', 'গ্রাম',       'ভরের একক'),
                           ('MG',    'en', 'Milligram',   'One thousandth of a gram'),
                           ('MG',    'bn', 'মিলিগ্রাম',  'গ্রামের এক হাজার ভাগের এক ভাগ'),
                           ('TON',   'en', 'Ton',         'Large unit of mass'),
                           ('TON',   'bn', 'টন',          'বড় ভরের একক'),

                           -- =========================
                           -- VOLUME
                           -- =========================
                           ('L',     'en', 'Liter',       'Unit of volume'),
                           ('L',     'bn', 'লিটার',       'আয়তনের একক'),
                           ('ML',    'en', 'Milliliter',  'One thousandth of a liter'),
                           ('ML',    'bn', 'মিলিলিটার',  'লিটারের এক হাজার ভাগের এক ভাগ'),

                           -- =========================
                           -- COUNT
                           -- =========================
                           ('PCS',   'en', 'Piece',       'Individual item unit'),
                           ('PCS',   'bn', 'পিস',         'একক আইটেম'),
                           ('BOX',   'en', 'Box',         'Boxed quantity'),
                           ('BOX',   'bn', 'বক্স',        'বক্সজাত পরিমাণ'),
                           ('PACK',  'en', 'Pack',        'Packaged group unit'),
                           ('PACK',  'bn', 'প্যাক',       'প্যাকেজড ইউনিট'),
                           ('DOZEN', 'en', 'Dozen',       'Twelve items'),
                           ('DOZEN', 'bn', 'ডজন',         '১২টি আইটেম'),

                           -- =========================
                           -- UNIT
                           -- =========================
                           ('UNIT',  'en', 'Unit',        'Generic unit'),
                           ('UNIT',  'bn', 'ইউনিট',      'সাধারণ ইউনিট')
                      ) v(code, locale_code, name, description) ON u.code = v.code
                 JOIN locales l ON l.code = v.locale_code;
    END
$$;
