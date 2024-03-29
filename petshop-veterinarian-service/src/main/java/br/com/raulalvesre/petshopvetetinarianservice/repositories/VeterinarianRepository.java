package br.com.raulalvesre.petshopvetetinarianservice.repositories;

import br.com.raulalvesre.petshopvetetinarianservice.models.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    boolean existsByIdNotAndEmail(Long id, String email);
    boolean existsByIdNotAndCpf(Long id, String cpf);
    boolean existsByIdNotAndCrmv(Long id, String crmv);
    boolean existsByIdNotAndPhone(Long id, String phone);
    Set<Veterinarian> findByIdIn(Collection<Long> idCollection);
    Optional<Veterinarian> findByEmail(String email);

}