CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_modified_date DATE NOT NULL,
    address_id BIGINT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id)
);