package br.com.raulalvesre.petshopauthservice.repositories;

import br.com.raulalvesre.petshopauthservice.models.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {
    Optional<EmailConfirmationToken> findByToken(String confirmationToken);
}