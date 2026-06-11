CREATE TABLE IF NOT EXISTS menu_categories
(
    id           bigserial PRIMARY KEY,

    menu_type_id bigint                   NOT NULL REFERENCES menu_types (id) ON DELETE CASCADE,

    code         varchar(50)              NOT NULL UNIQUE ,
    sort_order   int                      NOT NULL DEFAULT 0,

    created_by   bigint                   NOT NULL,
    created_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint                   NOT NULL,
    updated_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                            DEFAULT 0 NOT NULL,
    is_active    boolean                  NOT NULL DEFAULT true,
    is_deleted   boolean                  NOT NULL DEFAULT false,
    deleted_by   bigint,
    deleted_at   timestamp with time zone
);

CREATE TABLE IF NOT EXISTS menu_category_locales
(
    id               bigserial PRIMARY KEY,

    menu_category_id bigint                   NOT NULL REFERENCES menu_categories (id) ON DELETE CASCADE,
    locale_id        bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name             varchar(255)             NOT NULL,
    description      text,
    sort_order       int                      NOT NULL DEFAULT 0,

    created_by       bigint                   NOT NULL,
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL,
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                            DEFAULT 0 NOT NULL,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone,

    UNIQUE (menu_category_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO menu_categories (menu_type_id, code, sort_order, created_by, updated_by)
        SELECT mt.id, v.code, v.sort_order, sys_id, sys_id
        FROM menu_types mt
                 JOIN (VALUES
                           -- BREAKFAST categories
                           ('BREAKFAST', 'HOT_DRINKS', 1),
                           ('BREAKFAST', 'COLD_DRINKS', 2),
                           ('BREAKFAST', 'MAIN_DISHES', 3),
                           ('BREAKFAST', 'PASTRIES', 4),

                           -- LUNCH categories
                           ('LUNCH', 'STARTERS', 1),
                           ('LUNCH', 'SOUPS', 2),
                           ('LUNCH', 'MAIN_COURSE', 3),
                           ('LUNCH', 'DESSERTS', 4),
                           ('LUNCH', 'BEVERAGES', 5)) v(menu_type_code, code, sort_order) ON mt.code = v.menu_type_code
        ON CONFLICT DO NOTHING;

        INSERT INTO menu_category_locales (menu_category_id, locale_id, name, description, sort_order, created_by,
                                           updated_by)
        SELECT mc.id,
               l.id,
               v.name,
               v.description,
               mc.sort_order,
               sys_id,
               sys_id
        FROM menu_categories mc
                 JOIN menu_types mt ON mt.id = mc.menu_type_id
                 JOIN (VALUES
                           -- =========================
                           -- BREAKFAST — HOT_DRINKS
                           -- =========================
                           ('BREAKFAST', 'HOT_DRINKS', 'en', 'Hot Drinks', 'Warm beverages served with breakfast'),
                           ('BREAKFAST', 'HOT_DRINKS', 'bn', 'গরম পানীয়', 'সকালের নাস্তার সাথে পরিবেশিত গরম পানীয়'),

                           -- =========================
                           -- BREAKFAST — COLD_DRINKS
                           -- =========================
                           ('BREAKFAST', 'COLD_DRINKS', 'en', 'Cold Drinks', 'Chilled beverages served with breakfast'),
                           ('BREAKFAST', 'COLD_DRINKS', 'bn', 'ঠান্ডা পানীয়',
                            'সকালের নাস্তার সাথে পরিবেশিত ঠান্ডা পানীয়'),

                           -- =========================
                           -- BREAKFAST — MAIN_DISHES
                           -- =========================
                           ('BREAKFAST', 'MAIN_DISHES', 'en', 'Main Dishes',
                            'Main breakfast dishes served in the morning'),
                           ('BREAKFAST', 'MAIN_DISHES', 'bn', 'প্রধান খাবার', 'সকালে পরিবেশিত প্রধান নাস্তার আইটেম'),

                           -- =========================
                           -- BREAKFAST — PASTRIES
                           -- =========================
                           ('BREAKFAST', 'PASTRIES', 'en', 'Pastries', 'Freshly baked pastries and breads'),
                           ('BREAKFAST', 'PASTRIES', 'bn', 'পেস্ট্রি', 'তাজা বেক করা পেস্ট্রি ও পাউরুটি'),

                           -- =========================
                           -- LUNCH — STARTERS
                           -- =========================
                           ('LUNCH', 'STARTERS', 'en', 'Starters', 'Light appetizers to begin your meal'),
                           ('LUNCH', 'STARTERS', 'bn', 'স্টার্টার', 'খাবার শুরুর জন্য হালকা অ্যাপেটাইজার'),

                           -- =========================
                           -- LUNCH — SOUPS
                           -- =========================
                           ('LUNCH', 'SOUPS', 'en', 'Soups', 'Hot and cold soups prepared fresh daily'),
                           ('LUNCH', 'SOUPS', 'bn', 'স্যুপ', 'প্রতিদিন তাজা তৈরি গরম ও ঠান্ডা স্যুপ'),

                           -- =========================
                           -- LUNCH — MAIN_COURSE
                           -- =========================
                           ('LUNCH', 'MAIN_COURSE', 'en', 'Main Course', 'Hearty main course dishes for lunch'),
                           ('LUNCH', 'MAIN_COURSE', 'bn', 'প্রধান কোর্স', 'দুপুরের জন্য পরিপূর্ণ প্রধান কোর্সের পদ'),

                           -- =========================
                           -- LUNCH — DESSERTS
                           -- =========================
                           ('LUNCH', 'DESSERTS', 'en', 'Desserts', 'Sweet treats to finish your meal'),
                           ('LUNCH', 'DESSERTS', 'bn', 'মিষ্টান্ন', 'খাবার শেষে মিষ্টি পরিতৃপ্তির জন্য'),

                           -- =========================
                           -- LUNCH — BEVERAGES
                           -- =========================
                           ('LUNCH', 'BEVERAGES', 'en', 'Beverages', 'Drinks to complement your lunch'),
                           ('LUNCH', 'BEVERAGES', 'bn', 'পানীয়',
                            'দুপুরের খাবারের সাথে পানীয়')) v(menu_type_code, category_code, locale_code, name, description)
                      ON mt.code = v.menu_type_code AND mc.code = v.category_code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (menu_category_id, locale_id) DO NOTHING;
    END
$$;

