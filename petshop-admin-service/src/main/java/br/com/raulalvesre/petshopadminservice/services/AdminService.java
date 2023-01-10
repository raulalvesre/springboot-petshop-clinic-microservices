package br.com.raulalvesre.petshopadminservice.services;

import br.com.raulalvesre.petshopadminservice.dtos.AdminAuthDto;
import br.com.raulalvesre.petshopadminservice.dtos.AdminDto;
import br.com.raulalvesre.petshopadminservice.dtos.AdminForm;
import br.com.raulalvesre.petshopadminservice.mappers.AdminMapper;
import br.com.raulalvesre.petshopadminservice.models.Admin;
import br.com.raulalvesre.petshopadminservice.repositories.AdminRepository;
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
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminDto getById(Long id) {
        logger.info("Getting Admin with id=" + id);
        return adminRepository.findById(id)
                .map(AdminDto::new)
                .orElseThrow(() -> {
                    logger.info("Admin with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with id " + id + " not found!");
                });
    }

    public AdminAuthDto getByEmail(String email) {
        logger.info("Getting Admin with email=" + email);
        return adminRepository.findByEmail(email)
                .map(AdminAuthDto::new)
                .orElseThrow(() -> {
                    logger.info("Admin with email " + email + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with email " + email + " not found!");
                });
    }

    public Page<AdminDto> getPage(Pageable pageable) {
        logger.info("Getting Admin page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize());
        return adminRepository.findAll(pageable)
                .map(AdminDto::new);
    }

    public Set<AdminDto> getByIdIn(Collection<Long> idCollection) {
        logger.info("Getting Admins with id in " + idCollection);
        return adminRepository.findByIdIn(idCollection).stream()
                .map(AdminDto::new)
                .collect(Collectors.toSet());
    }
    
    public AdminDto create(AdminForm form) {
        validateUniqueFields(form);
        logger.info("Creating customer");
        Admin customer = adminMapper.toModel(form);
        adminRepository.save(customer);
        logger.info("New Admin with id=" + customer.getId() + " created");
        return new AdminDto(customer);
    }

    private void validateUniqueFields(AdminForm form) {
        validateUniqueFields(0L, form);
    }

    private void validateUniqueFields(Long existingCustomerId, AdminForm customerForm) {
        ArrayList<String> errorMessages = new ArrayList<>();

        String formEmail = customerForm.getEmail();
        String formCpf = customerForm.getCpf();
        String formPhone = customerForm.getPhone();

        if (adminRepository.existsByIdNotAndEmail(existingCustomerId, formEmail))
            errorMessages.add("Email already registered");

        if (adminRepository.existsByIdNotAndCpf(existingCustomerId, formCpf))
            errorMessages.add("Cpf already registered");

        if(adminRepository.existsByIdNotAndPhone(existingCustomerId, formPhone))
            errorMessages.add("Phone already registered");

        if (!errorMessages.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errorMessages.toString());
    }

    public void update(Long id, AdminForm form) {
        logger.info("Updating Admin with id=" + id);

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Admin with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with id " + id + " not found!");
                });

        validateUniqueFields(id, form);
        adminMapper.merge(admin, form);
        adminRepository.save(admin);
        logger.info("Admin with id=" + id + " updated");
    }

    public void delete(Long id) {
        logger.info("Deleting Admin with id=" + id);

        Admin Admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Admin with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with id " + id + " not found!");
                });
        adminRepository.delete(Admin);
        logger.info("Admin with id=" + id + " deleted");
    }

}
