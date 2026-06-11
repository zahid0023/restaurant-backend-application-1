CREATE TABLE IF NOT EXISTS dishes
(
    id         bigserial PRIMARY KEY,

    code       varchar(50)              NOT NULL UNIQUE,
    sort_order int                      NOT NULL DEFAULT 0,

    created_by bigint                   NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint                   NOT NULL,
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                            DEFAULT 0 NOT NULL,
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean                  NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS dishes_locales
(
    id          bigserial PRIMARY KEY,

    dish_id     bigint                   NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,
    locale_id   bigint                   NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)             NOT NULL,
    description text,
    sort_order  int                      NOT NULL DEFAULT 0,

    created_by  bigint                   NOT NULL,
    created_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint                   NOT NULL,
    updated_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     bigint                            DEFAULT 0 NOT NULL,
    is_active   boolean                  NOT NULL DEFAULT true,
    is_deleted  boolean                  NOT NULL DEFAULT false,
    deleted_by  bigint,
    deleted_at  timestamp with time zone,

    UNIQUE (dish_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO dishes (code, sort_order, created_by, updated_by)
        VALUES
            -- Breakfast — Hot Drinks
            ('TEA', 1, sys_id, sys_id),
            ('COFFEE', 2, sys_id, sys_id),
            ('GREEN_TEA', 3, sys_id, sys_id),
            -- Breakfast — Cold Drinks
            ('ORANGE_JUICE', 4, sys_id, sys_id),
            ('LEMONADE', 5, sys_id, sys_id),
            -- Breakfast — Main Dishes
            ('FRIED_EGG', 6, sys_id, sys_id),
            ('OMELETTE', 7, sys_id, sys_id),
            ('FRENCH_TOAST', 8, sys_id, sys_id),
            -- Breakfast — Pastries
            ('CROISSANT', 9, sys_id, sys_id),
            ('MUFFIN', 10, sys_id, sys_id),
            -- Lunch — Starters
            ('SPRING_ROLLS', 11, sys_id, sys_id),
            ('BRUSCHETTA', 12, sys_id, sys_id),
            -- Lunch — Soups
            ('TOMATO_SOUP', 13, sys_id, sys_id),
            ('CHICKEN_SOUP', 14, sys_id, sys_id),
            -- Lunch — Main Course
            ('CHICKEN_RICE', 15, sys_id, sys_id),
            ('BEEF_STEAK', 16, sys_id, sys_id),
            ('PASTA', 17, sys_id, sys_id),
            -- Lunch — Desserts
            ('CHOCOLATE_CAKE', 18, sys_id, sys_id),
            ('ICE_CREAM', 19, sys_id, sys_id),
            -- Lunch — Beverages
            ('SOFT_DRINK', 20, sys_id, sys_id),
            ('MINERAL_WATER', 21, sys_id, sys_id);

        INSERT INTO dishes_locales (dish_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT d.id,
               l.id,
               v.name,
               v.description,
               d.sort_order,
               sys_id,
               sys_id
        FROM dishes d
                 JOIN (VALUES
                           -- =========================
                           -- TEA
                           -- =========================
                           ('TEA', 'en', 'Tea', 'Hot brewed tea served with milk and sugar'),
                           ('TEA', 'bn', 'চা', 'দুধ ও চিনি সহ গরম চা'),

                           -- =========================
                           -- COFFEE
                           -- =========================
                           ('COFFEE', 'en', 'Coffee', 'Rich and aromatic freshly brewed coffee'),
                           ('COFFEE', 'bn', 'কফি', 'তাজা তৈরি সুগন্ধি কফি'),

                           -- =========================
                           -- GREEN_TEA
                           -- =========================
                           ('GREEN_TEA', 'en', 'Green Tea', 'Light and refreshing green tea'),
                           ('GREEN_TEA', 'bn', 'গ্রিন টি', 'হালকা ও প্রাণতাজা গ্রিন টি'),

                           -- =========================
                           -- ORANGE_JUICE
                           -- =========================
                           ('ORANGE_JUICE', 'en', 'Orange Juice', 'Freshly squeezed orange juice'),
                           ('ORANGE_JUICE', 'bn', 'কমলার জুস', 'তাজা চিপা কমলার রস'),

                           -- =========================
                           -- LEMONADE
                           -- =========================
                           ('LEMONADE', 'en', 'Lemonade', 'Chilled lemonade with mint and ice'),
                           ('LEMONADE', 'bn', 'লেমনেড', 'পুদিনা ও বরফ সহ ঠান্ডা লেবুর শরবত'),

                           -- =========================
                           -- FRIED_EGG
                           -- =========================
                           ('FRIED_EGG', 'en', 'Fried Egg', 'Classic sunny-side-up fried egg'),
                           ('FRIED_EGG', 'bn', 'ভাজা ডিম', 'ক্লাসিক সানি-সাইড-আপ ভাজা ডিম'),

                           -- =========================
                           -- OMELETTE
                           -- =========================
                           ('OMELETTE', 'en', 'Omelette', 'Fluffy egg omelette with vegetables'),
                           ('OMELETTE', 'bn', 'অমলেট', 'সবজি দিয়ে তৈরি নরম ডিমের অমলেট'),

                           -- =========================
                           -- FRENCH_TOAST
                           -- =========================
                           ('FRENCH_TOAST', 'en', 'French Toast', 'Golden pan-toasted bread with egg batter'),
                           ('FRENCH_TOAST', 'bn', 'ফ্রেঞ্চ টোস্ট', 'ডিমের প্রলেপে প্যানে ভাজা রুটি'),

                           -- =========================
                           -- CROISSANT
                           -- =========================
                           ('CROISSANT', 'en', 'Croissant', 'Buttery flaky baked croissant'),
                           ('CROISSANT', 'bn', 'ক্রোয়াসাঁ', 'মাখনসমৃদ্ধ পরতে পরতে বেক করা ক্রোয়াসাঁ'),

                           -- =========================
                           -- MUFFIN
                           -- =========================
                           ('MUFFIN', 'en', 'Muffin', 'Soft baked muffin with blueberries'),
                           ('MUFFIN', 'bn', 'মাফিন', 'ব্লুবেরি দিয়ে তৈরি নরম মাফিন'),

                           -- =========================
                           -- SPRING_ROLLS
                           -- =========================
                           ('SPRING_ROLLS', 'en', 'Spring Rolls', 'Crispy vegetable spring rolls'),
                           ('SPRING_ROLLS', 'bn', 'স্প্রিং রোল', 'মুচমুচে সবজি স্প্রিং রোল'),

                           -- =========================
                           -- BRUSCHETTA
                           -- =========================
                           ('BRUSCHETTA', 'en', 'Bruschetta', 'Toasted bread topped with tomato and herbs'),
                           ('BRUSCHETTA', 'bn', 'ব্রুসকেটা', 'টমেটো ও ভেষজ উপকরণ দিয়ে তৈরি টোস্টড ব্রেড'),

                           -- =========================
                           -- TOMATO_SOUP
                           -- =========================
                           ('TOMATO_SOUP', 'en', 'Tomato Soup', 'Creamy roasted tomato soup'),
                           ('TOMATO_SOUP', 'bn', 'টমেটো স্যুপ', 'ক্রিমি রোস্টেড টমেটো স্যুপ'),

                           -- =========================
                           -- CHICKEN_SOUP
                           -- =========================
                           ('CHICKEN_SOUP', 'en', 'Chicken Soup', 'Warm chicken broth with vegetables'),
                           ('CHICKEN_SOUP', 'bn', 'চিকেন স্যুপ', 'সবজি সহ গরম মুরগির ঝোল'),

                           -- =========================
                           -- CHICKEN_RICE
                           -- =========================
                           ('CHICKEN_RICE', 'en', 'Chicken Rice', 'Steamed rice served with grilled chicken'),
                           ('CHICKEN_RICE', 'bn', 'চিকেন রাইস', 'গ্রিলড চিকেন সহ ভাপানো ভাত'),

                           -- =========================
                           -- BEEF_STEAK
                           -- =========================
                           ('BEEF_STEAK', 'en', 'Beef Steak', 'Grilled beef steak with sauce and vegetables'),
                           ('BEEF_STEAK', 'bn', 'বিফ স্টেক', 'সসের সাথে গ্রিলড বিফ স্টেক ও সবজি'),

                           -- =========================
                           -- PASTA
                           -- =========================
                           ('PASTA', 'en', 'Pasta', 'Italian pasta in tomato or white cream sauce'),
                           ('PASTA', 'bn', 'পাস্তা', 'টমেটো বা সাদা ক্রিম সসে রান্না করা ইতালিয়ান পাস্তা'),

                           -- =========================
                           -- CHOCOLATE_CAKE
                           -- =========================
                           ('CHOCOLATE_CAKE', 'en', 'Chocolate Cake', 'Rich layered chocolate cake'),
                           ('CHOCOLATE_CAKE', 'bn', 'চকোলেট কেক', 'সমৃদ্ধ স্তরে স্তরে চকোলেট কেক'),

                           -- =========================
                           -- ICE_CREAM
                           -- =========================
                           ('ICE_CREAM', 'en', 'Ice Cream', 'Creamy ice cream in assorted flavours'),
                           ('ICE_CREAM', 'bn', 'আইসক্রিম', 'বিভিন্ন স্বাদে ক্রিমি আইসক্রিম'),

                           -- =========================
                           -- SOFT_DRINK
                           -- =========================
                           ('SOFT_DRINK', 'en', 'Soft Drink', 'Chilled carbonated soft drink'),
                           ('SOFT_DRINK', 'bn', 'সফট ড্রিংক', 'ঠান্ডা কার্বোনেটেড সফট ড্রিংক'),

                           -- =========================
                           -- MINERAL_WATER
                           -- =========================
                           ('MINERAL_WATER', 'en', 'Mineral Water', 'Still or sparkling mineral water'),
                           ('MINERAL_WATER', 'bn', 'মিনারেল ওয়াটার',
                            'স্থির বা ঝলমলে মিনারেল ওয়াটার')) v(dish_code, locale_code, name, description)
                      ON d.code = v.dish_code
                 JOIN locales l ON l.code = v.locale_code;
    END
$$;

