DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        -- =============================================
        -- 1. Menu Type
        -- =============================================
        INSERT INTO menu_types (code, sort_order, created_by, updated_by)
        VALUES ('MAIN', 1, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        INSERT INTO menu_type_locales (menu_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT mt.id, l.id, v.name, v.description, mt.sort_order, sys_id, sys_id
        FROM menu_types mt
                 JOIN (VALUES
                           ('MAIN', 'en', 'Main Menu', 'The main dining menu of the restaurant.')
                      ) v(code, locale_code, name, description) ON mt.code = v.code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (menu_id, locale_id) DO NOTHING;

        -- =============================================
        -- 2. Menu Categories
        -- =============================================
        INSERT INTO menu_categories (menu_type_id, code, sort_order, created_by, updated_by)
        SELECT mt.id, v.code, v.sort_order, sys_id, sys_id
        FROM menu_types mt
                 JOIN (VALUES
                           ('MAIN', 'ROASTED_TALES',       1),
                           ('MAIN', 'ICED_COFFEE_AND_TEA', 2),
                           ('MAIN', 'BLENDED_FROZENS',     3),
                           ('MAIN', 'HOT_SPECIALS',        4),
                           ('MAIN', 'MOJITO_REFRESHERS',   5),
                           ('MAIN', 'REFRESHERS',          6),
                           ('MAIN', 'SHAKES',              7),
                           ('MAIN', 'BEVERAGE',            8)
                      ) v(menu_type_code, code, sort_order) ON mt.code = v.menu_type_code
        ON CONFLICT (code) DO NOTHING;

        -- =============================================
        -- 3. Menu Category Locales (English)
        -- =============================================
        INSERT INTO menu_category_locales (menu_category_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT mc.id, l.id, v.name, v.description, mc.sort_order, sys_id, sys_id
        FROM menu_categories mc
                 JOIN (VALUES
                           ('ROASTED_TALES',       'en', 'Roasted Tales',       'A curated selection of expertly roasted espresso-based coffees.'),
                           ('ICED_COFFEE_AND_TEA', 'en', 'Iced Coffee & Tea',   'Chilled coffee and tea beverages served over ice for a cool, refreshing experience.'),
                           ('BLENDED_FROZENS',     'en', 'Blended Frozens',     'Frosty blended drinks including frappes and smoothies for a frozen indulgence.'),
                           ('HOT_SPECIALS',        'en', 'Hot Specials',        'Warm and comforting hot beverages including teas, hot chocolate, and classic coffees.'),
                           ('MOJITO_REFRESHERS',   'en', 'Mojito Refreshers',   'Crisp and minty mocktail-style refreshers inspired by classic mojito flavors.'),
                           ('REFRESHERS',          'en', 'Refreshers',          'Light and fruity non-alcoholic refreshers and mocktails bursting with natural flavors.'),
                           ('SHAKES',              'en', 'Shakes',              'Thick and indulgent milkshakes blended with ice cream and a variety of bold flavors.'),
                           ('BEVERAGE',            'en', 'Beverage',            'A selection of everyday beverages including water, soft drinks, and traditional drinks.')
                      ) v(code, locale_code, name, description) ON mc.code = v.code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (menu_category_id, locale_id) DO NOTHING;

    END
$$;
