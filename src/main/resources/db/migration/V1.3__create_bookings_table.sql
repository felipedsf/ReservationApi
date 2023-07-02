CREATE TABLE IF NOT EXISTS booking(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    guest_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS booking ADD CONSTRAINT FK_BOOKING_USER
    FOREIGN KEY (guest_id) REFERENCES users;

ALTER TABLE IF EXISTS booking ADD CONSTRAINT FK_BOOKING_PROPERTY
    FOREIGN KEY (property_id) REFERENCES property;