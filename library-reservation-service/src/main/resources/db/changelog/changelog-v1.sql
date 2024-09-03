-- liquibase formatted sql

-- changeset Alexey_Bobrovich:1725203347374-1
CREATE SEQUENCE IF NOT EXISTS books_reservations_seq START WITH 1 INCREMENT BY 50;

-- changeset Alexey_Bobrovich:1725203347374-2
CREATE TABLE books_reservations
(
    id            BIGINT                      NOT NULL,
    book_id       BIGINT                      NOT NULL,
    user_id       BIGINT                      NOT NULL,
    borrowed_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    return_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_books_reservations PRIMARY KEY (id)
);

