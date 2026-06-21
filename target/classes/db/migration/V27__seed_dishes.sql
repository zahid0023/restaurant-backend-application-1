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
        VALUES ('ESPRESSO_KICK', 1, sys_id, sys_id),
               ('MACCHIATO', 2, sys_id, sys_id),
               ('AMERICANO', 3, sys_id, sys_id),
               ('CAPPUCCINO', 4, sys_id, sys_id),
               ('CAFE_LATTE', 5, sys_id, sys_id),
               ('FLAVORED_LATTE', 6, sys_id, sys_id),
               ('CAFE_MOCHA', 7, sys_id, sys_id),
               ('AFFOGATO', 8, sys_id, sys_id),
               ('ICED_AMERICANO', 9, sys_id, sys_id),
               ('ICED_CAPPUCCINO', 10, sys_id, sys_id),
               ('ICED_LATTE', 11, sys_id, sys_id),
               ('ICED_FLAVOURED_LATTE', 12, sys_id, sys_id),
               ('ICED_MOCHA', 13, sys_id, sys_id),
               ('PEACH_ICED_TEA', 14, sys_id, sys_id),
               ('THAI_MILK_TEA', 15, sys_id, sys_id),
               ('CLASSIC_COLD_COFFEE', 16, sys_id, sys_id),
               ('MOCHA_FRAPPE', 17, sys_id, sys_id),
               ('CAFE_FRAPPE', 18, sys_id, sys_id),
               ('FLAVOURED_FRAPPE', 19, sys_id, sys_id),
               ('MANGO_YOGHURT_SMOOTHIE', 20, sys_id, sys_id),
               ('BANANA_NIRVANA_SMOOTHIE', 21, sys_id, sys_id),
               ('CLASSIC_MILK_TEA', 22, sys_id, sys_id),
               ('CLASSIC_HOT_COFFEE', 23, sys_id, sys_id),
               ('GINGER_GREEN_TEA', 24, sys_id, sys_id),
               ('HONEY_LEMON_TEA', 25, sys_id, sys_id),
               ('CHAI_LATTE', 26, sys_id, sys_id),
               ('CLASSIC_HOT_CHOCOLATE', 27, sys_id, sys_id),
               ('LIME_SUBLIME', 28, sys_id, sys_id),
               ('BLUE_LAGOON', 29, sys_id, sys_id),
               ('GOLDEN_HOUR_GLOW', 30, sys_id, sys_id),
               ('STRAWBERRY_CRUSH', 31, sys_id, sys_id),
               ('TROPICAL_DAWN', 32, sys_id, sys_id),
               ('THE_NINTH_CHAPTER', 33, sys_id, sys_id),
               ('PAPAYA_PARADISE', 34, sys_id, sys_id),
               ('ORANGE_SUNRISE', 35, sys_id, sys_id),
               ('MINT_LEMON', 36, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        -- =============================================
        -- 2. Dish Locales (English)
        -- =============================================
        INSERT INTO dishes_locales (dish_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT d.id, l.id, v.name, v.description, d.sort_order, sys_id, sys_id
        FROM dishes d
                 JOIN (VALUES ('ESPRESSO_KICK', 'en', 'Espresso Kick',
                               'A bold, concentrated espresso delivering an instant energy kick, available as a single or double shot.'),
                              ('MACCHIATO', 'en', 'Macchiato',
                               'A rich espresso stained with a small splash of steamed milk, balancing intensity with a creamy touch.'),
                              ('AMERICANO', 'en', 'Americano',
                               'A smooth and mellow espresso lengthened with hot water, offering a clean, full-bodied coffee experience.'),
                              ('CAPPUCCINO', 'en', 'Cappuccino',
                               'A classic Italian favorite with equal parts espresso, steamed milk, and velvety milk foam in every sip.'),
                              ('CAFE_LATTE', 'en', 'Cafe Latte',
                               'A creamy and comforting blend of espresso and steamed milk with a delicate foam layer on top.'),
                              ('FLAVORED_LATTE', 'en', 'Flavored Latte',
                               'A creamy latte elevated with your choice of vanilla, caramel, or hazelnut syrup for a sweet aromatic twist.'),
                              ('CAFE_MOCHA', 'en', 'Cafe Mocha',
                               'A luxurious blend of espresso and rich chocolate syrup topped with steamed milk and whipped cream.'),
                              ('AFFOGATO', 'en', 'Affogato',
                               'A simple Italian indulgence — a hot shot of espresso poured over creamy vanilla ice cream.'),
                              ('ICED_AMERICANO', 'en', 'Iced Americano',
                               'A refreshingly chilled espresso served over ice, smooth and bold with every sip.'),
                              ('ICED_CAPPUCCINO', 'en', 'Iced Cappuccino',
                               'A cold and frothy version of the classic cappuccino, layered over ice for a refreshing experience.'),
                              ('ICED_LATTE', 'en', 'Iced Latte',
                               'A smooth shot of espresso blended with cold milk and poured over ice for an effortlessly refreshing drink.'),
                              ('ICED_FLAVOURED_LATTE', 'en', 'Iced Flavoured Latte',
                               'A chilled latte enriched with your choice of vanilla, caramel, or hazelnut, served over ice.'),
                              ('ICED_MOCHA', 'en', 'Iced Mocha',
                               'A frosty chocolate-espresso indulgence blended with cold milk and poured over ice.'),
                              ('PEACH_ICED_TEA', 'en', 'Peach Iced Tea',
                               'A light and refreshing iced tea infused with the sweet, fruity notes of ripe peach.'),
                              ('THAI_MILK_TEA', 'en', 'Thai Milk Tea',
                               'A richly brewed Thai-style tea blended with sweetened condensed milk for a creamy, aromatic delight.'),
                              ('CLASSIC_COLD_COFFEE', 'en', 'Classic Cold Coffee',
                               'A simple and satisfying chilled coffee blended with milk, cool and refreshing.'),
                              ('MOCHA_FRAPPE', 'en', 'Mocha Frappe',
                               'A frozen blend of espresso, chocolate, and cold milk topped with whipped cream — indulgence in a glass.'),
                              ('CAFE_FRAPPE', 'en', 'Cafe Frappe',
                               'A smooth, frosty blended coffee drink with a creamy finish, perfect for warm days.'),
                              ('FLAVOURED_FRAPPE', 'en', 'Flavoured Frappe',
                               'A blended iced frappe with your choice of vanilla, caramel, or hazelnut for a flavorful frozen treat.'),
                              ('MANGO_YOGHURT_SMOOTHIE', 'en', 'Mango Yoghurt Smoothie',
                               'A thick and tropical blend of ripe mango and creamy yoghurt, naturally refreshing and wholesome.'),
                              ('BANANA_NIRVANA_SMOOTHIE', 'en', 'Banana Nirvana Smoothie',
                               'A velvety blend of ripe bananas and creamy milk, naturally sweet and deeply satisfying.'),
                              ('CLASSIC_MILK_TEA', 'en', 'Classic Milk Tea',
                               'A comforting blend of strong brewed tea and smooth milk — simple, warm, and timeless.'),
                              ('CLASSIC_HOT_COFFEE', 'en', 'Classic Hot Coffee',
                               'A no-fuss hot brewed coffee, bold and satisfying for coffee purists.'),
                              ('GINGER_GREEN_TEA', 'en', 'Ginger Green Tea',
                               'A soothing brew of green tea with the warm, spicy kick of fresh ginger for a calming experience.'),
                              ('HONEY_LEMON_TEA', 'en', 'Honey Lemon Tea',
                               'A warm and calming tea sweetened with natural honey and brightened with a hint of fresh lemon.'),
                              ('CHAI_LATTE', 'en', 'Chai Latte',
                               'A warming spiced tea blended with steamed milk for a rich, aromatic, and comforting cup.'),
                              ('CLASSIC_HOT_CHOCOLATE', 'en', 'Classic Hot Chocolate',
                               'A rich and velvety hot chocolate made with premium cocoa and steamed milk, indulgent and warming.'),
                              ('LIME_SUBLIME', 'en', 'Lime Sublime',
                               'A zesty and revitalizing mocktail with fresh lime juice, mint, and a hint of fizz.'),
                              ('BLUE_LAGOON', 'en', 'Blue Lagoon',
                               'A visually stunning tropical mocktail with blue curacao flavor, citrus, and a refreshing finish.'),
                              ('GOLDEN_HOUR_GLOW', 'en', 'Golden Hour Glow',
                               'A warm and luminous mocktail inspired by sunset hues, blending citrus and tropical flavors.'),
                              ('STRAWBERRY_CRUSH', 'en', 'Strawberry Crush',
                               'A vibrant and fruity mocktail bursting with the sweet essence of fresh strawberries and a fizzy finish.'),
                              ('TROPICAL_DAWN', 'en', 'Tropical Dawn',
                               'A bright and exotic blend of tropical fruits evoking the freshness of a golden sunrise.'),
                              ('THE_NINTH_CHAPTER', 'en', 'The Ninth Chapter',
                               'A signature creation with a complex blend of exotic flavors that tells a story in every sip.'),
                              ('PAPAYA_PARADISE', 'en', 'Papaya Paradise',
                               'A lush tropical mocktail featuring the natural sweetness of ripe papaya in a refreshing blend.'),
                              ('ORANGE_SUNRISE', 'en', 'Orange Sunrise',
                               'A bright and citrusy mocktail inspired by the warm colors of morning, tangy and uplifting.'),
                              ('MINT_LEMON', 'en', 'Mint Lemon',
                               'A cool and invigorating mocktail with fresh mint leaves and zesty lemon for a crisp, revitalizing sip.')) v(code, locale_code, name, description)
                      ON d.code = v.code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (dish_id, locale_id) DO NOTHING;

        -- =============================================
        -- 3. Dish Variants
        -- =============================================
        INSERT INTO dish_variants (dish_id, code, sort_order, price, is_default, is_veg, created_by, updated_by)
        SELECT d.id,
               v.variant_code,
               v.sort_order,
               v.price,
               v.is_default,
               true,
               sys_id,
               sys_id
        FROM dishes d
                 JOIN (VALUES
                           -- Espresso Kick
                           ('ESPRESSO_KICK', 'ESPRESSO_KICK_SINGLE', 1, 160.00, true),
                           ('ESPRESSO_KICK', 'ESPRESSO_KICK_DOUBLE', 2, 190.00, false),

                           -- Macchiato
                           ('MACCHIATO', 'MACCHIATO_SINGLE', 1, 200.00, true),

                           -- Americano
                           ('AMERICANO', 'AMERICANO_SINGLE', 1, 190.00, true),

                           -- Cappuccino
                           ('CAPPUCCINO', 'CAPPUCCINO_SINGLE', 1, 250.00, true),

                           -- Cafe Latte
                           ('CAFE_LATTE', 'CAFE_LATTE_SINGLE', 1, 250.00, true),

                           -- Flavored Latte
                           ('FLAVORED_LATTE', 'FLAVORED_LATTE_VANILLA', 1, 330.00, true),
                           ('FLAVORED_LATTE', 'FLAVORED_LATTE_CARAMEL', 2, 330.00, false),
                           ('FLAVORED_LATTE', 'FLAVORED_LATTE_HAZELNUT', 3, 330.00, false),

                           -- Cafe Mocha
                           ('CAFE_MOCHA', 'CAFE_MOCHA_SINGLE', 1, 350.00, true),

                           -- Affogato
                           ('AFFOGATO', 'AFFOGATO_SINGLE', 1, 250.00, true),

                           -- Iced Americano
                           ('ICED_AMERICANO', 'ICED_AMERICANO_SINGLE', 1, 210.00, true),

                           -- Iced Cappuccino
                           ('ICED_CAPPUCCINO', 'ICED_CAPPUCCINO_SINGLE', 1, 270.00, true),

                           -- Iced Latte
                           ('ICED_LATTE', 'ICED_LATTE_SINGLE', 1, 270.00, true),

                           -- Iced Flavoured Latte
                           ('ICED_FLAVOURED_LATTE', 'ICED_FLAVOURED_LATTE_VANILLA', 1, 360.00, true),
                           ('ICED_FLAVOURED_LATTE', 'ICED_FLAVOURED_LATTE_CARAMEL', 2, 360.00, false),
                           ('ICED_FLAVOURED_LATTE', 'ICED_FLAVOURED_LATTE_HAZELNUT', 3, 360.00, false),

                           -- Iced Mocha
                           ('ICED_MOCHA', 'ICED_MOCHA_SINGLE', 1, 370.00, true),

                           -- Peach Iced Tea
                           ('PEACH_ICED_TEA', 'PEACH_ICED_TEA_SINGLE', 1, 240.00, true),

                           -- Thai Milk Tea
                           ('THAI_MILK_TEA', 'THAI_MILK_TEA_SINGLE', 1, 190.00, true),

                           -- Classic Cold Coffee
                           ('CLASSIC_COLD_COFFEE', 'CLASSIC_COLD_COFFEE_SINGLE', 1, 180.00, true),

                           -- Mocha Frappe
                           ('MOCHA_FRAPPE', 'MOCHA_FRAPPE_SINGLE', 1, 380.00, true),

                           -- Cafe Frappe
                           ('CAFE_FRAPPE', 'CAFE_FRAPPE_SINGLE', 1, 320.00, true),

                           -- Flavoured Frappe
                           ('FLAVOURED_FRAPPE', 'FLAVOURED_FRAPPE_VANILLA', 1, 370.00, true),
                           ('FLAVOURED_FRAPPE', 'FLAVOURED_FRAPPE_CARAMEL', 2, 370.00, false),
                           ('FLAVOURED_FRAPPE', 'FLAVOURED_FRAPPE_HAZELNUT', 3, 370.00, false),

                           -- Mango Yoghurt Smoothie
                           ('MANGO_YOGHURT_SMOOTHIE', 'MANGO_YOGHURT_SMOOTHIE_SINGLE', 1, 280.00, true),

                           -- Banana Nirvana Smoothie
                           ('BANANA_NIRVANA_SMOOTHIE', 'BANANA_NIRVANA_SMOOTHIE_SINGLE', 1, 280.00, true),

                           -- Classic Milk Tea
                           ('CLASSIC_MILK_TEA', 'CLASSIC_MILK_TEA_SINGLE', 1, 50.00, true),

                           -- Classic Hot Coffee
                           ('CLASSIC_HOT_COFFEE', 'CLASSIC_HOT_COFFEE_SINGLE', 1, 150.00, true),

                           -- Ginger Green Tea
                           ('GINGER_GREEN_TEA', 'GINGER_GREEN_TEA_SINGLE', 1, 90.00, true),

                           -- Honey Lemon Tea
                           ('HONEY_LEMON_TEA', 'HONEY_LEMON_TEA_SINGLE', 1, 120.00, true),

                           -- Chai Latte
                           ('CHAI_LATTE', 'CHAI_LATTE_SINGLE', 1, 320.00, true),

                           -- Classic Hot Chocolate
                           ('CLASSIC_HOT_CHOCOLATE', 'CLASSIC_HOT_CHOCOLATE_SINGLE', 1, 280.00, true),

                           -- Lime Sublime
                           ('LIME_SUBLIME', 'LIME_SUBLIME_SINGLE', 1, 270.00, true),

                           -- Blue Lagoon
                           ('BLUE_LAGOON', 'BLUE_LAGOON_SINGLE', 1, 290.00, true),

                           -- Golden Hour Glow
                           ('GOLDEN_HOUR_GLOW', 'GOLDEN_HOUR_GLOW_SINGLE', 1, 320.00, true),

                           -- Strawberry Crush
                           ('STRAWBERRY_CRUSH', 'STRAWBERRY_CRUSH_SINGLE', 1, 290.00, true),

                           -- Tropical Dawn
                           ('TROPICAL_DAWN', 'TROPICAL_DAWN_SINGLE', 1, 320.00, true),

                           -- The Ninth Chapter
                           ('THE_NINTH_CHAPTER', 'THE_NINTH_CHAPTER_SINGLE', 1, 350.00, true),

                           -- Papaya Paradise
                           ('PAPAYA_PARADISE', 'PAPAYA_PARADISE_SINGLE', 1, 160.00, true),

                           -- Orange Sunrise
                           ('ORANGE_SUNRISE', 'ORANGE_SUNRISE_SINGLE', 1, 190.00, true),

                           -- Mint Lemon (TODO: update price)
                           ('MINT_LEMON', 'MINT_LEMON_SINGLE', 1, 0.00,
                            true)) v(dish_code, variant_code, sort_order, price, is_default) ON d.code = v.dish_code
        ON CONFLICT (code) DO NOTHING;

        -- =============================================
        -- 4. Dish Variant Locales (English)
        -- =============================================
        INSERT INTO dish_variant_locales (dish_variant_id, locale_id, name, sort_order, created_by, updated_by)
        SELECT dv.id, l.id, v.name, dv.sort_order, sys_id, sys_id
        FROM dish_variants dv
                 JOIN (VALUES ('ESPRESSO_KICK_SINGLE', 'en', 'Single'),
                              ('ESPRESSO_KICK_DOUBLE', 'en', 'Double'),
                              ('MACCHIATO_SINGLE', 'en', 'Single'),
                              ('AMERICANO_SINGLE', 'en', 'Single'),
                              ('CAPPUCCINO_SINGLE', 'en', 'Single'),
                              ('CAFE_LATTE_SINGLE', 'en', 'Single'),
                              ('FLAVORED_LATTE_VANILLA', 'en', 'Vanilla'),
                              ('FLAVORED_LATTE_CARAMEL', 'en', 'Caramel'),
                              ('FLAVORED_LATTE_HAZELNUT', 'en', 'Hazelnut'),
                              ('CAFE_MOCHA_SINGLE', 'en', 'Single'),
                              ('AFFOGATO_SINGLE', 'en', 'Single'),
                              ('ICED_AMERICANO_SINGLE', 'en', 'Single'),
                              ('ICED_CAPPUCCINO_SINGLE', 'en', 'Single'),
                              ('ICED_LATTE_SINGLE', 'en', 'Single'),
                              ('ICED_FLAVOURED_LATTE_VANILLA', 'en', 'Vanilla'),
                              ('ICED_FLAVOURED_LATTE_CARAMEL', 'en', 'Caramel'),
                              ('ICED_FLAVOURED_LATTE_HAZELNUT', 'en', 'Hazelnut'),
                              ('ICED_MOCHA_SINGLE', 'en', 'Single'),
                              ('PEACH_ICED_TEA_SINGLE', 'en', 'Single'),
                              ('THAI_MILK_TEA_SINGLE', 'en', 'Single'),
                              ('CLASSIC_COLD_COFFEE_SINGLE', 'en', 'Single'),
                              ('MOCHA_FRAPPE_SINGLE', 'en', 'Single'),
                              ('CAFE_FRAPPE_SINGLE', 'en', 'Single'),
                              ('FLAVOURED_FRAPPE_VANILLA', 'en', 'Vanilla'),
                              ('FLAVOURED_FRAPPE_CARAMEL', 'en', 'Caramel'),
                              ('FLAVOURED_FRAPPE_HAZELNUT', 'en', 'Hazelnut'),
                              ('MANGO_YOGHURT_SMOOTHIE_SINGLE', 'en', 'Single'),
                              ('BANANA_NIRVANA_SMOOTHIE_SINGLE', 'en', 'Single'),
                              ('CLASSIC_MILK_TEA_SINGLE', 'en', 'Single'),
                              ('CLASSIC_HOT_COFFEE_SINGLE', 'en', 'Single'),
                              ('GINGER_GREEN_TEA_SINGLE', 'en', 'Single'),
                              ('HONEY_LEMON_TEA_SINGLE', 'en', 'Single'),
                              ('CHAI_LATTE_SINGLE', 'en', 'Single'),
                              ('CLASSIC_HOT_CHOCOLATE_SINGLE', 'en', 'Single'),
                              ('LIME_SUBLIME_SINGLE', 'en', 'Single'),
                              ('BLUE_LAGOON_SINGLE', 'en', 'Single'),
                              ('GOLDEN_HOUR_GLOW_SINGLE', 'en', 'Single'),
                              ('STRAWBERRY_CRUSH_SINGLE', 'en', 'Single'),
                              ('TROPICAL_DAWN_SINGLE', 'en', 'Single'),
                              ('THE_NINTH_CHAPTER_SINGLE', 'en', 'Single'),
                              ('PAPAYA_PARADISE_SINGLE', 'en', 'Single'),
                              ('ORANGE_SUNRISE_SINGLE', 'en', 'Single'),
                              ('MINT_LEMIN_SINGLE', 'en', 'Single')) v(variant_code, locale_code, name)
                      ON dv.code = v.variant_code
                 JOIN locales l ON l.code = v.locale_code
        ON CONFLICT (dish_variant_id, locale_id) DO NOTHING;

    END
$$;
