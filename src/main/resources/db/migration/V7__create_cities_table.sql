CREATE TABLE IF NOT EXISTS cities
(
    id         bigserial PRIMARY KEY,
    country_id bigint                       NOT NULL REFERENCES countries (id) ON DELETE RESTRICT,
    code       varchar(50),
    sort_order int                          NOT NULL DEFAULT 0,

    created_by bigint references users (id) NOT NULL,
    created_at timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint references users (id) NOT NULL,
    updated_at timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                                DEFAULT 0 NOT NULL,
    is_active  boolean                      NOT NULL DEFAULT true,
    is_deleted boolean                      NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS city_locales
(
    id          bigserial PRIMARY KEY,
    city_id     bigint                       NOT NULL REFERENCES cities (id) ON DELETE CASCADE,
    locale_id   bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name        varchar(255)                 NOT NULL,
    description text                         NOT NULL DEFAULT '',
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
    UNIQUE (city_id, locale_id)
);

INSERT INTO cities (code, country_id, sort_order, created_by, updated_by)
VALUES ('DHAKA',      (SELECT id FROM countries WHERE code = 'BD'), 1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('CTG',        (SELECT id FROM countries WHERE code = 'BD'), 2, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('SYLHET',     (SELECT id FROM countries WHERE code = 'BD'), 3, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('RAJSHAHI',   (SELECT id FROM countries WHERE code = 'BD'), 4, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('KHULNA',     (SELECT id FROM countries WHERE code = 'BD'), 5, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('BARISAL',    (SELECT id FROM countries WHERE code = 'BD'), 6, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('RANGPUR',    (SELECT id FROM countries WHERE code = 'BD'), 7, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),
       ('MYMENSINGH', (SELECT id FROM countries WHERE code = 'BD'), 8, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));

INSERT INTO city_locales (city_id,
                          locale_id,
                          name,
                          description,
                          sort_order,
                          created_by,
                          updated_by)
VALUES ((SELECT id FROM cities WHERE code = 'DHAKA'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'ঢাকা',
        'বাংলাদেশের রাজধানী শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'CTG'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'চট্টগ্রাম',
        'বাংলাদেশের প্রধান সমুদ্র বন্দর শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'SYLHET'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'সিলেট',
        'চা বাগান ও পাহাড়ি অঞ্চল',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'RAJSHAHI'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'রাজশাহী',
        'রেশম ও আমের শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'KHULNA'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'খুলনা',
        'সুন্দরবনের প্রবেশদ্বার',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'BARISAL'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'বরিশাল',
        'নদীমাতৃক শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'RANGPUR'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'রংপুর',
        'উত্তরাঞ্চলের প্রশাসনিক শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system')),

       ((SELECT id FROM cities WHERE code = 'MYMENSINGH'),
        (SELECT id FROM locales WHERE code = 'bn'),
        'ময়মনসিংহ',
        'শিক্ষা ও সংস্কৃতির শহর',
        1, (SELECT id FROM users WHERE username = 'system'), (SELECT id FROM users WHERE username = 'system'));
