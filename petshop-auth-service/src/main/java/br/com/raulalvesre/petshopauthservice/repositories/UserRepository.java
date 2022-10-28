package br.com.raulalvesre.petshopauthservice.repositories;

import br.com.raulalvesre.petshopauthservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    User getByEmail(String email);

    Optional<User> findByEmail(String email);
}