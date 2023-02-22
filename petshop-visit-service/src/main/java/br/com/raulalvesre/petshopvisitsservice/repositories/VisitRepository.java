package br.com.raulalvesre.petshopvisitsservice.repositories;

import br.com.raulalvesre.petshopvisitsservice.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {


}