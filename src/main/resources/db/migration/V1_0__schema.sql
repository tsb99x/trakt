CREATE TABLE messages (
    id      IDENTITY,
    text    VARCHAR,
);

CREATE TABLE users (
    id      IDENTITY,
    name    VARCHAR NOT NULL,
    hash    VARCHAR NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Default credentials are 'admin:admin'
INSERT INTO users (name, hash, enabled)
VALUES ('admin', '$2a$11$HXUtm.6N8OmQ9biKeW.vpOwmW5uoEG5GKa4J49e1RCM0RKHvnb23i', TRUE);

CREATE TABLE roles (
    id      IDENTITY,
    name    VARCHAR NOT NULL
);

-- Default roles list
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Default group of 'admin' is 'ADMIN'
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
