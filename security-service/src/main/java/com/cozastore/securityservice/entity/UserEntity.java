package com.cozastore.securityservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity extends AbstractAuditingEntity{
    @Id
    @Indexed(unique = true)
    private String id;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "full_name")
    private String fullName;
    @Field(name = "username")
    private String username;
    @Field(name = "password")
    private String password;
    @Field(name = "email")
    private String email;
    @Field(name = "status")
    private int status;
    @Field(name = "role")
    private RoleEntity role;
}
