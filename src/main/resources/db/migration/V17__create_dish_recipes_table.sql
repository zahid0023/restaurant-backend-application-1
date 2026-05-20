CREATE TABLE IF NOT EXISTS dish_recipes
(
    id              bigserial PRIMARY KEY,

    dish_variant_id bigint                       NOT NULL REFERENCES dish_variants (id) ON DELETE CASCADE,

    code            varchar(50),

    created_by      bigint references users (id) NOT NULL,
    created_at      timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by      bigint references users (id) NOT NULL,
    updated_at      timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version         bigint                                DEFAULT 0 NOT NULL,
    is_active       boolean                      NOT NULL DEFAULT true,
    is_deleted      boolean                      NOT NULL DEFAULT false,
    deleted_by      bigint,
    deleted_at      timestamp with time zone
);

CREATE TABLE IF NOT EXISTS dish_recipe_ingredients
(
    id             bigserial PRIMARY KEY,

    dish_recipe_id bigint                       NOT NULL REFERENCES dish_recipes (id) ON DELETE CASCADE,
    item_id        bigint                       NOT NULL REFERENCES items (id),

    quantity       numeric(18, 6),
    unit_id        bigint references units (id),

    created_by     bigint references users (id) NOT NULL,
    created_at     timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by     bigint references users (id) NOT NULL,
    updated_at     timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version        bigint                                DEFAULT 0 NOT NULL,
    is_active      boolean                      NOT NULL DEFAULT true,
    is_deleted     boolean                      NOT NULL DEFAULT false,
    deleted_by     bigint,
    deleted_at     timestamp with time zone
);

