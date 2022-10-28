package br.com.raulalvesre.petshopauthservice.repositories;

import br.com.raulalvesre.petshopauthservice.models.PasswordRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Long> {
    Optional<PasswordRecoveryToken> findByToken(String token);

}