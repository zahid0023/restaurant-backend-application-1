CREATE TABLE IF NOT EXISTS dining_spaces (
    id bigserial PRIMARY KEY,
    dining_space_type_id bigint NOT NULL REFERENCES dining_space_types(id) ON DELETE RESTRICT,
    floor_id bigint REFERENCES floors(id) ON DELETE SET NULL,
    code varchar(50) NOT NULL,
    sort_order int NOT NULL DEFAULT 0,
    capacity int NOT NULL,
    is_bookable boolean NOT NULL DEFAULT true,
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
