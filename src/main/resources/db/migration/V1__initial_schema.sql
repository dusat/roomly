CREATE SEQUENCE room_seq START WITH 1 INCREMENT BY 50 CACHE 50;
CREATE SEQUENCE location_seq START WITH 1 INCREMENT BY 50 CACHE 50;
CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 50 CACHE 50;

CREATE TABLE locations (
                           id BIGINT PRIMARY KEY DEFAULT nextval('location_seq'),
                           country VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           street VARCHAR(255),
                           street_number VARCHAR(50) NOT NULL,
                           UNIQUE (country, city, street, street_number)
);

CREATE TABLE rooms (
                       id BIGINT PRIMARY KEY DEFAULT nextval('room_seq'),
                       active BOOLEAN NOT NULL,
                       location_id BIGINT NOT NULL,
                       person_capacity INTEGER NOT NULL,
                       CONSTRAINT fk_room_location FOREIGN KEY (location_id)
                           REFERENCES locations (id) ON DELETE CASCADE
);

CREATE TABLE room_equipment (
                       room_id BIGINT NOT NULL,
                       equipment VARCHAR(50) NOT NULL,
                       PRIMARY KEY (room_id, equipment),
                       CONSTRAINT fk_equipment_room FOREIGN KEY (room_id)
                           REFERENCES rooms (id) ON DELETE CASCADE
);

CREATE TABLE reservations (
                       id BIGINT PRIMARY KEY DEFAULT nextval('reservation_seq'),
                       room_id BIGINT NOT NULL,
                       date_from TIMESTAMP NOT NULL,
                       date_to TIMESTAMP NOT NULL,
                       CONSTRAINT fk_reservation_room FOREIGN KEY (room_id)
                             REFERENCES rooms (id) ON DELETE CASCADE
);

CREATE INDEX ix_reservation_room_from_to
    ON reservations (room_id, date_from, date_to);

