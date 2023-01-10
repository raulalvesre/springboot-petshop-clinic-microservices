package br.com.raulalvesre.petshopadminservice.repositories;

import br.com.raulalvesre.petshopadminservice.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByIdNotAndEmail(Long id, String email);
    boolean existsByIdNotAndCpf(Long id, String cpf);
    boolean existsByIdNotAndPhone(Long id, String phone);
    Set<Admin> findByIdIn(Collection<Long> idCollection);
    Optional<Admin> findByEmail(String email);

}