package com.cozastore.securityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "refresh_token")
public class TokenEntity extends AbstractAuditingEntity{
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revokes")
    private boolean revoke;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
