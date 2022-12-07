package br.com.raulalvesre.petshopcustomerservice.mappers;

import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerForm;
import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import br.com.raulalvesre.petshopcustomerservice.models.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;

    public Customer toModel(CustomerForm customerForm) {
        return Customer.builder()
                .name(customerForm.getName())
                .email(customerForm.getEmail())
                .password(passwordEncoder.encode(customerForm.getPassword()))
                .cpf(customerForm.getCpf())
                .state(customerForm.getAddress().getState())
                .city(customerForm.getAddress().getCity())
                .address(customerForm.getAddress().getAddress())
                .neighbourhood(customerForm.getAddress().getNeighbourhood())
                .complement(customerForm.getAddress().getComplement())
                .number(customerForm.getAddress().getNumber())
                .cep(customerForm.getAddress().getCep())
                .phone(customerForm.getPhone())
                .birthDate(customerForm.getBirthDate())
                .build();
    }

    public void merge(Customer customer, CustomerForm customerForm, Collection<Pet> pets) {
        customer.setName(customerForm.getName());
        customer.setEmail(customerForm.getEmail());
        customer.setPassword(passwordEncoder.encode(customerForm.getPassword()));
        customer.setCpf(customerForm.getCpf());
        customer.setState(customerForm.getAddress().getState());
        customer.setCity(customerForm.getAddress().getCity());
        customer.setAddress(customerForm.getAddress().getAddress());
        customer.setNeighbourhood(customerForm.getAddress().getNeighbourhood());
        customer.setComplement(customerForm.getAddress().getComplement());
        customer.setNumber(customerForm.getAddress().getNumber());
        customer.setCpf(customerForm.getCpf());
        customer.setPhone(customerForm.getPhone());
        customer.setBirthDate(customerForm.getBirthDate());
        customer.setPets(pets);
    }

}
