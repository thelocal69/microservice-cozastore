package com.cozastore.securityservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    @Value("${jwt.public-key}")
    private String publicKey;
    @Value("${jwt.private-key}")
    private String privateKey;

    private RSAPrivateKey getPrivateKeys() {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s+", ""))
        );
        RSAPrivateKey rsaPrivateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.info(e.getMessage());
        }
        return rsaPrivateKey;
    }

    private RSAPublicKey getPublicKeys() {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s+", ""))
        );
        RSAPublicKey rsaPublicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.info(e.getMessage());
        }
        return rsaPublicKey;
    }

    public Date getExpirationDateFromToken(String token)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKey publicKey = getPublicKeys();
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
        return jws.getBody().getExpiration();

    }

    public boolean isTokenExpired(String token) {
        Date expirationDate;
        try {
            expirationDate = getExpirationDateFromToken(token);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }
        return expirationDate.before(new Date());
    }


    public String createAccessToken(String data) {
        RSAPrivateKey privateKey = getPrivateKeys();
        return Jwts.builder()
                .setSubject(data)
                .claim("type", "access-token")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(privateKey, SignatureAlgorithm.RS512)
                .compact();
    }

    public String createRefreshToken(String data) {
        RSAPrivateKey privateKey = getPrivateKeys();
        return Jwts.builder()
                .setSubject(data)
                .claim("type", "refresh-token")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(privateKey, SignatureAlgorithm.RS512)
                .compact();
    }

    public Claims parserToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKey rsaPublicKey = getPublicKeys();
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(rsaPublicKey).build()
                .parseClaimsJws(token);
        return claimsJws.getBody();
    }
}
