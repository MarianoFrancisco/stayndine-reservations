CREATE TABLE IF NOT EXISTS reservation (
    id            BINARY(16)  NOT NULL,
    hotel_id      BINARY(16)  NOT NULL,
    user_id       BINARY(16)  NOT NULL,
    customer_id   BINARY(16)  NULL,
    code          VARCHAR(20) NOT NULL,
    status        ENUM('PENDING','CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
    check_in      DATE        NOT NULL,
    check_out     DATE        NOT NULL,
    guests        INT         NOT NULL,
    currency      CHAR(3)     NOT NULL DEFAULT 'USD',
    total_amount  DECIMAL(12,2) NOT NULL,
    pricing_json  JSON        NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_user (user_id),
    KEY idx_hotel_dates (hotel_id, check_in, check_out)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS reservation_item (
    id              BINARY(16) NOT NULL,
    reservation_id  BINARY(16) NOT NULL,
    room_type_id    BINARY(16) NOT NULL,
    quantity        INT        NOT NULL,
    nightly_price DECIMAL(12,2) NOT NULL,
    nights          INT        NOT NULL,
    subtotal        DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_res (reservation_id),
    CONSTRAINT fk_res_item_res FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
