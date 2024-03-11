package com.cozastore.securityservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "verification_token")
public class VerificationTokenEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "expired_time")
    private Date expired;
    private static final int EXPIRATION_TIME = 15;

    @OneToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    public VerificationTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expired = getTokenExpirationTime();
    }

    public VerificationTokenEntity(String token) {
        this.token = token;
        this.expired = getTokenExpirationTime();
    }

    public Date getTokenExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
