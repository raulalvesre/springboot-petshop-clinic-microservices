package br.com.raulalvesre.petshopauthservice.repositories;

import br.com.raulalvesre.petshopauthservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String standard);
}