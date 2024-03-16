CREATE SCHEMA if not exists rest;

CREATE TABLE rest.sensor
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE rest.measurement
(
    id                    SERIAL PRIMARY KEY,
    value                 DOUBLE PRECISION NOT NULL,
    raining               BOOLEAN          NOT NULL,
    measurement_date_time TIMESTAMP        NOT NULL,
    sensor             varchar REFERENCES rest.sensor (name)
);