package br.com.raulalvesre.petshopvetsservice.mappers;

import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianForm;
import br.com.raulalvesre.petshopvetsservice.models.Veterinarian;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeterinarianMapper {

    private final PasswordEncoder passwordEncoder;

    public Veterinarian toModel(VeterinarianForm veterinarianForm) {
        return Veterinarian.builder()
                .name(veterinarianForm.getName())
                .email(veterinarianForm.getName())
                .password(passwordEncoder.encode(veterinarianForm.getPassword()))
                .cpf(veterinarianForm.getCpf())
                .crmv(veterinarianForm.getCrmv())
                .state(veterinarianForm.getAddress().getState())
                .city(veterinarianForm.getAddress().getCity())
                .address(veterinarianForm.getAddress().getAddress())
                .neighbourhood(veterinarianForm.getAddress().getNeighbourhood())
                .complement(veterinarianForm.getAddress().getComplement())
                .number(veterinarianForm.getAddress().getNumber())
                .cep(veterinarianForm.getAddress().getCep())
                .phone(veterinarianForm.getPhone())
                .birthDate(veterinarianForm.getBirthDate())
                .build();
    }

    public void merge(Veterinarian veterinarian, VeterinarianForm customerForm) {
        veterinarian.setName(customerForm.getName());
        veterinarian.setEmail(customerForm.getEmail());
        veterinarian.setPassword(passwordEncoder.encode(customerForm.getPassword()));
        veterinarian.setCpf(customerForm.getCpf());
        veterinarian.setCrmv(customerForm.getCrmv());
        veterinarian.setState(customerForm.getAddress().getState());
        veterinarian.setCity(customerForm.getAddress().getCity());
        veterinarian.setAddress(customerForm.getAddress().getAddress());
        veterinarian.setNeighbourhood(customerForm.getAddress().getNeighbourhood());
        veterinarian.setComplement(customerForm.getAddress().getComplement());
        veterinarian.setNumber(customerForm.getAddress().getNumber());
        veterinarian.setCpf(customerForm.getCpf());
        veterinarian.setPhone(customerForm.getPhone());
        veterinarian.setBirthDate(customerForm.getBirthDate());
    }

}
