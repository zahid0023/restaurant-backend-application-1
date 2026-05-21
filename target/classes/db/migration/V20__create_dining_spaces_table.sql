CREATE TABLE IF NOT EXISTS dining_spaces
(
    id                   bigserial PRIMARY KEY,

    dining_space_type_id bigint      NOT NULL REFERENCES dining_space_types (id),
    floor_id             bigint REFERENCES floors (id),

    code                 varchar(50) NOT NULL UNIQUE,
    sort_order           int         NOT NULL DEFAULT 0,
    capacity             int         NOT NULL,
    is_bookable          boolean     NOT NULL DEFAULT true,
    created_by           bigint      NOT NULL,
    created_at           timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by           bigint      NOT NULL,
    updated_at           timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version              bigint      NOT NULL DEFAULT 0,
    is_active            boolean     NOT NULL DEFAULT true,
    is_deleted           boolean     NOT NULL DEFAULT false,
    deleted_by           bigint,
    deleted_at           timestamptz
);

CREATE TABLE IF NOT EXISTS dining_space_locales
(
    id              bigserial PRIMARY KEY,

    dining_space_id bigint       NOT NULL REFERENCES dining_spaces (id) ON DELETE CASCADE,
    locale_id       bigint       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name            varchar(255) NOT NULL,
    description     text,
    sort_order      int          NOT NULL DEFAULT 0,

    created_by      bigint       NOT NULL,
    created_at      timestamptz  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by      bigint       NOT NULL,
    updated_at      timestamptz  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version         bigint       NOT NULL DEFAULT 0,
    is_active       boolean      NOT NULL DEFAULT true,
    is_deleted      boolean      NOT NULL DEFAULT false,
    deleted_by      bigint,
    deleted_at      timestamptz
);

