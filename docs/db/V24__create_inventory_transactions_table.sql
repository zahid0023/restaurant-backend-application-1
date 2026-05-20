CREATE TABLE IF NOT EXISTS inventory_transactions
(
    id               bigserial                    PRIMARY KEY,

    -- core references
    item_id          bigint                       NOT NULL REFERENCES items (id),
    location_id      bigint                       NOT NULL REFERENCES inventory_locations (id),

    -- movement classification
    transaction_type varchar(30)                  NOT NULL,
    /*
        PURCHASE            -> stock in
        SALE                -> stock out
        CONSUME             -> usage (recipe/production)
        ADJUSTMENT          -> manual correction
        TRANSFER_IN         -> incoming transfer
        TRANSFER_OUT        -> outgoing transfer
        RETURN              -> customer return (stock in)
        RETURN_TO_SUPPLIER  -> return to supplier (stock out)
    */

    -- quantity movement
    quantity         numeric(18, 6)               NOT NULL,
    -- convention:
    -- positive = IN
    -- negative = OUT (enforce in service layer)

    -- optional costing (useful for accounting later)
    unit_cost        numeric(18, 6),
    total_cost       numeric(18, 6),

    notes            text,

    created_by       bigint                       NOT NULL,
    created_at       timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by       bigint                       NOT NULL,
    updated_at       timestamp with time zone     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version          bigint                                DEFAULT 0 NOT NULL,
    is_active        boolean                      NOT NULL DEFAULT true,
    is_deleted       boolean                      NOT NULL DEFAULT false,
    deleted_by       bigint,
    deleted_at       timestamp with time zone
);
