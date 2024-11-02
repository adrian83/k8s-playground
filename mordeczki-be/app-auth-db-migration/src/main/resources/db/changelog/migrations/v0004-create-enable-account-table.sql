create table "AUTH_ENABLE_ACCOUNT" (
	token VARCHAR(128) NOT NULL,
	account_id INT NOT NULL,
	creation_time TIMESTAMP WITH TIME ZONE,
	
	PRIMARY KEY (token),
	
	CONSTRAINT auth_enable_account_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id)
);
