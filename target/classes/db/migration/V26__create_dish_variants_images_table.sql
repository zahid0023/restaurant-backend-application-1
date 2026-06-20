CREATE TABLE IF NOT EXISTS dish_variant_images
(
    id              bigserial PRIMARY KEY,

    config_id       bigint                               NOT NULL
        REFERENCES restaurant_image_hosting_configs (id),

    dish_variant_id bigint references dish_variants (id) NOT NULL,

    external_id     varchar(255),
    -- cloudinary public_id / s3 key
    url             text                                 NOT NULL UNIQUE,
    caption         varchar(255),

    sort_order      int                                  NOT NULL DEFAULT 0,

    created_by      bigint                               not null references users (id),
    created_at      timestamptz                          not null default current_timestamp,
    updated_by      bigint                               not null references users (id),
    updated_at      timestamptz                          not null default current_timestamp,
    version         bigint                               not null default 0,

    is_active       boolean                              not null default true,
    is_deleted      boolean                              not null default false,
    deleted_by      bigint,
    deleted_at      timestamptz
);