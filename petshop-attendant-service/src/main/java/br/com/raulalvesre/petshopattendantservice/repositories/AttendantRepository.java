package br.com.raulalvesre.petshopattendantservice.repositories;

import br.com.raulalvesre.petshopattendantservice.models.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AttendantRepository extends JpaRepository<Attendant, Long> {

    boolean existsByIdNotAndEmail(Long id, String email);
    boolean existsByIdNotAndCpf(Long id, String cpf);
    boolean existsByIdNotAndPhone(Long id, String phone);
    Set<Attendant> findByIdIn(Collection<Long> idCollection);
    Optional<Attendant> findByEmail(String email);

}