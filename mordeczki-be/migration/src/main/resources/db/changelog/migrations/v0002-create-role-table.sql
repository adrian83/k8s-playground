create table "AUTH_ACCOUNT_ROLE" (
	account_id INT NOT NULL,
	role_id INT NOT NULL,
	
	PRIMARY KEY (role_id, account_id),

	CONSTRAINT auth_account_role_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (id),
	CONSTRAINT auth_account_role_fk_2 FOREIGN KEY (role_id) REFERENCES "AUTH_ROLE" (id)
);
