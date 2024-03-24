package com.cozastore.userservice.entity;

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
    @Field(name = "avatar_url")
    private String avatarUrl;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "full_name")
    private String fullName;
    @Field(name = "username")
    private String username;
    @Field(name = "email")
    private String email;
    @Field(name = "phone")
    private String phone;
    @Field(name = "user_id ")
    private Long userId;
    @Field(name = "status")
    private int status;
    @Field(name = "enable")
    private boolean isEnable;
}
