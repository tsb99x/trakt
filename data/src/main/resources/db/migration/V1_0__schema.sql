CREATE TABLE messages (
    id          UUID NOT NULL,
    text        VARCHAR NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT messages_pk PRIMARY KEY (id)
);

CREATE INDEX idx_messages_created_at ON messages (created_at);

CREATE TABLE users (
    id      UUID NOT NULL,
    name    VARCHAR NOT NULL,
    hash    CHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE api_tokens (
    id              UUID NOT NULL,
    user_id         UUID NOT NULL REFERENCES users,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_used_at    TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT api_tokens_pk PRIMARY KEY (id)
);

-- Default credentials are 'admin:admin'
INSERT INTO users (id, name, hash, enabled)
VALUES (
    '5007f5be-bef7-428e-b40d-534d27c92f22',
    'admin',
    '$2a$11$HXUtm.6N8OmQ9biKeW.vpOwmW5uoEG5GKa4J49e1RCM0RKHvnb23i',
    TRUE
);

CREATE TABLE roles (
    id      UUID NOT NULL,
    name    VARCHAR NOT NULL,

    CONSTRAINT roles_pk PRIMARY KEY (id)
);

-- Default roles list
INSERT INTO roles (id, name)
VALUES (
    'fd80f768-44a4-42f8-9e35-d588b3b1f535',
    'ROLE_ADMIN'
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users,
    role_id UUID NOT NULL REFERENCES roles
);

-- Default group of 'admin' is 'ADMIN'
INSERT INTO user_roles (user_id, role_id)
VALUES (
    '5007f5be-bef7-428e-b40d-534d27c92f22',
    'fd80f768-44a4-42f8-9e35-d588b3b1f535'
);
