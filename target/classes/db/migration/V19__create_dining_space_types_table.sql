CREATE TABLE IF NOT EXISTS dining_space_types
(
    id         bigserial PRIMARY KEY,

    code       varchar(50)                  NOT NULL,
    sort_order int                          NOT NULL DEFAULT 0,

    created_by bigint references users (id) NOT NULL,
    created_at timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint references users (id) NOT NULL,
    updated_at timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                       NOT NULL DEFAULT 0,
    is_active  boolean                      NOT NULL DEFAULT true,
    is_deleted boolean                      NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamptz
);

CREATE TABLE IF NOT EXISTS dining_space_type_locales
(
    id                   bigserial PRIMARY KEY,

    dining_space_type_id bigint                       NOT NULL REFERENCES dining_space_types (id) ON DELETE CASCADE,
    locale_id            bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name                 varchar(255)                 NOT NULL,
    description          text,
    sort_order           int                          NOT NULL DEFAULT 0,

    created_by           bigint references users (id) NOT NULL,
    created_at           timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by           bigint references users (id) NOT NULL,
    updated_at           timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version              bigint                       NOT NULL DEFAULT 0,
    is_active            boolean                      NOT NULL DEFAULT true,
    is_deleted           boolean                      NOT NULL DEFAULT false,
    deleted_by           bigint,
    deleted_at           timestamptz
);

INSERT INTO dining_space_types (code, sort_order, created_by, updated_by)
VALUES ('INDOOR',   1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('OUTDOOR',  2, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('TERRACE',  3, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('PRIVATE',  4, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('BAR',      5, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('GARDEN',   6, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('VIP',      7, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

INSERT INTO dining_space_type_locales (dining_space_type_id, locale_id, name, description, sort_order, created_by, updated_by)
VALUES
    -- Indoor
    ((SELECT id FROM dining_space_types WHERE code = 'INDOOR'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Indoor', 'Enclosed dining area inside the restaurant', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'INDOOR'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'ইনডোর', 'রেস্তোরাঁর ভেতরে আবদ্ধ খাবার এলাকা', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Outdoor
    ((SELECT id FROM dining_space_types WHERE code = 'OUTDOOR'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Outdoor', 'Open-air dining area outside the restaurant', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'OUTDOOR'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'আউটডোর', 'রেস্তোরাঁর বাইরে খোলা খাবার এলাকা', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Terrace
    ((SELECT id FROM dining_space_types WHERE code = 'TERRACE'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Terrace', 'Elevated open-air dining on a terrace or balcony', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'TERRACE'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'টেরেস', 'ছাদ বা বারান্দায় উন্মুক্ত খাবার এলাকা', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Private
    ((SELECT id FROM dining_space_types WHERE code = 'PRIVATE'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Private Room', 'Enclosed private dining room for events and gatherings', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'PRIVATE'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'প্রাইভেট রুম', 'অনুষ্ঠান ও সমাবেশের জন্য আবদ্ধ প্রাইভেট ডাইনিং রুম', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Bar
    ((SELECT id FROM dining_space_types WHERE code = 'BAR'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Bar & Lounge', 'Bar and lounge seating area', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'BAR'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'বার ও লাউঞ্জ', 'বার এবং লাউঞ্জ বসার এলাকা', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Garden
    ((SELECT id FROM dining_space_types WHERE code = 'GARDEN'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Garden', 'Outdoor garden dining surrounded by greenery', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'GARDEN'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'গার্ডেন', 'সবুজে ঘেরা আউটডোর গার্ডেন ডাইনিং', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- VIP
    ((SELECT id FROM dining_space_types WHERE code = 'VIP'),
     (SELECT id FROM locales WHERE code = 'en'),
     'VIP', 'Exclusive VIP dining experience with premium service', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM dining_space_types WHERE code = 'VIP'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'ভিআইপি', 'প্রিমিয়াম সেবা সহ এক্সক্লুসিভ ভিআইপি ডাইনিং অভিজ্ঞতা', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));
