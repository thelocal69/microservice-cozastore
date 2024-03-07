CREATE DATABASE cozastore_order_service;
USE cozastore_order_service;

CREATE TABLE order_product(
                              id bigint auto_increment,
                              order_no varchar(255) not null unique,
                                user_id bigint not null,
                              product_id bigint not null ,
                                status int not null ,
                              quantity int,
                              price decimal(12, 2),
                              created_by varchar(155),
                              created_date timestamp,
                              last_modified_by varchar(155),
                              last_modified_date timestamp,
                              primary key (id)
);
