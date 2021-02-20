 
create table "user" (
  email varchar(128) not null,
  password_hash varchar(256) not null,
  
  primary key (email)
);