CREATE TABLE IF NOT EXISTS item_types
(
    id            bigserial PRIMARY KEY,
    code          varchar(50)                  NOT NULL UNIQUE,
    is_consumable boolean                      NOT NULL DEFAULT false,
    sort_order    int                          NOT NULL DEFAULT 0,

    created_by    bigint references users (id) NOT NULL,
    created_at    timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by    bigint references users (id) NOT NULL,
    updated_at    timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version       bigint                                DEFAULT 0 NOT NULL,
    is_active     boolean                      NOT NULL DEFAULT true,
    is_deleted    boolean                      NOT NULL DEFAULT false,
    deleted_by    bigint,
    deleted_at    timestamp with time zone
);

CREATE TABLE IF NOT EXISTS item_type_locales
(
    id           bigserial PRIMARY KEY,
    item_type_id bigint                       NOT NULL REFERENCES item_types (id) ON DELETE CASCADE,
    locale_id    bigint                       NOT NULL REFERENCES locales (id) ON DELETE RESTRICT,

    name         varchar(255)                 NOT NULL,
    description  text                         NOT NULL DEFAULT '',
    sort_order   int                          NOT NULL DEFAULT 0,

    created_by   bigint references users (id) NOT NULL,
    created_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint references users (id) NOT NULL,
    updated_at   timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      bigint                                DEFAULT 0 NOT NULL,
    is_active    boolean                      NOT NULL DEFAULT true,
    is_deleted   boolean                      NOT NULL DEFAULT false,
    deleted_by   bigint,
    deleted_at   timestamp with time zone,
    UNIQUE (item_type_id, locale_id)
);

DO
$$
    DECLARE
        sys_id bigint;
    BEGIN
        SELECT id INTO sys_id FROM users WHERE username = 'system';

        INSERT INTO item_types (code, is_consumable, sort_order, created_by, updated_by)
        VALUES ('INGREDIENT', true,  1, sys_id, sys_id),
               ('PACKAGING',  false, 2, sys_id, sys_id),
               ('ASSET',      false, 3, sys_id, sys_id),
               ('EQUIPMENT',  false, 4, sys_id, sys_id)
        ON CONFLICT (code) DO NOTHING;

        INSERT INTO item_type_locales (item_type_id, locale_id, name, description, sort_order, created_by, updated_by)
        SELECT it.id,
               l.id,
               v.name,
               v.description,
               it.sort_order,
               sys_id,
               sys_id
        FROM item_types it
                 JOIN (VALUES
                           -- =========================
                           -- INGREDIENT
                           -- =========================
                           ('INGREDIENT', 'en', 'Ingredient', 'Items used in recipes or production'),
                           ('INGREDIENT', 'bn', 'উপাদান', 'রেসিপি বা উৎপাদনে ব্যবহৃত উপকরণ'),

                           -- =========================
                           -- PACKAGING
                           -- =========================
                           ('PACKAGING', 'en', 'Packaging', 'Materials used for packaging products'),
                           ('PACKAGING', 'bn', 'প্যাকেজিং', 'পণ্য মোড়কজাত করার উপকরণ'),

                           -- =========================
                           -- ASSET
                           -- =========================
                           ('ASSET', 'en', 'Asset', 'Long-term business-owned resources'),
                           ('ASSET', 'bn', 'সম্পদ', 'ব্যবসার দীর্ঘমেয়াদি মালিকানাধীন সম্পদ'),

                           -- =========================
                           -- EQUIPMENT
                           -- =========================
                           ('EQUIPMENT', 'en', 'Equipment', 'Tools and machines used in operations'),
                           ('EQUIPMENT', 'bn', 'যন্ত্রপাতি', 'কার্যক্রমে ব্যবহৃত সরঞ্জাম ও মেশিন')
                      ) v(code, locale_code, name, description) ON it.code = v.code
                 JOIN locales l ON l.code = v.locale_code;
    END
$$;
