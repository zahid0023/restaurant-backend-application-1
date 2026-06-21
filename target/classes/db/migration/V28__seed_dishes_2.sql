DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        -- =============================================
        -- 1. Dishes
        -- =============================================
        INSERT INTO dishes (code, sort_order, created_by, updated_by)
        VALUES ('ORANGE_LEMONADE',    37, sys_id, sys_id),
               ('PINEAPPLE_PUNCH',    38, sys_id, sys_id),
               ('WATERMELON_BREEZE',  39, sys_id, sys_id),
               ('CLASSIC_LASSI',      40, sys_id, sys_id),
               ('FLAVOURED_LASSI',    41, sys_id, sys_id),
               ('STRAWBERRY_BLISS',   42, sys_id, sys_id),
               ('VANILLA_CLOUD',      43, sys_id, sys_id),
               ('MIDNIGHT_CHOCO',     44, sys_id, sys_id),
               ('OREO_DOREO',         45, sys_id, sys_id),
               ('SNICKER_PUNCH',      46, sys_id, sys_id),
               ('BROWNIE_BLAST',      47, sys_id, sys_id),
               ('BUTTERSCOTCH_SILK',  48, sys_id, sys_id),
               ('MINERAL_WATER',      49, sys_id, sys_id),
               ('SOFT_DRINKS',        50, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        -- =============================================
        -- 2. Dish Locales (English)
        -- =============================================
        INSERT INTO dishes_locales (dish_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT d.id, l.id, v.name, v.description, d.sort_order, sys_id, sys_id
        FROM dishes d
                 JOIN (VALUES
                           ('ORANGE_LEMONADE',   'en', 'Orange Lemonade',   'A bright and citrusy blend of fresh orange and tangy lemon, perfectly balanced and refreshing.'),
                           ('PINEAPPLE_PUNCH',   'en', 'Pineapple Punch',   'A tropical burst of sweet pineapple, bright and uplifting — best enjoyed when in season.'),
                           ('WATERMELON_BREEZE', 'en', 'Watermelon Breeze', 'A cool and hydrating blend of fresh watermelon juice, light and perfectly refreshing — available seasonally.'),
                           ('CLASSIC_LASSI',     'en', 'Classic Lassi',     'A traditional South Asian yoghurt-based drink, smooth, slightly tangy, and wonderfully cooling.'),
                           ('FLAVOURED_LASSI',   'en', 'Flavoured Lassi',   'A creamy yoghurt-based lassi infused with your choice of mango, strawberry, or mint flavor.'),
                           ('STRAWBERRY_BLISS',  'en', 'Strawberry Bliss',  'A rich and indulgent shake blending fresh strawberries with smooth ice cream for a blissful sip.'),
                           ('VANILLA_CLOUD',     'en', 'Vanilla Cloud',     'A dreamy and velvety milkshake made with premium vanilla ice cream, light and indulgent.'),
                           ('MIDNIGHT_CHOCO',    'en', 'Midnight Choco',    'A decadent chocolate milkshake with deep, rich cocoa flavor and a smooth, velvety finish.'),
                           ('OREO_DOREO',        'en', 'Oreo Doreo',        'A fun and indulgent milkshake blending crushed Oreo cookies with creamy vanilla ice cream.'),
                           ('SNICKER_PUNCH',     'en', 'Snicker Punch',     'A thick and indulgent milkshake inspired by the classic Snickers bar — caramel, chocolate, and peanut.'),
                           ('BROWNIE_BLAST',     'en', 'Brownie Blast',     'A rich and fudgy milkshake loaded with chunks of brownie blended with creamy ice cream.'),
                           ('BUTTERSCOTCH_SILK', 'en', 'Butterscotch Silk', 'A smooth and luscious milkshake with warm butterscotch drizzle and silky ice cream.'),
                           ('MINERAL_WATER',     'en', 'Mineral Water',     'Pure, crisp, and refreshing still mineral water.'),
                           ('SOFT_DRINKS',       'en', 'Soft Drinks',       'A classic chilled carbonated soft drink to complement any meal.')
                      ) v(code, locale_code, name, description) ON d.code = v.code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (dish_id, locale_id) DO NOTHING;

        -- =============================================
        -- 3. Dish Variants
        -- =============================================
        INSERT INTO dish_variants (dish_id, code, sort_order, price, is_default, is_veg, created_by, updated_by)
        SELECT d.id, v.variant_code, v.sort_order, v.price, v.is_default, true, sys_id, sys_id
        FROM dishes d
                 JOIN (VALUES
                           -- Orange Lemonade
                           ('ORANGE_LEMONADE',   'ORANGE_LEMONADE_SINGLE',        1, 170.00, true),

                           -- Pineapple Punch
                           ('PINEAPPLE_PUNCH',   'PINEAPPLE_PUNCH_SEASONAL',      1, 190.00, true),

                           -- Watermelon Breeze
                           ('WATERMELON_BREEZE', 'WATERMELON_BREEZE_SEASONAL',    1, 180.00, true),

                           -- Classic Lassi
                           ('CLASSIC_LASSI',     'CLASSIC_LASSI_SINGLE',          1, 150.00, true),

                           -- Flavoured Lassi
                           ('FLAVOURED_LASSI',   'FLAVOURED_LASSI_MANGO',         1, 190.00, true),
                           ('FLAVOURED_LASSI',   'FLAVOURED_LASSI_STRAWBERRY',    2, 190.00, false),
                           ('FLAVOURED_LASSI',   'FLAVOURED_LASSI_MINT',          3, 190.00, false),

                           -- Strawberry Bliss
                           ('STRAWBERRY_BLISS',  'STRAWBERRY_BLISS_SINGLE',       1, 220.00, true),

                           -- Vanilla Cloud
                           ('VANILLA_CLOUD',     'VANILLA_CLOUD_SINGLE',          1, 220.00, true),

                           -- Midnight Choco
                           ('MIDNIGHT_CHOCO',    'MIDNIGHT_CHOCO_SINGLE',         1, 220.00, true),

                           -- Oreo Doreo
                           ('OREO_DOREO',        'OREO_DOREO_SINGLE',             1, 240.00, true),

                           -- Snicker Punch
                           ('SNICKER_PUNCH',     'SNICKER_PUNCH_SINGLE',          1, 250.00, true),

                           -- Brownie Blast
                           ('BROWNIE_BLAST',     'BROWNIE_BLAST_SINGLE',          1, 260.00, true),

                           -- Butterscotch Silk
                           ('BUTTERSCOTCH_SILK', 'BUTTERSCOTCH_SILK_SINGLE',      1, 260.00, true),

                           -- Mineral Water
                           ('MINERAL_WATER',     'MINERAL_WATER_SINGLE',          1,  20.00, true),

                           -- Soft Drinks
                           ('SOFT_DRINKS',       'SOFT_DRINKS_SINGLE',            1,  50.00, true)

                      ) v(dish_code, variant_code, sort_order, price, is_default) ON d.code = v.dish_code
        ON CONFLICT (code) DO NOTHING;

        -- =============================================
        -- 4. Dish Variant Locales (English)
        -- =============================================
        INSERT INTO dish_variant_locales (dish_variant_id, locale_id, name, sort_order, created_by, updated_by)
        SELECT dv.id, l.id, v.name, dv.sort_order, sys_id, sys_id
        FROM dish_variants dv
                 JOIN (VALUES
                           ('ORANGE_LEMONADE_SINGLE',     'en', 'Single'),
                           ('PINEAPPLE_PUNCH_SEASONAL',   'en', 'Seasonal'),
                           ('WATERMELON_BREEZE_SEASONAL', 'en', 'Seasonal'),
                           ('CLASSIC_LASSI_SINGLE',       'en', 'Single'),
                           ('FLAVOURED_LASSI_MANGO',      'en', 'Mango'),
                           ('FLAVOURED_LASSI_STRAWBERRY', 'en', 'Strawberry'),
                           ('FLAVOURED_LASSI_MINT',       'en', 'Mint'),
                           ('STRAWBERRY_BLISS_SINGLE',    'en', 'Single'),
                           ('VANILLA_CLOUD_SINGLE',       'en', 'Single'),
                           ('MIDNIGHT_CHOCO_SINGLE',      'en', 'Single'),
                           ('OREO_DOREO_SINGLE',          'en', 'Single'),
                           ('SNICKER_PUNCH_SINGLE',       'en', 'Single'),
                           ('BROWNIE_BLAST_SINGLE',       'en', 'Single'),
                           ('BUTTERSCOTCH_SILK_SINGLE',   'en', 'Single'),
                           ('MINERAL_WATER_SINGLE',       'en', 'Single'),
                           ('SOFT_DRINKS_SINGLE',         'en', 'Single')
                      ) v(variant_code, locale_code, name) ON dv.code = v.variant_code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (dish_variant_id, locale_id) DO NOTHING;

    END
$$;
