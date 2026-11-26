CREATE TABLE users (
                       id    BIGSERIAL PRIMARY KEY,
                       name  VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       role  VARCHAR(255) NOT NULL
);

CREATE TABLE rooms (
                      id          BIGSERIAL PRIMARY KEY,
                      name        VARCHAR(255) NOT NULL,
                      capacity    INTEGER NOT NULL,
                      description VARCHAR(255),
                      active      BOOLEAN NOT NULL
);

CREATE TABLE bookings (
                          id         BIGSERIAL PRIMARY KEY,
                          user_id    BIGINT NOT NULL REFERENCES users (id),
                          room_id    BIGINT NOT NULL REFERENCES rooms (id),
                          start_time TIMESTAMPTZ NOT NULL,
                          end_time   TIMESTAMPTZ NOT NULL,
                          status     VARCHAR(255) NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL,
                          CONSTRAINT chk_booking_time CHECK (end_time > start_time)
);

CREATE INDEX idx_booking_room_time ON bookings (room_id, start_time, end_time);
CREATE INDEX idx_booking_user ON bookings (user_id);