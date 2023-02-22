package br.com.raulalvesre.petshopvetsservice.services;

import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianAuthDto;
import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianDto;
import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianForm;
import br.com.raulalvesre.petshopvetsservice.mappers.VeterinarianMapper;
import br.com.raulalvesre.petshopvetsservice.models.Veterinarian;
import br.com.raulalvesre.petshopvetsservice.repositories.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianMapper veterinarianMapper;
    Logger logger = LoggerFactory.getLogger(VeterinarianService.class);

    public VeterinarianDto getById(Long id) {
        logger.info("Getting veterinarian with id=" + id);
        return veterinarianRepository.findById(id)
                .map(VeterinarianDto::new)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian with id " + id + " not found!");
                });
    }

    public VeterinarianAuthDto getByEmail(String email) {
        logger.info("Getting veterinarian with email=" + email);
        return veterinarianRepository.findByEmail(email)
                .map(VeterinarianAuthDto::new)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with email " + email + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian with email " + email + " not found!");
                });
    }

    public Page<VeterinarianDto> getPage(Pageable pageable) {
        logger.info("Getting veterinarian page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize());
        return veterinarianRepository.findAll(pageable)
                .map(VeterinarianDto::new);
    }

    public Set<VeterinarianDto> getByIdIn(Collection<Long> idCollection) {
        logger.info("Getting veterinarians with id in " + idCollection);
        return veterinarianRepository.findByIdIn(idCollection).stream()
                .map(VeterinarianDto::new)
                .collect(Collectors.toSet());
    }
    
    public VeterinarianDto create(VeterinarianForm form) {
        validateUniqueFields(form);
        logger.info("Creating customer");
        Veterinarian customer = veterinarianMapper.toModel(form);
        veterinarianRepository.save(customer);
        logger.info("New veterinarian with id=" + customer.getId() + " created");
        return new VeterinarianDto(customer);
    }

    private void validateUniqueFields(VeterinarianForm form) {
        validateUniqueFields(0L, form);
    }

    private void validateUniqueFields(Long existingCustomerId, VeterinarianForm customerForm) {
        ArrayList<String> errorMessages = new ArrayList<>();

        String formEmail = customerForm.getEmail();
        String formCpf = customerForm.getCpf();
        String formCrmv = customerForm.getCrmv();
        String formPhone = customerForm.getPhone();

        if (veterinarianRepository.existsByIdNotAndEmail(existingCustomerId, formEmail))
            errorMessages.add("Email already registered");

        if (veterinarianRepository.existsByIdNotAndCpf(existingCustomerId, formCpf))
            errorMessages.add("Cpf already registered");

        if (veterinarianRepository.existsByIdNotAndCrmv(existingCustomerId, formCrmv))
            errorMessages.add("Crmv already registered");

        if(veterinarianRepository.existsByIdNotAndPhone(existingCustomerId, formPhone))
            errorMessages.add("Phone already registered");

        if (!errorMessages.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errorMessages.toString());
    }

    public void update(Long id, VeterinarianForm form) {
        logger.info("Updating veterinarian with id=" + id);

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian with id " + id + " not found!");
                });

        validateUniqueFields(id, form);
        veterinarianMapper.merge(veterinarian, form);
        veterinarianRepository.save(veterinarian);
        logger.info("Veterinarian with id=" + id + " updated");
    }

    public void delete(Long id) {
        logger.info("Deleting veterinarian with id=" + id);

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian with id " + id + " not found!");
                });
        veterinarianRepository.delete(veterinarian);
        logger.info("Veterinarian with id=" + id + " deleted");
    }

}
