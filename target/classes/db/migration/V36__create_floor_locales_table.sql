CREATE TABLE IF NOT EXISTS floor_locales (
    id bigserial PRIMARY KEY,
    floor_id bigint NOT NULL REFERENCES floors(id) ON DELETE CASCADE,
    locale_id bigint NOT NULL REFERENCES locales(id) ON DELETE RESTRICT,
    name varchar(255) NOT NULL,
    description text,
    sort_order int NOT NULL DEFAULT 0,
    created_by bigint NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version bigint NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamptz
);
