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

create table "AUTH_ACCOUNT_ROLE" (
	account_id INT NOT NULL,
	role_id INT NOT NULL,
	
	PRIMARY KEY (role_id, account_id),

	CONSTRAINT auth_account_role_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id),
	CONSTRAINT auth_account_role_fk_2 FOREIGN KEY (role_id) REFERENCES "AUTH_ROLE" (id)
);

create table "AUTH_RESET_PASSWORD" (
	id VARCHAR(128) NOT NULL,
	account_id INT NOT NULL,
	creation_time TIMESTAMP WITH TIME ZONE,
	
	PRIMARY KEY (id),
	
	CONSTRAINT auth_reset_password_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id)
);

create table "AUTH_ENABLE_ACCOUNT" (
	token VARCHAR(128) NOT NULL,
	account_id INT NOT NULL,
	creation_time TIMESTAMP WITH TIME ZONE,
	
	PRIMARY KEY (token),
	
	CONSTRAINT auth_enable_account_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id)
);



