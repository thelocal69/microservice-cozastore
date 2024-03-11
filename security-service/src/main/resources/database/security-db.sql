CREATE DATABASE cozastore_security_service;
USE cozastore_security_service;

CREATE TABLE roles(
                      id bigint auto_increment,
                      role_name varchar(155),

                      primary key (id)
);

CREATE TABLE users(
                      id bigint auto_increment,
                      avatar_url varchar(255),
                      first_name varchar(155),
                      last_name varchar(155),
                      full_name varchar(255),
                      username varchar(255),
                      password varchar(255),
                      email varchar(255),
                      phone varchar(11),
                      status int not null ,
                      enable tinyint not null ,
                      role_id bigint not null ,
                      created_by varchar(255),
                      created_date timestamp,
                      last_modified_by varchar(255),
                      last_modified_date timestamp,
                      primary key (id)
);

CREATE TABLE refresh_token(
                              id bigint auto_increment,
                              token text,
                              token_type varchar(50),
                              expired tinyint,
                              revokes tinyint,
                              user_id bigint not null ,
                              primary key (id)
);

ALTER TABLE users ADD CONSTRAINT FK_role_user FOREIGN KEY (role_id) REFERENCES roles(id);
ALTER TABLE refresh_token ADD CONSTRAINT  FK_user_token FOREIGN KEY (user_id) REFERENCES users(id);

INSERT INTO roles(role_name) VALUES ('ADMIN'), ('USER');