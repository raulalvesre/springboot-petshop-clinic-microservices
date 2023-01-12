package br.com.raulalvesre.petshopauthservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtGeneratorService {

    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;

    @Value("${jwt.validityMs}")
    private String tokenValidity;

    public String generateJwt(String subject, Map<String, String> claims) {
        JWTCreator.Builder builder = JWT.create().withSubject(subject);

        claims.forEach(builder::withClaim);

        Date expiration =  new Date(new Date().getTime() + Long.parseLong(tokenValidity));

        return builder
                .withExpiresAt(expiration)
                .sign(Algorithm.RSA256(rsaPublicKey, rsaPrivateKey));
    }

}
