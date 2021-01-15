drop table if exists users;
drop table if exists roles;
drop table if exists user_roles;
drop table if exists address;
drop table if exists bank_info;
drop table if exists contact;
drop table if exists customer;
drop table if exists account;
drop table if exists customer_account;
drop table if exists transaction;

CREATE TABLE users (
	user_id bigserial NOT NULL,
	enabled bool NOT NULL,
	full_name varchar(255) NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


CREATE TABLE roles (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id)
);


CREATE TABLE user_roles (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES roles(id),
	CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users(user_id)
);



CREATE TABLE address (
	add_id bigserial NOT NULL,
	address1 varchar(255) NULL,
	address2 varchar(255) NULL,
	city varchar(255) NULL,
	country varchar(255) NULL,
	postcode varchar(255) NULL,
	state varchar(255) NULL,
	CONSTRAINT address_pkey PRIMARY KEY (add_id)
);



CREATE TABLE bank_info (
	bank_id int8 NOT NULL,
	branch_code int4 NULL,
	branch_name varchar(255) NULL,
	branch_address_add_id int8 NULL,
	CONSTRAINT bank_info_pkey PRIMARY KEY (bank_id),
	CONSTRAINT fk1vn2dhkixv4hiri1ul2gej67e FOREIGN KEY (branch_address_add_id) REFERENCES address(add_id)
);


CREATE TABLE contact (
	cont_id bigserial NOT NULL,
	contact_no varchar(255) NULL,
	email_id varchar(255) NULL,
	CONSTRAINT contact_pkey PRIMARY KEY (cont_id)
);



CREATE TABLE customer (
	cust_id bigserial NOT NULL,
	create_date_time date NULL,
	customer_number int8 NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	contact_details_cont_id int8 NULL,
	customer_address_add_id int8 NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (cust_id),
	CONSTRAINT uk_mo8uc9hxwlycihsxq6ucdxs1c UNIQUE (customer_number),
	CONSTRAINT fkgnqqgqd5qm78ed24vv23v86xk FOREIGN KEY (contact_details_cont_id) REFERENCES contact(cont_id),
	CONSTRAINT fkr974lj38cy4yvgpclymcc7i5g FOREIGN KEY (customer_address_add_id) REFERENCES address(add_id)
);


CREATE TABLE account (
	acc_id bigserial NOT NULL,
	account_balance float8 NULL,
	account_number int8 NULL,
	account_type int4 NULL,
	created_at date NULL,
	modified_at date NULL,
	bank_information_bank_id int8 NULL,
	CONSTRAINT account_pkey PRIMARY KEY (acc_id),
	CONSTRAINT fkau908n3yo0tq3jcip43k6l7r6 FOREIGN KEY (bank_information_bank_id) REFERENCES bank_info(bank_id)
);



CREATE TABLE customer_account (
	cust_acc_id bigserial NOT NULL,
	account_number int8 NULL,
	customer_number int8 NULL,
	CONSTRAINT customer_account_pkey PRIMARY KEY (cust_acc_id)
);



CREATE TABLE transaction (
	tx_id int8 NOT NULL,
	account_number int8 NULL,
	amount float8 NULL,
	"date" date NULL,
	"type" varchar(255) NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (tx_id)
);



