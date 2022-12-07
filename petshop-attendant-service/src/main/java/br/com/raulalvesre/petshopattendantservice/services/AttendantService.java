package br.com.raulalvesre.petshopattendantservice.services;

import br.com.raulalvesre.petshopattendantservice.dtos.AttendantAuthDto;
import br.com.raulalvesre.petshopattendantservice.dtos.AttendantDto;
import br.com.raulalvesre.petshopattendantservice.dtos.AttendantForm;
import br.com.raulalvesre.petshopattendantservice.mappers.AttendantMapper;
import br.com.raulalvesre.petshopattendantservice.models.Attendant;
import br.com.raulalvesre.petshopattendantservice.repositories.AttendantRepository;
import br.com.raulalvesre.petshopvetsservice.exceptions.NotFoundException;
import br.com.raulalvesre.petshopvetsservice.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendantService {

    private final AttendantRepository attendantRepository;
    private final AttendantMapper attendantMapper;
    Logger logger = LoggerFactory.getLogger(AttendantService.class);

    public AttendantDto getById(Long id) {
        logger.info("Getting veterinarian with id=" + id);
        return attendantRepository.findById(id)
                .map(AttendantDto::new)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new NotFoundException("Veterinarian with id " + id + " not found!");
                });
    }

    public AttendantAuthDto getByEmail(String email) {
        logger.info("Getting veterinarian with email=" + email);
        return attendantRepository.findByEmail(email)
                .map(AttendantAuthDto::new)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with email " + email + " not found!");
                    return new NotFoundException("Veterinarian with email " + email + " not found!");
                });
    }

    public Page<AttendantDto> getPage(Pageable pageable) {
        logger.info("Getting veterinarian page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize());
        return attendantRepository.findAll(pageable)
                .map(AttendantDto::new);
    }

    public Set<AttendantDto> getByIdIn(Collection<Long> idCollection) {
        logger.info("Getting veterinarians with id in " + idCollection);
        return attendantRepository.findByIdIn(idCollection).stream()
                .map(AttendantDto::new)
                .collect(Collectors.toSet());
    }
    
    public AttendantDto create(AttendantForm form) {
        validateUniqueFields(form);
        logger.info("Creating customer");
        Attendant customer = attendantMapper.toModel(form);
        attendantRepository.save(customer);
        logger.info("New veterinarian with id=" + customer.getId() + " created");
        return new AttendantDto(customer);
    }

    private void validateUniqueFields(AttendantForm form) {
        validateUniqueFields(0L, form);
    }

    private void validateUniqueFields(Long existingCustomerId, AttendantForm customerForm) {
        ArrayList<String> errorMessages = new ArrayList<>();

        String formEmail = customerForm.getEmail();
        String formCpf = customerForm.getCpf();
        String formCrmv = customerForm.getCrmv();
        String formPhone = customerForm.getPhone();

        if (attendantRepository.existsByIdNotAndEmail(existingCustomerId, formEmail))
            errorMessages.add("Email already registered");

        if (attendantRepository.existsByIdNotAndCpf(existingCustomerId, formCpf))
            errorMessages.add("Cpf already registered");

        if (attendantRepository.existsByIdNotAndCrmv(existingCustomerId, formCrmv))
            errorMessages.add("Crmv already registered");

        if(attendantRepository.existsByIdNotAndPhone(existingCustomerId, formPhone))
            errorMessages.add("Phone already registered");

        if (!errorMessages.isEmpty())
            throw new UnprocessableEntityException(errorMessages);
    }

    public void update(Long id, AttendantForm form) {
        logger.info("Updating veterinarian with id=" + id);

        Attendant veterinarian = attendantRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new NotFoundException("Veterinarian with id " + id + " not found!");
                });

        validateUniqueFields(id, form);
        attendantMapper.merge(veterinarian, form);
        attendantRepository.save(veterinarian);
        logger.info("Veterinarian with id=" + id + " updated");
    }

    public void delete(Long id) {
        logger.info("Deleting veterinarian with id=" + id);

        Attendant veterinarian = attendantRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Veterinarian with id " + id + " not found!");
                    return new NotFoundException("Veterinarian with id " + id + " not found!");
                });
        attendantRepository.delete(veterinarian);
        logger.info("Veterinarian with id=" + id + " deleted");
    }

}
