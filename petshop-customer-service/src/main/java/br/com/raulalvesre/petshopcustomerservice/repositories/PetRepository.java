package br.com.raulalvesre.petshopcustomerservice.repositories;

import br.com.raulalvesre.petshopcustomerservice.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner_Id(Long id);

}
