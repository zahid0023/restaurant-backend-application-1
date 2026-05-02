CREATE TABLE IF NOT EXISTS locales
(
    id         bigserial PRIMARY KEY,
    code       varchar(50)              NOT NULL UNIQUE,
    name       varchar(255)             NOT NULL,
    is_default boolean                  NOT NULL,
    sort_order integer                  NOT NULL,

    created_by bigint                   NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint                   NOT NULL,
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    bigint                            DEFAULT 0 NOT NULL,
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean                  NOT NULL DEFAULT false,
    deleted_by bigint,
    deleted_at timestamp with time zone
);
