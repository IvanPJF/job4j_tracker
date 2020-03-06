CREATE TABLE IF NOT EXISTS tracker (
   id serial primary key,
   name varchar(50),
   description varchar(100),
   create_date timestamp
);