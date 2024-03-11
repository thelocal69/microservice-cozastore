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

    public void parserToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getPublicKeys()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }

    private RSAPublicKey getPublicKeys() {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s+", ""))
        );
        RSAPublicKey rsaPublicKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
        return rsaPublicKey;
    }
}
