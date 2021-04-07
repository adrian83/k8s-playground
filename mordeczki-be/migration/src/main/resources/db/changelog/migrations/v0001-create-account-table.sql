 
create table "auth_account" (
	email varchar(128) not null,
	password_hash varchar(256) not null,
	credentials_expired boolean not null,
	expired boolean not null,
	locked boolean not null,
	enabled boolean not null,
	primary key (email)
);
