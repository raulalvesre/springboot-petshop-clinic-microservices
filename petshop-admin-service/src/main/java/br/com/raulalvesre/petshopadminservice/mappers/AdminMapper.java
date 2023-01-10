package br.com.raulalvesre.petshopadminservice.mappers;

import br.com.raulalvesre.petshopadminservice.dtos.AdminForm;
import br.com.raulalvesre.petshopadminservice.models.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    private final PasswordEncoder passwordEncoder;

    public Admin toModel(AdminForm AdminForm) {
        return Admin.builder()
                .name(AdminForm.getName())
                .email(AdminForm.getName())
                .password(passwordEncoder.encode(AdminForm.getPassword()))
                .cpf(AdminForm.getCpf())
                .state(AdminForm.getAddress().getState())
                .city(AdminForm.getAddress().getCity())
                .address(AdminForm.getAddress().getAddress())
                .neighbourhood(AdminForm.getAddress().getNeighbourhood())
                .complement(AdminForm.getAddress().getComplement())
                .number(AdminForm.getAddress().getNumber())
                .cep(AdminForm.getAddress().getCep())
                .phone(AdminForm.getPhone())
                .birthDate(AdminForm.getBirthDate())
                .build();
    }

    public void merge(Admin Admin, AdminForm AdminForm) {
        Admin.setName(AdminForm.getName());
        Admin.setEmail(AdminForm.getEmail());
        Admin.setPassword(passwordEncoder.encode(AdminForm.getPassword()));
        Admin.setCpf(AdminForm.getCpf());
        Admin.setState(AdminForm.getAddress().getState());
        Admin.setCity(AdminForm.getAddress().getCity());
        Admin.setAddress(AdminForm.getAddress().getAddress());
        Admin.setNeighbourhood(AdminForm.getAddress().getNeighbourhood());
        Admin.setComplement(AdminForm.getAddress().getComplement());
        Admin.setNumber(AdminForm.getAddress().getNumber());
        Admin.setCpf(AdminForm.getCpf());
        Admin.setPhone(AdminForm.getPhone());
        Admin.setBirthDate(AdminForm.getBirthDate());
    }

}
