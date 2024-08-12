create table "AUTH_RESET_PASSWORD" (
	id VARCHAR(128) NOT NULL,
	account_id INT NOT NULL,
	creation_time TIMESTAMP WITH TIME ZONE,
	
	PRIMARY KEY (id),
	
	CONSTRAINT auth_reset_password_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id)
);
