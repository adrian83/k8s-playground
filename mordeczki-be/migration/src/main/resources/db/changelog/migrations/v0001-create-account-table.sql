 create table "AUTH_ACCOUNT" (
    id SERIAL,
    email VARCHAR(255) UNIQUE NOT NULL,
	password_hash VARCHAR(256) NOT NULL,
	expired BOOLEAN NOT NULL,
	locked BOOLEAN NOT NULL,
	enabled BOOLEAN NOT NULL,

	PRIMARY KEY (id)
);

create table "AUTH_ROLE" (
	id SERIAL,
	name VARCHAR(128) NOT NULL,

	PRIMARY KEY (id)
);