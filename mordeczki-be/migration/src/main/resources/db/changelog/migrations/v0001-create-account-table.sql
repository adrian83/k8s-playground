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