CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    created TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS phones (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id UUID,
    number VARCHAR(20),
    city_code SMALLINT,
    country_code VARCHAR(4)
);