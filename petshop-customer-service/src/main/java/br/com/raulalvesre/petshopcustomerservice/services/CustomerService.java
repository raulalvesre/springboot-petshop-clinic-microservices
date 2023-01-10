package br.com.raulalvesre.petshopcustomerservice.services;

import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerAuthDto;
import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerForm;
import br.com.raulalvesre.petshopcustomerservice.mappers.CustomerMapper;
import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import br.com.raulalvesre.petshopcustomerservice.models.Pet;
import br.com.raulalvesre.petshopcustomerservice.repositories.CustomerRepository;
import br.com.raulalvesre.petshopcustomerservice.repositories.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetTypeRepository petTypeRepository;
    private final CustomerMapper customerMapper;


    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerDto getById(Long id) {
        logger.info("Getting customer with id=" + id);
        return customerRepository.findById(id)
                .map(CustomerDto::new)
                .orElseThrow(() ->  {
                    logger.info("Customer with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found!");
                });
    }

    public CustomerAuthDto getByEmail(String email) {
        logger.info("Getting customer with email=" + email);
        return customerRepository.findByEmail(email)
                .map(CustomerAuthDto::new)
                .orElseThrow(() ->  {
                    logger.info("Customer with email " + email + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with email " + email + " not found!");
                });
    }

    public Page<CustomerDto> getPage(Pageable pageable) {
        logger.info("Getting customer page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize());
        return customerRepository.findAll(pageable)
                .map(CustomerDto::new);
    }

    public Set<CustomerDto> getByIdIn(Collection<Long> idList) {
        logger.info("Getting customers with id in " + idList);
        return customerRepository.findByIdIn(idList).stream()
                .map(CustomerDto::new)
                .collect(Collectors.toSet());
    }

    @Transactional
    public CustomerDto create(CustomerForm form) {
        validateUniqueFields(form);
        logger.info("Creating customer");
        Customer customer = customerMapper.toModel(form);
        Set<Pet> pets = form.getPets().stream()
                .map(x -> new Pet(x, petTypeRepository.getReferenceById(x.getType_id()), customer))
                .collect(Collectors.toSet());
        customer.setPets(pets);
        customerRepository.save(customer);
        logger.info("New customer with id=" + customer.getId() + " created");
        return new CustomerDto(customer);
    }

    private void validateUniqueFields(CustomerForm form) {
        validateUniqueFields(0L, form);
    }

    private void validateUniqueFields(Long existingCustomerId, CustomerForm customerForm) {
        ArrayList<String> errorMessages = new ArrayList<>();

        String formEmail = customerForm.getEmail();
        String formCpf = customerForm.getCpf();
        String formPhone = customerForm.getPhone();

        if (customerRepository.existsByIdNotAndEmail(existingCustomerId, formEmail))
            errorMessages.add("Email already registered");

        if (customerRepository.existsByIdNotAndCpf(existingCustomerId, formCpf))
            errorMessages.add("Cpf already registered");

        if(customerRepository.existsByIdNotAndPhone(existingCustomerId, formPhone))
            errorMessages.add("Phone already registered");

        if (!errorMessages.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errorMessages.toString());
    }

    @Transactional
    public void update(Long id, CustomerForm form) {
        logger.info("Updating customer with id=" + id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Customer with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found!");
                });

        validateUniqueFields(id, form);

        Set<Pet> pets = form.getPets().stream()
                .map(x -> new Pet(x, petTypeRepository.getReferenceById(x.getType_id()), customer))
                .collect(Collectors.toSet());

        customerMapper.merge(customer, form, pets);
        customerRepository.save(customer);
        logger.info("Customer with id=" + id + " updated");
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting customer with id=" + id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Customer with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found!");
                });
        customerRepository.delete(customer);
        logger.info("Customer with id=" + id + " deleted");
    }

}
