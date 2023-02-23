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

    public Admin toModel(AdminForm form) {
        return Admin.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .cpf(form.getCpf())
                .state(form.getAddress().getState())
                .city(form.getAddress().getCity())
                .address(form.getAddress().getAddress())
                .neighbourhood(form.getAddress().getNeighbourhood())
                .complement(form.getAddress().getComplement())
                .number(form.getAddress().getNumber())
                .cep(form.getAddress().getCep())
                .phone(form.getPhone())
                .birthDate(form.getBirthDate())
                .build();
    }

    public void merge(Admin admin, AdminForm form) {
        admin.setName(form.getName());
        admin.setEmail(form.getEmail());
        admin.setPassword(passwordEncoder.encode(form.getPassword()));
        admin.setCpf(form.getCpf());
        admin.setState(form.getAddress().getState());
        admin.setCity(form.getAddress().getCity());
        admin.setAddress(form.getAddress().getAddress());
        admin.setNeighbourhood(form.getAddress().getNeighbourhood());
        admin.setComplement(form.getAddress().getComplement());
        admin.setNumber(form.getAddress().getNumber());
        admin.setCpf(form.getCpf());
        admin.setPhone(form.getPhone());
        admin.setBirthDate(form.getBirthDate());
    }

}
