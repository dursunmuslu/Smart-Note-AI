CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE notes (
                       id BIGSERIAL PRIMARY KEY,
                       raw_text TEXT,
                       summary TEXT,
                       status VARCHAR(20) NOT NULL,
                       user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now()
);