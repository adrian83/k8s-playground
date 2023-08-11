 
create table "AUTH_ACCOUNT" (
	email varchar(128) not null,
	password_hash varchar(256) not null,
	credentials_expired boolean not null,
	expired boolean not null,
	locked boolean not null,
	enabled boolean not null,
	primary key (email)
);


create table "AUTH_ROLE" (
	id SERIAL,
	name varchar(128) not null,
	
	primary key (id)
);

create table "AUTH_ACCOUNT_ROLE" (
	account_id varchar(128) not null,
	role_id int not null,
	
	PRIMARY KEY (role_id, account_id),
	CONSTRAINT auth_account_role_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (email),
	CONSTRAINT auth_account_role_fk_2 FOREIGN KEY (role_id) REFERENCES "AUTH_ROLE" (id)
);

create table "AUTH_RESET_PASSWORD" (
	id varchar(128) not null,
	account_id varchar(128) not null,
	creation_time_utc timestamp,
	
	PRIMARY KEY (id),
	CONSTRAINT auth_reset_password_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (email)
);
