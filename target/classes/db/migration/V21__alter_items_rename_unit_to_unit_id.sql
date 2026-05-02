DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'items' AND column_name = 'unit'
    ) THEN
        ALTER TABLE items RENAME COLUMN unit TO unit_id;
    END IF;
END $$;
