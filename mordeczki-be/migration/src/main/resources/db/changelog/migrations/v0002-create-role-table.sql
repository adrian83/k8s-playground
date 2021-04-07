create table "auth_role" (
	id SERIAL,
	name varchar(128) not null,
	
	primary key (id)
);

create table "auth_account_role" (
	account_id varchar(128) not null,
	role_id int not null,
	
	PRIMARY KEY (role_id, account_id),
	CONSTRAINT auth_account_role_fk_1 FOREIGN KEY (account_id) REFERENCES auth_account (email),
	CONSTRAINT auth_account_role_fk_2 FOREIGN KEY (role_id) REFERENCES auth_role (id)
);

