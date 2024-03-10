package com.cozastore.securityservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
public class JwtUtil {
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    @Value("${jwt.public-key}")
    private String publicKey;
    @Value("${jwt.private-key}")
    private String privateKey;

    private RSAPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(
                privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s+", ""));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    }

    private RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(
                publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s+", ""));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }

    public Date getExpirationDateFromToken(String token)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKey publicKey = getPublicKey();
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

    public boolean validateToken(String token)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (!isTokenExpired(token));
    }

    public String createAccessToken(String data) {
        try {
            RSAPrivateKey privateKey = getPrivateKey();
            return Jwts.builder()
                    .setSubject(data)
                    .claim("type", "access-token")
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                    .signWith(privateKey, SignatureAlgorithm.RS512)
                    .compact();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String createRefreshToken(String data) {
        try {
            RSAPrivateKey privateKey = getPrivateKey();
            return Jwts.builder()
                    .setSubject(data)
                    .claim("type", "refresh-token")
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                    .signWith(privateKey, SignatureAlgorithm.RS512)
                    .compact();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String parserToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }
}
