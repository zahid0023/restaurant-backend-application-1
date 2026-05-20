CREATE TABLE IF NOT EXISTS menu_categories
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

CREATE TABLE IF NOT EXISTS menu_category_locales
(
    id               bigserial PRIMARY KEY,

    menu_category_id bigint                       NOT NULL REFERENCES menu_categories (id) ON DELETE CASCADE,
    locale_id        bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name             varchar(255)                 NOT NULL,
    description      text,
    sort_order       int                          NOT NULL DEFAULT 0,

    created_by       bigint references users (id) NOT NULL,
    created_at       timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint references users (id) NOT NULL,
    updated_at       timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                                DEFAULT 0 NOT NULL,
    is_active        boolean                      NOT NULL DEFAULT true,
    is_deleted       boolean                      NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone,

    UNIQUE (menu_category_id, locale_id)
);

INSERT INTO menu_categories (code, sort_order, created_by, updated_by)
VALUES ('PLATTER', 1, 1, 1),
       ('COMBO', 2, 1, 1),
       ('BREAKFAST_SPECIAL', 3, 1, 1),
       ('LUNCH_SPECIAL', 4, 1, 1),
       ('DRINKS', 5, 1, 1),
       ('DESSERTS', 6, 1, 1),
       ('VEG_ITEMS', 7, 1, 1),
       ('NON_VEG_ITEMS', 8, 1, 1);

INSERT INTO menu_category_locales (menu_category_id, locale_id, name, description, sort_order, created_by, updated_by)
VALUES
    -- PLATTER
    ((SELECT id FROM menu_categories WHERE code = 'PLATTER'), (SELECT id FROM locales WHERE code = 'en'), 'Platter', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'PLATTER'), (SELECT id FROM locales WHERE code = 'bn'), 'প্লেটার', '', 1, 1, 1),
    -- COMBO
    ((SELECT id FROM menu_categories WHERE code = 'COMBO'), (SELECT id FROM locales WHERE code = 'en'), 'Combo', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'COMBO'), (SELECT id FROM locales WHERE code = 'bn'), 'কম্বো', '', 1, 1, 1),
    -- BREAKFAST_SPECIAL
    ((SELECT id FROM menu_categories WHERE code = 'BREAKFAST_SPECIAL'), (SELECT id FROM locales WHERE code = 'en'), 'Breakfast Special', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'BREAKFAST_SPECIAL'), (SELECT id FROM locales WHERE code = 'bn'), 'সকালের বিশেষ', '', 1, 1, 1),
    -- LUNCH_SPECIAL
    ((SELECT id FROM menu_categories WHERE code = 'LUNCH_SPECIAL'), (SELECT id FROM locales WHERE code = 'en'), 'Lunch Special', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'LUNCH_SPECIAL'), (SELECT id FROM locales WHERE code = 'bn'), 'দুপুরের বিশেষ', '', 1, 1, 1),
    -- DRINKS
    ((SELECT id FROM menu_categories WHERE code = 'DRINKS'), (SELECT id FROM locales WHERE code = 'en'), 'Drinks', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'DRINKS'), (SELECT id FROM locales WHERE code = 'bn'), 'পানীয়', '', 1, 1, 1),
    -- DESSERTS
    ((SELECT id FROM menu_categories WHERE code = 'DESSERTS'), (SELECT id FROM locales WHERE code = 'en'), 'Desserts', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'DESSERTS'), (SELECT id FROM locales WHERE code = 'bn'), 'মিষ্টান্ন', '', 1, 1, 1),
    -- VEG_ITEMS
    ((SELECT id FROM menu_categories WHERE code = 'VEG_ITEMS'), (SELECT id FROM locales WHERE code = 'en'), 'Veg Items', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'VEG_ITEMS'), (SELECT id FROM locales WHERE code = 'bn'), 'নিরামিষ আইটেম', '', 1, 1, 1),
    -- NON_VEG_ITEMS
    ((SELECT id FROM menu_categories WHERE code = 'NON_VEG_ITEMS'), (SELECT id FROM locales WHERE code = 'en'), 'Non-Veg Items', '', 1, 1, 1),
    ((SELECT id FROM menu_categories WHERE code = 'NON_VEG_ITEMS'), (SELECT id FROM locales WHERE code = 'bn'), 'আমিষ আইটেম', '', 1, 1, 1);
