CREATE TABLE IF NOT EXISTS menus
(
    id         bigserial PRIMARY KEY,

    code       varchar(50)                  NOT NULL,
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

CREATE TABLE IF NOT EXISTS menu_locales
(
    id          bigserial PRIMARY KEY,

    menu_id     bigint                       NOT NULL REFERENCES menus (id) ON DELETE CASCADE,
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

    UNIQUE (menu_id, locale_id)
);

INSERT INTO menus (code, sort_order, created_by, updated_by)
VALUES ('BREAKFAST', 1, (SELECT id FROM users WHERE username = 'system'),
        (SELECT id FROM users WHERE username = 'system')),
       ('LUNCH', 2, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

INSERT INTO menu_locales (menu_id, locale_id, name, sort_order, created_by, updated_by)
VALUES ((SELECT id FROM menus WHERE code = 'BREAKFAST'),
        (SELECT id FROM locales WHERE code = 'en'),
        'Breakfast', 1,
        (SELECT id FROM users WHERE username = 'system'),
        (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM menus WHERE code = 'BREAKFAST'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'ব্রেকফাস্ট', 2,
        (SELECT id FROM users WHERE username = 'system'),
        (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM menus WHERE code = 'LUNCH'),
        (SELECT id FROM locales WHERE code = 'en'),
        'Lunch', 1,
        (SELECT id FROM users WHERE username = 'system'),
        (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM menus WHERE code = 'LUNCH'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'লাঞ্চ', 2,
        (SELECT id FROM users WHERE username = 'system'),
        (SELECT id FROM users WHERE username = 'system'));


