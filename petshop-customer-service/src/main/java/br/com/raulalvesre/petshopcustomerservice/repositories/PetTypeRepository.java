package br.com.raulalvesre.petshopcustomerservice.repositories;

import br.com.raulalvesre.petshopcustomerservice.models.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}
