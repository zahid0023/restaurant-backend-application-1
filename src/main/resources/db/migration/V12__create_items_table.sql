CREATE TABLE IF NOT EXISTS items
(
    id           bigserial PRIMARY KEY,

    code         varchar(20)                  NOT NULL,
    item_type_id bigint                       NOT NULL REFERENCES item_types (id),
    unit_type_id bigint                       NOT NULL REFERENCES unit_types (id) ON DELETE RESTRICT,
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

CREATE TABLE IF NOT EXISTS item_locales
(
    id          bigserial PRIMARY KEY,

    item_id     bigint                       NOT NULL REFERENCES items (id) ON DELETE CASCADE,
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

    UNIQUE (item_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        -- =====================================================
        -- ITEMS SEED
        -- =====================================================

        INSERT INTO items (code, item_type_id, unit_type_id, sort_order, created_by, updated_by)
        SELECT v.item_code,
               it.id AS item_type_id,
               ut.id AS unit_type_id,
               v.sort_order,
               sys_id,
               sys_id
        FROM (VALUES ('WEIGHT', 'CHICKEN', 1, 'INGREDIENT'),
                     ('WEIGHT', 'BEEF', 2, 'INGREDIENT'),
                     ('WEIGHT', 'MUTTON', 3, 'INGREDIENT'),

                     ('WEIGHT', 'HILSA_FISH', 4, 'INGREDIENT'),
                     ('WEIGHT', 'RUI_FISH', 5, 'INGREDIENT'),
                     ('WEIGHT', 'SHRIMP', 6, 'INGREDIENT'),

                     ('WEIGHT', 'ONION', 7, 'INGREDIENT'),
                     ('WEIGHT', 'TOMATO', 8, 'INGREDIENT'),
                     ('WEIGHT', 'POTATO', 9, 'INGREDIENT'),
                     ('WEIGHT', 'GARLIC', 10, 'INGREDIENT'),
                     ('WEIGHT', 'GINGER', 11, 'INGREDIENT'),

                     ('WEIGHT', 'LEMON', 12, 'INGREDIENT'),
                     ('WEIGHT', 'MANGO', 13, 'INGREDIENT'),

                     ('WEIGHT', 'RICE', 14, 'INGREDIENT'),
                     ('WEIGHT', 'LENTIL', 15, 'INGREDIENT'),

                     ('VOLUME', 'MILK', 16, 'INGREDIENT'),
                     ('WEIGHT', 'BUTTER', 17, 'INGREDIENT'),
                     ('WEIGHT', 'YOGURT', 18, 'INGREDIENT'),

                     ('WEIGHT', 'TURMERIC', 19, 'INGREDIENT'),
                     ('WEIGHT', 'CUMIN', 20, 'INGREDIENT'),
                     ('WEIGHT', 'CHILI_POWDER', 21, 'INGREDIENT'),
                     ('WEIGHT', 'BLACK_PEPPER', 22, 'INGREDIENT'),
                     ('WEIGHT', 'CORIANDER', 23, 'INGREDIENT'),

                     ('VOLUME', 'COOKING_OIL', 24, 'INGREDIENT'),
                     ('VOLUME', 'GHEE', 25, 'INGREDIENT'),

                     ('VOLUME', 'SOY_SAUCE', 26, 'INGREDIENT'),
                     ('VOLUME', 'TOMATO_SAUCE', 27, 'INGREDIENT'),

                     ('WEIGHT', 'WHEAT_FLOUR', 28, 'INGREDIENT'),
                     ('WEIGHT', 'CHICKPEA_FLOUR', 29, 'INGREDIENT'),

                     ('COUNT', 'FOOD_CONT_SM', 30, 'PACKAGING'),
                     ('COUNT', 'FOOD_CONT_LG', 31, 'PACKAGING'),
                     ('COUNT', 'PIZZA_BOX', 32, 'PACKAGING'),
                     ('COUNT', 'MEAL_BOX', 33, 'PACKAGING'),
                     ('COUNT', 'WATER_BOTTLE', 34, 'PACKAGING'),
                     ('COUNT', 'CLING_WRAP', 35, 'PACKAGING'),
                     ('COUNT', 'PLASTIC_BAG', 36, 'PACKAGING'),
                     ('COUNT', 'PAPER_BAG', 37, 'PACKAGING'),
                     ('COUNT', 'DISPOSABLE_CUP', 38, 'PACKAGING'),
                     ('COUNT', 'COFFEE_CUP', 39, 'PACKAGING'),

                     ('COUNT', 'DINING_TABLE', 40, 'ASSET'),
                     ('COUNT', 'DINING_CHAIR', 41, 'ASSET'),
                     ('COUNT', 'DELIVERY_BIKE', 42, 'ASSET'),
                     ('COUNT', 'POS_SYSTEM', 43, 'ASSET'),
                     ('COUNT', 'MICROWAVE_UNIT', 44, 'ASSET'),

                     ('COUNT', 'CHEF_KNIFE', 45, 'EQUIPMENT'),
                     ('COUNT', 'SPATULA', 46, 'EQUIPMENT'),
                     ('COUNT', 'COOKING_LADLE', 47, 'EQUIPMENT'),
                     ('COUNT', 'BLENDER_UNIT', 48, 'EQUIPMENT'),
                     ('COUNT', 'COMM_FRIDGE', 49, 'EQUIPMENT'),
                     ('COUNT', 'MOP', 50, 'EQUIPMENT'),
                     ('COUNT', 'CUTLERY_SET', 51, 'EQUIPMENT')) v(unit_type_code, item_code, sort_order, item_type_code)
                 JOIN unit_types ut ON ut.code = v.unit_type_code
                 JOIN item_types it ON it.code = v.item_type_code
        WHERE NOT EXISTS (SELECT 1
                          FROM items i
                          WHERE i.code = v.item_code);


        -- =====================================================
        -- ITEM LOCALES SEED
        -- =====================================================

        INSERT INTO item_locales (item_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT i.id, l.id, v.name, v.description, i.sort_order, sys_id, sys_id
        FROM items i
                 JOIN (VALUES

                           -- =========================
                           -- MEAT
                           -- =========================
                           ('CHICKEN', 'en', 'Chicken', 'Fresh whole or cut chicken'),
                           ('CHICKEN', 'bn', 'মুরগি', 'তাজা মুরগির মাংস'),
                           ('BEEF', 'en', 'Beef', 'Fresh beef cuts'),
                           ('BEEF', 'bn', 'গরুর মাংস', 'তাজা গরুর মাংস'),
                           ('MUTTON', 'en', 'Mutton', 'Fresh mutton cuts'),
                           ('MUTTON', 'bn', 'খাসির মাংস', 'তাজা খাসির মাংস'),

                           -- =========================
                           -- FISH
                           -- =========================
                           ('HILSA_FISH', 'en', 'Hilsa Fish', 'Fresh hilsa fish'),
                           ('HILSA_FISH', 'bn', 'ইলিশ মাছ', 'তাজা ইলিশ মাছ'),
                           ('RUI_FISH', 'en', 'Rui Fish', 'Fresh rui carp fish'),
                           ('RUI_FISH', 'bn', 'রুই মাছ', 'তাজা রুই মাছ'),
                           ('SHRIMP', 'en', 'Shrimp', 'Fresh or frozen shrimp'),
                           ('SHRIMP', 'bn', 'চিংড়ি', 'তাজা বা হিমায়িত চিংড়ি'),

                           -- =========================
                           -- VEGETABLE
                           -- =========================
                           ('ONION', 'en', 'Onion', 'Fresh onions'),
                           ('ONION', 'bn', 'পেঁয়াজ', 'তাজা পেঁয়াজ'),
                           ('TOMATO', 'en', 'Tomato', 'Fresh red tomatoes'),
                           ('TOMATO', 'bn', 'টমেটো', 'তাজা লাল টমেটো'),
                           ('POTATO', 'en', 'Potato', 'Fresh potatoes'),
                           ('POTATO', 'bn', 'আলু', 'তাজা আলু'),
                           ('GARLIC', 'en', 'Garlic', 'Fresh garlic'),
                           ('GARLIC', 'bn', 'রসুন', 'তাজা রসুন'),
                           ('GINGER', 'en', 'Ginger', 'Fresh ginger root'),
                           ('GINGER', 'bn', 'আদা', 'তাজা আদা'),

                           -- =========================
                           -- FRUIT
                           -- =========================
                           ('LEMON', 'en', 'Lemon', 'Fresh lemons'),
                           ('LEMON', 'bn', 'লেবু', 'তাজা লেবু'),
                           ('MANGO', 'en', 'Mango', 'Fresh mangoes'),
                           ('MANGO', 'bn', 'আম', 'তাজা আম'),

                           -- =========================
                           -- RICE & GRAIN
                           -- =========================
                           ('RICE', 'en', 'Rice', 'Polished white rice'),
                           ('RICE', 'bn', 'চাল', 'পালিশ করা সাদা চাল'),
                           ('LENTIL', 'en', 'Lentil', 'Dried red lentils'),
                           ('LENTIL', 'bn', 'ডাল', 'শুকনো লাল মসুর ডাল'),

                           -- =========================
                           -- DAIRY
                           -- =========================
                           ('MILK', 'en', 'Milk', 'Fresh whole milk'),
                           ('MILK', 'bn', 'দুধ', 'তাজা গরুর দুধ'),
                           ('BUTTER', 'en', 'Butter', 'Unsalted butter'),
                           ('BUTTER', 'bn', 'মাখন', 'আনসল্টেড মাখন'),
                           ('YOGURT', 'en', 'Yogurt', 'Plain yogurt'),
                           ('YOGURT', 'bn', 'দই', 'সাদা দই'),

                           -- =========================
                           -- SPICE
                           -- =========================
                           ('TURMERIC', 'en', 'Turmeric', 'Ground turmeric powder'),
                           ('TURMERIC', 'bn', 'হলুদ', 'গুঁড়া হলুদ'),
                           ('CUMIN', 'en', 'Cumin', 'Ground cumin powder'),
                           ('CUMIN', 'bn', 'জিরা', 'গুঁড়া জিরা'),
                           ('CHILI_POWDER', 'en', 'Chili Powder', 'Red chili powder'),
                           ('CHILI_POWDER', 'bn', 'মরিচের গুঁড়া', 'লাল মরিচের গুঁড়া'),
                           ('BLACK_PEPPER', 'en', 'Black Pepper', 'Ground black pepper'),
                           ('BLACK_PEPPER', 'bn', 'কালো মরিচ', 'গুঁড়া কালো মরিচ'),
                           ('CORIANDER', 'en', 'Coriander', 'Ground coriander powder'),
                           ('CORIANDER', 'bn', 'ধনিয়া', 'গুঁড়া ধনিয়া'),

                           -- =========================
                           -- OIL & FAT
                           -- =========================
                           ('COOKING_OIL', 'en', 'Cooking Oil', 'Refined soybean cooking oil'),
                           ('COOKING_OIL', 'bn', 'রান্নার তেল', 'পরিশোধিত সয়াবিন তেল'),
                           ('GHEE', 'en', 'Ghee', 'Clarified butter ghee'),
                           ('GHEE', 'bn', 'ঘি', 'পরিশোধিত মাখনের ঘি'),

                           -- =========================
                           -- SAUCE
                           -- =========================
                           ('SOY_SAUCE', 'en', 'Soy Sauce', 'Dark soy sauce'),
                           ('SOY_SAUCE', 'bn', 'সয়া সস', 'গাঢ় সয়া সস'),
                           ('TOMATO_SAUCE', 'en', 'Tomato Sauce', 'Tomato ketchup sauce'),
                           ('TOMATO_SAUCE', 'bn', 'টমেটো সস', 'টমেটো কেচাপ সস'),

                           -- =========================
                           -- FLOUR
                           -- =========================
                           ('WHEAT_FLOUR', 'en', 'Wheat Flour', 'All-purpose wheat flour'),
                           ('WHEAT_FLOUR', 'bn', 'গমের আটা', 'সর্বজনীন গমের আটা'),
                           ('CHICKPEA_FLOUR', 'en', 'Chickpea Flour', 'Besan chickpea flour'),
                           ('CHICKPEA_FLOUR', 'bn', 'বেসন', 'ছোলার বেসন'),

                           -- =========================
                           -- PACKAGING / CONTAINER
                           -- =========================
                           ('FOOD_CONT_SM', 'en', 'Food Container Small', 'Small takeaway food container'),
                           ('FOOD_CONT_SM', 'bn', 'ছোট খাবার পাত্র', 'ছোট টেকঅ্যাওয়ে পাত্র'),
                           ('FOOD_CONT_LG', 'en', 'Food Container Large', 'Large takeaway food container'),
                           ('FOOD_CONT_LG', 'bn', 'বড় খাবার পাত্র', 'বড় টেকঅ্যাওয়ে পাত্র'),

                           -- =========================
                           -- PACKAGING / BOX
                           -- =========================
                           ('PIZZA_BOX', 'en', 'Pizza Box', 'Corrugated pizza delivery box'),
                           ('PIZZA_BOX', 'bn', 'পিজা বক্স', 'পিজা ডেলিভারি বক্স'),
                           ('MEAL_BOX', 'en', 'Meal Box', 'Single meal packaging box'),
                           ('MEAL_BOX', 'bn', 'মিল বক্স', 'একক খাবারের বক্স'),

                           -- =========================
                           -- PACKAGING / BOTTLE
                           -- =========================
                           ('WATER_BOTTLE', 'en', 'Water Bottle 500ml', 'Disposable plastic water bottle'),
                           ('WATER_BOTTLE', 'bn', 'পানির বোতল ৫০০মিলি', 'ডিসপোজেবল প্লাস্টিকের বোতল'),

                           -- =========================
                           -- PACKAGING / WRAPPER
                           -- =========================
                           ('CLING_WRAP', 'en', 'Cling Wrap Roll', 'Transparent food cling wrap'),
                           ('CLING_WRAP', 'bn', 'ক্লিং র‍্যাপ', 'স্বচ্ছ খাবারের র‍্যাপ'),

                           -- =========================
                           -- PACKAGING / BAG
                           -- =========================
                           ('PLASTIC_BAG', 'en', 'Plastic Bag', 'Small plastic carry bag'),
                           ('PLASTIC_BAG', 'bn', 'পলি ব্যাগ', 'ছোট পলিথিন ক্যারি ব্যাগ'),
                           ('PAPER_BAG', 'en', 'Paper Bag', 'Kraft paper carry bag'),
                           ('PAPER_BAG', 'bn', 'কাগজের ব্যাগ', 'ক্রাফট পেপার ক্যারি ব্যাগ'),

                           -- =========================
                           -- PACKAGING / CUP
                           -- =========================
                           ('DISPOSABLE_CUP', 'en', 'Disposable Cup', 'Single-use plastic cup'),
                           ('DISPOSABLE_CUP', 'bn', 'ডিসপোজেবল কাপ', 'একবার ব্যবহারের প্লাস্টিক কাপ'),
                           ('COFFEE_CUP', 'en', 'Coffee Cup', 'Insulated disposable coffee cup'),
                           ('COFFEE_CUP', 'bn', 'কফি কাপ', 'ইনসুলেটেড ডিসপোজেবল কাপ'),

                           -- =========================
                           -- ASSET / FURNITURE
                           -- =========================
                           ('DINING_TABLE', 'en', 'Dining Table', 'Restaurant dining table'),
                           ('DINING_TABLE', 'bn', 'ডাইনিং টেবিল', 'রেস্তোরাঁর ডাইনিং টেবিল'),
                           ('DINING_CHAIR', 'en', 'Dining Chair', 'Restaurant dining chair'),
                           ('DINING_CHAIR', 'bn', 'ডাইনিং চেয়ার', 'রেস্তোরাঁর ডাইনিং চেয়ার'),

                           -- =========================
                           -- ASSET / VEHICLE
                           -- =========================
                           ('DELIVERY_BIKE', 'en', 'Delivery Bike', 'Motorcycle for food delivery'),
                           ('DELIVERY_BIKE', 'bn', 'ডেলিভারি বাইক', 'খাবার ডেলিভারির মোটরসাইকেল'),

                           -- =========================
                           -- ASSET / IT_EQUIPMENT
                           -- =========================
                           ('POS_SYSTEM', 'en', 'POS System', 'Point of sale terminal'),
                           ('POS_SYSTEM', 'bn', 'পিওএস সিস্টেম', 'পয়েন্ট অব সেল টার্মিনাল'),

                           -- =========================
                           -- ASSET / APPLIANCE
                           -- =========================
                           ('MICROWAVE_UNIT', 'en', 'Microwave', 'Commercial microwave oven'),
                           ('MICROWAVE_UNIT', 'bn', 'মাইক্রোওয়েভ', 'কমার্শিয়াল মাইক্রোওয়েভ ওভেন'),

                           -- =========================
                           -- EQUIPMENT / KITCHEN_TOOL
                           -- =========================
                           ('CHEF_KNIFE', 'en', 'Chef Knife', 'Professional chef knife'),
                           ('CHEF_KNIFE', 'bn', 'শেফ ছুরি', 'পেশাদার শেফ ছুরি'),
                           ('SPATULA', 'en', 'Spatula', 'Flat cooking spatula'),
                           ('SPATULA', 'bn', 'স্প্যাচুলা', 'চ্যাপ্টা রান্নার স্প্যাচুলা'),
                           ('COOKING_LADLE', 'en', 'Cooking Ladle', 'Long handle soup ladle'),
                           ('COOKING_LADLE', 'bn', 'রান্নার খুন্তি', 'লম্বা হাতলের স্যুপ লাডেল'),

                           -- =========================
                           -- EQUIPMENT / COOKING_MACHINE
                           -- =========================
                           ('BLENDER_UNIT', 'en', 'Blender', 'Commercial kitchen blender'),
                           ('BLENDER_UNIT', 'bn', 'ব্লেন্ডার', 'কমার্শিয়াল কিচেন ব্লেন্ডার'),

                           -- =========================
                           -- EQUIPMENT / REFRIGERATOR
                           -- =========================
                           ('COMM_FRIDGE', 'en', 'Commercial Fridge', 'Commercial refrigerator unit'),
                           ('COMM_FRIDGE', 'bn', 'কমার্শিয়াল ফ্রিজ', 'বাণিজ্যিক রেফ্রিজারেটর'),

                           -- =========================
                           -- EQUIPMENT / CLEANING_TOOL
                           -- =========================
                           ('MOP', 'en', 'Mop', 'Floor cleaning mop'),
                           ('MOP', 'bn', 'মপ', 'মেঝে পরিষ্কারের মপ'),

                           -- =========================
                           -- EQUIPMENT / CUTLERY
                           -- =========================
                           ('CUTLERY_SET', 'en', 'Cutlery Set', 'Fork, knife and spoon set'),
                           ('CUTLERY_SET', 'bn', 'কাটলারি সেট',
                            'চামচ, কাঁটা ও ছুরির সেট')) v(item_code, locale_code, name, description)
                      ON i.code = v.item_code
                 JOIN locales l ON l.code = v.locale_code
        WHERE NOT EXISTS (SELECT 1
                          FROM item_locales il2
                          WHERE il2.item_id = i.id
                            AND il2.locale_id = l.id);


    END
$$;
