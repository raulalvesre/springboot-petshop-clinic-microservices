package br.com.raulalvesre.petshopvisitservice.repositories;

import br.com.raulalvesre.petshopvisitservice.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {


}