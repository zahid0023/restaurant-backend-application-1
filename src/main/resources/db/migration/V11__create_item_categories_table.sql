CREATE TABLE IF NOT EXISTS item_categories
(
    id         bigserial PRIMARY KEY,

    parent_id  bigint REFERENCES item_categories (id),

    code       varchar(50)              NOT NULL,
    sort_order int                      NOT NULL DEFAULT 0,

    created_by bigint                   NOT NULL REFERENCES users (id),
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint                   NOT NULL REFERENCES users (id),
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                   NOT NULL DEFAULT 0,
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean                  NOT NULL DEFAULT false,
    deleted_by bigint REFERENCES users (id),
    deleted_at timestamp with time zone,

    UNIQUE (code)
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
        (code,
         sort_order,
         created_by,
         updated_by)
        SELECT v.code,
               v.sort_order,
               sys_id,
               sys_id
        FROM (VALUES

                  -- =========================
                  -- INGREDIENT
                  -- =========================
                  ('MEAT', 1),
                  ('FISH', 2),
                  ('VEGETABLE', 3),
                  ('FRUIT', 4),
                  ('RICE_GRAIN', 5),
                  ('DAIRY', 6),
                  ('SPICE', 7),
                  ('OIL_FAT', 8),
                  ('SAUCE', 9),
                  ('FLOUR', 10),

                  -- =========================
                  -- PACKAGING
                  -- =========================
                  ('CONTAINER', 11),
                  ('BOX', 12),
                  ('BOTTLE', 13),
                  ('WRAPPER', 14),
                  ('BAG', 15),
                  ('CUP', 16),

                  -- =========================
                  -- ASSET
                  -- =========================
                  ('FURNITURE', 17),
                  ('VEHICLE', 18),
                  ('PROPERTY', 19),
                  ('IT_EQUIPMENT', 20),
                  ('APPLIANCE', 21),

                  -- =========================
                  -- EQUIPMENT
                  -- =========================
                  ('KITCHEN_TOOL', 22),
                  ('COOKING_MACHINE', 23),
                  ('REFRIGERATOR', 24),
                  ('CLEANING_TOOL', 25),
                  ('CUTLERY', 26)) v(code, sort_order)
        WHERE NOT EXISTS (SELECT 1
                          FROM item_categories ic2
                          WHERE ic2.code = v.code);


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
                 JOIN (VALUES

                           -- =========================
                           -- INGREDIENT
                           -- =========================
                           ('MEAT', 'en', 'Meat', 'Animal meat ingredients'),
                           ('MEAT', 'bn', 'মাংস', 'প্রাণিজ মাংস'),

                           ('FISH', 'en', 'Fish', 'Sea and freshwater fish'),
                           ('FISH', 'bn', 'মাছ', 'সামুদ্রিক ও নদীর মাছ'),

                           ('VEGETABLE', 'en', 'Vegetable', 'Fresh vegetables'),
                           ('VEGETABLE', 'bn', 'সবজি', 'তাজা সবজি'),

                           ('FRUIT', 'en', 'Fruit', 'Fresh fruits'),
                           ('FRUIT', 'bn', 'ফল', 'তাজা ফল'),

                           ('RICE_GRAIN', 'en', 'Rice & Grains', 'Rice and grain products'),
                           ('RICE_GRAIN', 'bn', 'চাল ও শস্য', 'চাল ও শস্যজাতীয় পণ্য'),

                           ('DAIRY', 'en', 'Dairy', 'Milk and dairy products'),
                           ('DAIRY', 'bn', 'দুগ্ধজাত', 'দুধ ও দুগ্ধজাত পণ্য'),

                           ('SPICE', 'en', 'Spices', 'Cooking spices'),
                           ('SPICE', 'bn', 'মসলা', 'রান্নার মসলা'),

                           ('OIL_FAT', 'en', 'Oil & Fat', 'Cooking oil and fat'),
                           ('OIL_FAT', 'bn', 'তেল ও চর্বি', 'রান্নার তেল ও চর্বি'),

                           ('SAUCE', 'en', 'Sauces', 'Food sauces'),
                           ('SAUCE', 'bn', 'সস', 'খাবারের সস'),

                           ('FLOUR', 'en', 'Flour', 'Wheat and other flour'),
                           ('FLOUR', 'bn', 'আটা/ময়দা', 'গমের আটা ও ময়দা'),

                           -- =========================
                           -- PACKAGING
                           -- =========================
                           ('CONTAINER', 'en', 'Container', 'Food containers'),
                           ('CONTAINER', 'bn', 'কনটেইনার', 'খাবারের পাত্র'),

                           ('BOX', 'en', 'Box', 'Packaging boxes'),
                           ('BOX', 'bn', 'বক্স', 'প্যাকেজিং বক্স'),

                           ('BOTTLE', 'en', 'Bottle', 'Plastic or glass bottles'),
                           ('BOTTLE', 'bn', 'বোতল', 'প্লাস্টিক/কাচের বোতল'),

                           ('WRAPPER', 'en', 'Wrapper', 'Food wrapping materials'),
                           ('WRAPPER', 'bn', 'র‍্যাপার', 'মোড়কজাত উপকরণ'),

                           ('BAG', 'en', 'Bag', 'Packaging bags'),
                           ('BAG', 'bn', 'ব্যাগ', 'প্যাকেজিং ব্যাগ'),

                           ('CUP', 'en', 'Cup', 'Disposable cups'),
                           ('CUP', 'bn', 'কাপ', 'একবার ব্যবহারযোগ্য কাপ'),

                           -- =========================
                           -- ASSET
                           -- =========================
                           ('FURNITURE', 'en', 'Furniture', 'Tables, chairs etc.'),
                           ('FURNITURE', 'bn', 'আসবাবপত্র', 'টেবিল, চেয়ার ইত্যাদি'),

                           ('VEHICLE', 'en', 'Vehicle', 'Transport vehicles'),
                           ('VEHICLE', 'bn', 'যানবাহন', 'পরিবহন যান'),

                           ('PROPERTY', 'en', 'Property', 'Physical properties'),
                           ('PROPERTY', 'bn', 'সম্পত্তি', 'স্থাবর সম্পদ'),

                           ('IT_EQUIPMENT', 'en', 'IT Equipment', 'Computers and IT devices'),
                           ('IT_EQUIPMENT', 'bn', 'আইটি সরঞ্জাম', 'কম্পিউটার ও আইটি ডিভাইস'),

                           ('APPLIANCE', 'en', 'Appliance', 'Electrical appliances'),
                           ('APPLIANCE', 'bn', 'ইলেকট্রিক যন্ত্র', 'বৈদ্যুতিক যন্ত্র'),

                           -- =========================
                           -- EQUIPMENT
                           -- =========================
                           ('KITCHEN_TOOL', 'en', 'Kitchen Tool', 'Cooking tools'),
                           ('KITCHEN_TOOL', 'bn', 'রান্নার সরঞ্জাম', 'রান্নার টুলস'),

                           ('COOKING_MACHINE', 'en', 'Cooking Machine', 'Industrial cooking machines'),
                           ('COOKING_MACHINE', 'bn', 'রান্নার মেশিন', 'শিল্প রান্নার যন্ত্র'),

                           ('REFRIGERATOR', 'en', 'Refrigerator', 'Cooling storage'),
                           ('REFRIGERATOR', 'bn', 'ফ্রিজ', 'ঠান্ডা সংরক্ষণ যন্ত্র'),

                           ('CLEANING_TOOL', 'en', 'Cleaning Tool', 'Cleaning equipment'),
                           ('CLEANING_TOOL', 'bn', 'পরিষ্কার সরঞ্জাম', 'পরিষ্কারের সরঞ্জাম'),

                           ('CUTLERY', 'en', 'Cutlery', 'Forks, knives, spoons'),
                           ('CUTLERY', 'bn', 'কাটলারি',
                            'চামচ, ছুরি ইত্যাদি')) v(code, locale_code, name, description)
                      ON ic.code = v.code
                 JOIN locales l
                      ON l.code = v.locale_code
        WHERE NOT EXISTS (SELECT 1
                          FROM item_category_locales icl2
                          WHERE icl2.item_category_id = ic.id
                            AND icl2.locale_id = l.id);

    END
$$;