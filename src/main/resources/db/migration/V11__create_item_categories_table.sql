CREATE TABLE IF NOT EXISTS item_categories
(
    id           bigserial PRIMARY KEY,

    item_type_id bigint                   NOT NULL REFERENCES item_types (id) ON DELETE RESTRICT,
    parent_id    bigint REFERENCES item_categories (id),

    code         varchar(50)              NOT NULL,
    sort_order   int                      NOT NULL DEFAULT 0,

    created_by   bigint                   NOT NULL REFERENCES users (id),
    created_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint                   NOT NULL REFERENCES users (id),
    updated_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                   NOT NULL DEFAULT 0,
    is_active    boolean                  NOT NULL DEFAULT true,
    is_deleted   boolean                  NOT NULL DEFAULT false,
    deleted_by   bigint REFERENCES users (id),
    deleted_at   timestamp with time zone,

    UNIQUE (item_type_id, code)
);

CREATE TABLE IF NOT EXISTS item_category_locales
(
    id               bigserial PRIMARY KEY,

    item_category_id bigint                   NOT NULL REFERENCES item_categories (id) ON DELETE CASCADE,
    locale_id        bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name             varchar(255)             NOT NULL,
    description      text                     NOT NULL DEFAULT '',
    sort_order       int                      NOT NULL DEFAULT 0,

    created_by       bigint                   NOT NULL REFERENCES users (id),
    created_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                   NOT NULL REFERENCES users (id),
    updated_at       timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                   NOT NULL DEFAULT 0,
    is_active        boolean                  NOT NULL DEFAULT true,
    is_deleted       boolean                  NOT NULL DEFAULT false,
    deleted_by       bigint REFERENCES users (id),
    deleted_at       timestamp with time zone,

    UNIQUE (item_category_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN

        SELECT id
        INTO sys_id
        FROM users
        WHERE username = 'system';

        -- =====================================================
        -- ITEM CATEGORIES SEED
        -- =====================================================

        INSERT INTO item_categories
        (item_type_id,
         code,
         sort_order,
         created_by,
         updated_by)
        SELECT it.id,
               v.code,
               v.sort_order,
               sys_id,
               sys_id
        FROM item_types it
                 JOIN (VALUES

                           -- =========================
                           -- INGREDIENT
                           -- =========================
                           ('INGREDIENT', 'MEAT', 1),
                           ('INGREDIENT', 'FISH', 2),
                           ('INGREDIENT', 'VEGETABLE', 3),
                           ('INGREDIENT', 'FRUIT', 4),
                           ('INGREDIENT', 'RICE_GRAIN', 5),
                           ('INGREDIENT', 'DAIRY', 6),
                           ('INGREDIENT', 'SPICE', 7),
                           ('INGREDIENT', 'OIL_FAT', 8),
                           ('INGREDIENT', 'SAUCE', 9),
                           ('INGREDIENT', 'FLOUR', 10),

                           -- =========================
                           -- PACKAGING
                           -- =========================
                           ('PACKAGING', 'CONTAINER', 11),
                           ('PACKAGING', 'BOX', 12),
                           ('PACKAGING', 'BOTTLE', 13),
                           ('PACKAGING', 'WRAPPER', 14),
                           ('PACKAGING', 'BAG', 15),
                           ('PACKAGING', 'CUP', 16),

                           -- =========================
                           -- ASSET
                           -- =========================
                           ('ASSET', 'FURNITURE', 17),
                           ('ASSET', 'VEHICLE', 18),
                           ('ASSET', 'PROPERTY', 19),
                           ('ASSET', 'IT_EQUIPMENT', 20),
                           ('ASSET', 'APPLIANCE', 21),

                           -- =========================
                           -- EQUIPMENT
                           -- =========================
                           ('EQUIPMENT', 'KITCHEN_TOOL', 22),
                           ('EQUIPMENT', 'COOKING_MACHINE', 23),
                           ('EQUIPMENT', 'REFRIGERATOR', 24),
                           ('EQUIPMENT', 'CLEANING_TOOL', 25),
                           ('EQUIPMENT', 'CUTLERY', 26)) v(type_code, code, sort_order)
                      ON it.code = v.type_code
        WHERE NOT EXISTS (SELECT 1
                          FROM item_categories ic2
                          WHERE ic2.item_type_id = it.id
                            AND ic2.code = v.code);


        -- =====================================================
        -- ITEM CATEGORY LOCALIZATION SEED
        -- =====================================================

        INSERT INTO item_category_locales
        (item_category_id,
         locale_id,
         name,
         description,
         sort_order,
         created_by,
         updated_by)
        SELECT ic.id,
               l.id,
               v.name,
               v.description,
               ic.sort_order,
               sys_id,
               sys_id
        FROM item_categories ic
                 JOIN item_types it
                      ON it.id = ic.item_type_id
                 JOIN (VALUES

                           -- =========================
                           -- INGREDIENT
                           -- =========================
                           ('INGREDIENT', 'MEAT', 'en', 'Meat', 'Animal meat ingredients'),
                           ('INGREDIENT', 'MEAT', 'bn', 'মাংস', 'প্রাণিজ মাংস'),

                           ('INGREDIENT', 'FISH', 'en', 'Fish', 'Sea and freshwater fish'),
                           ('INGREDIENT', 'FISH', 'bn', 'মাছ', 'সামুদ্রিক ও নদীর মাছ'),

                           ('INGREDIENT', 'VEGETABLE', 'en', 'Vegetable', 'Fresh vegetables'),
                           ('INGREDIENT', 'VEGETABLE', 'bn', 'সবজি', 'তাজা সবজি'),

                           ('INGREDIENT', 'FRUIT', 'en', 'Fruit', 'Fresh fruits'),
                           ('INGREDIENT', 'FRUIT', 'bn', 'ফল', 'তাজা ফল'),

                           ('INGREDIENT', 'RICE_GRAIN', 'en', 'Rice & Grains', 'Rice and grain products'),
                           ('INGREDIENT', 'RICE_GRAIN', 'bn', 'চাল ও শস্য', 'চাল ও শস্যজাতীয় পণ্য'),

                           ('INGREDIENT', 'DAIRY', 'en', 'Dairy', 'Milk and dairy products'),
                           ('INGREDIENT', 'DAIRY', 'bn', 'দুগ্ধজাত', 'দুধ ও দুগ্ধজাত পণ্য'),

                           ('INGREDIENT', 'SPICE', 'en', 'Spices', 'Cooking spices'),
                           ('INGREDIENT', 'SPICE', 'bn', 'মসলা', 'রান্নার মসলা'),

                           ('INGREDIENT', 'OIL_FAT', 'en', 'Oil & Fat', 'Cooking oil and fat'),
                           ('INGREDIENT', 'OIL_FAT', 'bn', 'তেল ও চর্বি', 'রান্নার তেল ও চর্বি'),

                           ('INGREDIENT', 'SAUCE', 'en', 'Sauces', 'Food sauces'),
                           ('INGREDIENT', 'SAUCE', 'bn', 'সস', 'খাবারের সস'),

                           ('INGREDIENT', 'FLOUR', 'en', 'Flour', 'Wheat and other flour'),
                           ('INGREDIENT', 'FLOUR', 'bn', 'আটা/ময়দা', 'গমের আটা ও ময়দা'),

                           -- =========================
                           -- PACKAGING
                           -- =========================
                           ('PACKAGING', 'CONTAINER', 'en', 'Container', 'Food containers'),
                           ('PACKAGING', 'CONTAINER', 'bn', 'কনটেইনার', 'খাবারের পাত্র'),

                           ('PACKAGING', 'BOX', 'en', 'Box', 'Packaging boxes'),
                           ('PACKAGING', 'BOX', 'bn', 'বক্স', 'প্যাকেজিং বক্স'),

                           ('PACKAGING', 'BOTTLE', 'en', 'Bottle', 'Plastic or glass bottles'),
                           ('PACKAGING', 'BOTTLE', 'bn', 'বোতল', 'প্লাস্টিক/কাচের বোতল'),

                           ('PACKAGING', 'WRAPPER', 'en', 'Wrapper', 'Food wrapping materials'),
                           ('PACKAGING', 'WRAPPER', 'bn', 'র‍্যাপার', 'মোড়কজাত উপকরণ'),

                           ('PACKAGING', 'BAG', 'en', 'Bag', 'Packaging bags'),
                           ('PACKAGING', 'BAG', 'bn', 'ব্যাগ', 'প্যাকেজিং ব্যাগ'),

                           ('PACKAGING', 'CUP', 'en', 'Cup', 'Disposable cups'),
                           ('PACKAGING', 'CUP', 'bn', 'কাপ', 'একবার ব্যবহারযোগ্য কাপ'),

                           -- =========================
                           -- ASSET
                           -- =========================
                           ('ASSET', 'FURNITURE', 'en', 'Furniture', 'Tables, chairs etc.'),
                           ('ASSET', 'FURNITURE', 'bn', 'আসবাবপত্র', 'টেবিল, চেয়ার ইত্যাদি'),

                           ('ASSET', 'VEHICLE', 'en', 'Vehicle', 'Transport vehicles'),
                           ('ASSET', 'VEHICLE', 'bn', 'যানবাহন', 'পরিবহন যান'),

                           ('ASSET', 'PROPERTY', 'en', 'Property', 'Physical properties'),
                           ('ASSET', 'PROPERTY', 'bn', 'সম্পত্তি', 'স্থাবর সম্পদ'),

                           ('ASSET', 'IT_EQUIPMENT', 'en', 'IT Equipment', 'Computers and IT devices'),
                           ('ASSET', 'IT_EQUIPMENT', 'bn', 'আইটি সরঞ্জাম', 'কম্পিউটার ও আইটি ডিভাইস'),

                           ('ASSET', 'APPLIANCE', 'en', 'Appliance', 'Electrical appliances'),
                           ('ASSET', 'APPLIANCE', 'bn', 'ইলেকট্রিক যন্ত্র', 'বৈদ্যুতিক যন্ত্র'),

                           -- =========================
                           -- EQUIPMENT
                           -- =========================
                           ('EQUIPMENT', 'KITCHEN_TOOL', 'en', 'Kitchen Tool', 'Cooking tools'),
                           ('EQUIPMENT', 'KITCHEN_TOOL', 'bn', 'রান্নার সরঞ্জাম', 'রান্নার টুলস'),

                           ('EQUIPMENT', 'COOKING_MACHINE', 'en', 'Cooking Machine', 'Industrial cooking machines'),
                           ('EQUIPMENT', 'COOKING_MACHINE', 'bn', 'রান্নার মেশিন', 'শিল্প রান্নার যন্ত্র'),

                           ('EQUIPMENT', 'REFRIGERATOR', 'en', 'Refrigerator', 'Cooling storage'),
                           ('EQUIPMENT', 'REFRIGERATOR', 'bn', 'ফ্রিজ', 'ঠান্ডা সংরক্ষণ যন্ত্র'),

                           ('EQUIPMENT', 'CLEANING_TOOL', 'en', 'Cleaning Tool', 'Cleaning equipment'),
                           ('EQUIPMENT', 'CLEANING_TOOL', 'bn', 'পরিষ্কার সরঞ্জাম', 'পরিষ্কারের সরঞ্জাম'),

                           ('EQUIPMENT', 'CUTLERY', 'en', 'Cutlery', 'Forks, knives, spoons'),
                           ('EQUIPMENT', 'CUTLERY', 'bn', 'কাটলারি',
                            'চামচ, ছুরি ইত্যাদি')) v(item_type_code, code, locale_code, name, description)
                      ON it.code = v.item_type_code
                          AND ic.code = v.code
                 JOIN locales l
                      ON l.code = v.locale_code
        WHERE NOT EXISTS (SELECT 1
                          FROM item_category_locales icl2
                          WHERE icl2.item_category_id = ic.id
                            AND icl2.locale_id = l.id);

    END
$$;