INSERT INTO dining_spaces (code, dining_space_type_id, floor_id, capacity, sort_order, is_bookable, created_by, updated_by)
VALUES
    ('MAIN_HALL',
     (SELECT id FROM dining_space_types WHERE code = 'INDOOR'),
     (SELECT id FROM floors WHERE code = 'GF'),
     60, 1, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('GF_OUTDOOR',
     (SELECT id FROM dining_space_types WHERE code = 'OUTDOOR'),
     (SELECT id FROM floors WHERE code = 'GF'),
     30, 2, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('FF_DINING',
     (SELECT id FROM dining_space_types WHERE code = 'INDOOR'),
     (SELECT id FROM floors WHERE code = 'FF'),
     40, 3, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('PRIVATE_ROOM_1',
     (SELECT id FROM dining_space_types WHERE code = 'PRIVATE'),
     (SELECT id FROM floors WHERE code = 'FF'),
     15, 4, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('PRIVATE_ROOM_2',
     (SELECT id FROM dining_space_types WHERE code = 'PRIVATE'),
     (SELECT id FROM floors WHERE code = 'SF'),
     20, 5, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('ROOFTOP_TERRACE',
     (SELECT id FROM dining_space_types WHERE code = 'TERRACE'),
     (SELECT id FROM floors WHERE code = 'RT'),
     50, 6, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('GARDEN_AREA',
     (SELECT id FROM dining_space_types WHERE code = 'GARDEN'),
     (SELECT id FROM floors WHERE code = 'GF'),
     35, 7, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('BAR_LOUNGE',
     (SELECT id FROM dining_space_types WHERE code = 'BAR'),
     (SELECT id FROM floors WHERE code = 'BSM'),
     25, 8, false,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ('VIP_SUITE',
     (SELECT id FROM dining_space_types WHERE code = 'VIP'),
     (SELECT id FROM floors WHERE code = 'VIP'),
     12, 9, true,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

INSERT INTO dining_space_locales (dining_space_id, locale_id, name, description, sort_order, created_by, updated_by)
VALUES
    -- Main Hall
    ((SELECT id FROM dining_spaces WHERE code = 'MAIN_HALL'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Main Hall', 'Primary indoor dining area on the ground floor with full table service', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'MAIN_HALL'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'মেইন হল', 'গ্রাউন্ড ফ্লোরে সম্পূর্ণ টেবিল সার্ভিস সহ প্রধান ইনডোর ডাইনিং এলাকা', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Ground Floor Outdoor
    ((SELECT id FROM dining_spaces WHERE code = 'GF_OUTDOOR'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Ground Floor Outdoor', 'Open-air seating on the ground floor front area', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'GF_OUTDOOR'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'গ্রাউন্ড ফ্লোর আউটডোর', 'গ্রাউন্ড ফ্লোরের সামনের এলাকায় খোলা আকাশের নিচে বসার ব্যবস্থা', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- First Floor Dining
    ((SELECT id FROM dining_spaces WHERE code = 'FF_DINING'),
     (SELECT id FROM locales WHERE code = 'en'),
     'First Floor Dining', 'Spacious indoor dining hall on the first floor', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'FF_DINING'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'প্রথম তলার ডাইনিং', 'প্রথম তলায় প্রশস্ত ইনডোর ডাইনিং হল', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Private Room 1
    ((SELECT id FROM dining_spaces WHERE code = 'PRIVATE_ROOM_1'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Private Room 1', 'Intimate private dining room on the first floor, ideal for small gatherings', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'PRIVATE_ROOM_1'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'প্রাইভেট রুম ১', 'প্রথম তলায় ছোট অনুষ্ঠানের জন্য আদর্শ আন্তরিক প্রাইভেট ডাইনিং রুম', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Private Room 2
    ((SELECT id FROM dining_spaces WHERE code = 'PRIVATE_ROOM_2'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Private Room 2', 'Private dining room on the second floor suitable for corporate events', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'PRIVATE_ROOM_2'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'প্রাইভেট রুম ২', 'দ্বিতীয় তলায় কর্পোরেট ইভেন্টের জন্য উপযুক্ত প্রাইভেট ডাইনিং রুম', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Rooftop Terrace
    ((SELECT id FROM dining_spaces WHERE code = 'ROOFTOP_TERRACE'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Rooftop Terrace', 'Scenic open-air terrace dining with panoramic city views', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'ROOFTOP_TERRACE'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'রুফটপ টেরেস', 'শহরের প্যানোরামিক দৃশ্য সহ মনোরম খোলা ছাদের টেরেস ডাইনিং', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Garden Area
    ((SELECT id FROM dining_spaces WHERE code = 'GARDEN_AREA'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Garden Area', 'Lush outdoor garden dining surrounded by greenery and natural lighting', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'GARDEN_AREA'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'গার্ডেন এলাকা', 'সবুজ গাছপালা ও প্রাকৃতিক আলোয় ঘেরা সুন্দর আউটডোর গার্ডেন ডাইনিং', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Bar Lounge
    ((SELECT id FROM dining_spaces WHERE code = 'BAR_LOUNGE'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Bar & Lounge', 'Basement bar and lounge area with casual seating and drinks service', 8,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'BAR_LOUNGE'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'বার ও লাউঞ্জ', 'বেসমেন্টে ক্যাজুয়াল বসার ব্যবস্থা ও পানীয় সার্ভিস সহ বার এবং লাউঞ্জ এলাকা', 8,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- VIP Suite
    ((SELECT id FROM dining_spaces WHERE code = 'VIP_SUITE'),
     (SELECT id FROM locales WHERE code = 'en'),
     'VIP Suite', 'Exclusive VIP dining suite with premium furnishings and dedicated butler service', 9,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
    ((SELECT id FROM dining_spaces WHERE code = 'VIP_SUITE'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'ভিআইপি স্যুট', 'প্রিমিয়াম আসবাবপত্র ও নিবেদিত বাটলার সার্ভিস সহ একচেটিয়া ভিআইপি ডাইনিং স্যুট', 9,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));
