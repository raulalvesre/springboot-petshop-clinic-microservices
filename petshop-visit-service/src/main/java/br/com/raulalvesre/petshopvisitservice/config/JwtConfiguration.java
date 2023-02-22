package br.com.raulalvesre.petshopvisitservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


@Configuration
public class JwtConfiguration {

    Logger logger = LoggerFactory.getLogger(JwtConfiguration.class);

    @Value("${security.jks.value}")
    private String keyStoreValue;

    @Value("${security.jks.password}")
    private String keyStorePassword;

    @Value("${security.jks.alias}")
    private String keyAlias;

    @Value("${security.jks.private-key-passphrase}")
    private String privateKeyPassphrase;

    public KeyStore keyStore() {
        byte[] jksFile = Base64.getMimeDecoder()
                .decode(keyStoreValue);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] passwordAsCharArray = keyStorePassword.toCharArray();
            keyStore.load(new ByteArrayInputStream(jksFile), passwordAsCharArray);
            return keyStore;
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
            logger.error("Unable to load JKS KeyStore", e);
        }

        throw new RuntimeException("Unable to load JKS KeyStore");
    }

    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        try {
            Key key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            logger.error("Unable to load private key", e);
        }

        throw new RuntimeException("Unable to load private key");
    }

    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(keyAlias);
            PublicKey publicKey =  certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
        } catch (KeyStoreException e) {
            logger.error("Unable to load private key", e);
        }

        throw new RuntimeException("Unable to load RSA public key");
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(jwtValidationKey(keyStore())).build();
    }

}
