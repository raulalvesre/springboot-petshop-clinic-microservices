package br.com.raulalvesre.petshopcustomerservice.repositories;

import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByIdNotAndEmail(Long id, String email);
    boolean existsByIdNotAndCpf(Long id, String cpf);
    boolean existsByIdNotAndPhone(Long id, String phone);
    Set<Customer> findByIdIn(Collection<Long> idCollection);
    Optional<Customer> findByEmail(String email);

}