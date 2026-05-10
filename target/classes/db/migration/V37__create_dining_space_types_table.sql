CREATE TABLE IF NOT EXISTS dining_space_types (
    id bigserial PRIMARY KEY,
    code varchar(50) NOT NULL,
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
