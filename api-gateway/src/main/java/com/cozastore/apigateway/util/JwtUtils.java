package com.cozastore.apigateway.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtUtils {

    @Value("${jwt.public-key}")
    private String publicKey;

    public String parserToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }

    private RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(
                publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s+", ""));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }
}
