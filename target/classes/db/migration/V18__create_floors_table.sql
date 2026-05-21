CREATE TABLE IF NOT EXISTS floors
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

CREATE TABLE IF NOT EXISTS floor_locales
(
    id          bigserial PRIMARY KEY,

    floor_id    bigint                       NOT NULL REFERENCES floors (id) ON DELETE CASCADE,
    locale_id   bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)                 NOT NULL,
    description text,
    sort_order  int                          NOT NULL DEFAULT 0,

    created_by  bigint references users (id) NOT NULL,
    created_at  timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint references users (id) NOT NULL,
    updated_at  timestamptz                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     bigint                       NOT NULL DEFAULT 0,
    is_active   boolean                      NOT NULL DEFAULT true,
    is_deleted  boolean                      NOT NULL DEFAULT false,
    deleted_by  bigint,
    deleted_at  timestamptz
);

INSERT INTO floors (code, sort_order, created_by, updated_by)
VALUES ('GF',  1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('FF',  2, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('SF',  3, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('TF',  4, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('RT',  5, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('BSM', 6, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('VIP', 7, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

INSERT INTO floor_locales (floor_id, locale_id, name, description, sort_order, created_by, updated_by)
VALUES
    -- Ground Floor
    ((SELECT id FROM floors WHERE code = 'GF'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Ground Floor', 'Main dining area on the ground level', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'GF'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'গ্রাউন্ড ফ্লোর', 'মূল খাবার এলাকা', 1,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- First Floor
    ((SELECT id FROM floors WHERE code = 'FF'),
     (SELECT id FROM locales WHERE code = 'en'),
     'First Floor', 'Dining area on the first level', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'FF'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'প্রথম তলা', 'প্রথম স্তরের খাবার এলাকা', 2,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Second Floor
    ((SELECT id FROM floors WHERE code = 'SF'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Second Floor', 'Dining area on the second level', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'SF'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'দ্বিতীয় তলা', 'দ্বিতীয় স্তরের খাবার এলাকা', 3,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Third Floor
    ((SELECT id FROM floors WHERE code = 'TF'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Third Floor', 'Dining area on the third level', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'TF'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'তৃতীয় তলা', 'তৃতীয় স্তরের খাবার এলাকা', 4,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Rooftop
    ((SELECT id FROM floors WHERE code = 'RT'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Rooftop', 'Open-air rooftop dining area', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'RT'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'ছাদ', 'খোলা ছাদের খাবার এলাকা', 5,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- Basement
    ((SELECT id FROM floors WHERE code = 'BSM'),
     (SELECT id FROM locales WHERE code = 'en'),
     'Basement', 'Underground dining and event space', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'BSM'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'বেসমেন্ট', 'ভূগর্ভস্থ খাবার ও ইভেন্ট স্পেস', 6,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    -- VIP Lounge
    ((SELECT id FROM floors WHERE code = 'VIP'),
     (SELECT id FROM locales WHERE code = 'en'),
     'VIP Lounge', 'Exclusive VIP dining and lounge area', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

    ((SELECT id FROM floors WHERE code = 'VIP'),
     (SELECT id FROM locales WHERE code = 'bn'),
     'ভিআইপি লাউঞ্জ', 'এক্সক্লুসিভ ভিআইপি ডাইনিং এলাকা', 7,
     (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

