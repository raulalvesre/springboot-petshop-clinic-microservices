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

    public Admin toModel(AdminForm adminForm) {
        return Admin.builder()
                .name(adminForm.getName())
                .email(adminForm.getEmail())
                .password(passwordEncoder.encode(adminForm.getPassword()))
                .cpf(adminForm.getCpf())
                .state(adminForm.getAddress().getState())
                .city(adminForm.getAddress().getCity())
                .address(adminForm.getAddress().getAddress())
                .neighbourhood(adminForm.getAddress().getNeighbourhood())
                .complement(adminForm.getAddress().getComplement())
                .number(adminForm.getAddress().getNumber())
                .cep(adminForm.getAddress().getCep())
                .phone(adminForm.getPhone())
                .birthDate(adminForm.getBirthDate())
                .build();
    }

    public void merge(Admin admin, AdminForm adminForm) {
        admin.setName(adminForm.getName());
        admin.setEmail(adminForm.getEmail());
        admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
        admin.setCpf(adminForm.getCpf());
        admin.setState(adminForm.getAddress().getState());
        admin.setCity(adminForm.getAddress().getCity());
        admin.setAddress(adminForm.getAddress().getAddress());
        admin.setNeighbourhood(adminForm.getAddress().getNeighbourhood());
        admin.setComplement(adminForm.getAddress().getComplement());
        admin.setNumber(adminForm.getAddress().getNumber());
        admin.setCpf(adminForm.getCpf());
        admin.setPhone(adminForm.getPhone());
        admin.setBirthDate(adminForm.getBirthDate());
    }

}
