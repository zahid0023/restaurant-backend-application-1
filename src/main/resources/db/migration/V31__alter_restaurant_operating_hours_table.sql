ALTER TABLE restaurant_operating_hours
    ADD COLUMN IF NOT EXISTS label           VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS closes_next_day BOOLEAN      NOT NULL DEFAULT false,
    ADD COLUMN IF NOT EXISTS is_closed       BOOLEAN      NOT NULL DEFAULT false;
