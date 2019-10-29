CREATE TABLE messages (
    id          CHAR(36) NOT NULL,
    text        VARCHAR NOT NULL,

    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT messages_pk PRIMARY KEY (id)
);

CREATE INDEX idx_messages_created_at ON messages (created_at);

CREATE TABLE users (
    id      CHAR(36) NOT NULL,
    name    VARCHAR NOT NULL,
    hash    CHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id)
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
    id      CHAR(36) NOT NULL,
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
    user_id CHAR(36) NOT NULL REFERENCES users,
    role_id CHAR(36) NOT NULL REFERENCES roles
);

-- Default group of 'admin' is 'ADMIN'
INSERT INTO user_roles (user_id, role_id)
VALUES (
    '5007f5be-bef7-428e-b40d-534d27c92f22',
    'fd80f768-44a4-42f8-9e35-d588b3b1f535'
);

-- Reference schema for Spring Session tables

CREATE TABLE spring_session (
	primary_id CHAR(36) NOT NULL,
	session_id CHAR(36) NOT NULL,
	creation_time BIGINT NOT NULL,
	last_access_time BIGINT NOT NULL,
	max_inactive_interval INT NOT NULL,
	expiry_time BIGINT NOT NULL,
	principal_name VARCHAR(100),

	CONSTRAINT spring_session_pk PRIMARY KEY (primary_id)
);

CREATE UNIQUE INDEX spring_session_ix1 ON spring_session (session_id);
CREATE INDEX spring_session_ix2 ON spring_session (expiry_time);
CREATE INDEX spring_session_ix3 ON spring_session (principal_name);

CREATE TABLE spring_session_attributes (
	session_primary_id CHAR(36) NOT NULL,
	attribute_name VARCHAR(200) NOT NULL,
	attribute_bytes BYTEA NOT NULL,

	CONSTRAINT spring_session_attributes_pk PRIMARY KEY (session_primary_id, attribute_name),
	CONSTRAINT spring_session_attributes_fk FOREIGN KEY (session_primary_id) REFERENCES spring_session ON DELETE CASCADE
);
