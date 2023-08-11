create table "AUTH_RESET_PASSWORD" (
	id varchar(128) not null,
	account_id varchar(128) not null,
	creation_time_utc timestamp,
	
	PRIMARY KEY (id),
	CONSTRAINT auth_reset_password_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (email)
);
