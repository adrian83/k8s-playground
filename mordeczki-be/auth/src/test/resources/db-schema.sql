 
create table "auth_user" (
	email varchar(128) not null,
	password_hash varchar(256) not null,

	primary key (email)
);


create table "auth_role" (
	id SERIAL,
	name varchar(128) not null,
	
	primary key (id)
);

create table "auth_user_role" (
	user_id varchar(128) not null,
	role_id int not null,
	
	PRIMARY KEY (role_id, user_id),
	CONSTRAINT auth_user_role_fk_1 FOREIGN KEY (user_id) REFERENCES auth_user (email),
	CONSTRAINT auth_user_role_fk_2 FOREIGN KEY (role_id) REFERENCES auth_role (id)
);

