

create table "AUTH_ACCOUNT_ROLE" (
	account_id varchar(128) not null,
	role_id int not null,
	
	PRIMARY KEY (role_id, account_id),
	CONSTRAINT auth_account_role_fk_1 FOREIGN KEY (account_id) REFERENCES "AUTH_ACCOUNT" (email),
	CONSTRAINT auth_account_role_fk_2 FOREIGN KEY (role_id) REFERENCES "AUTH_ROLE" (id)
);
