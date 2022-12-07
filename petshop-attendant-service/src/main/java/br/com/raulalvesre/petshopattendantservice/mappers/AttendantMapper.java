package br.com.raulalvesre.petshopattendantservice.mappers;

import br.com.raulalvesre.petshopattendantservice.dtos.AttendantForm;
import br.com.raulalvesre.petshopattendantservice.models.Attendant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendantMapper {

    private final PasswordEncoder passwordEncoder;

    public Attendant toModel(AttendantForm attendantForm) {
        return Attendant.builder()
                .name(attendantForm.getName())
                .email(attendantForm.getName())
                .password(passwordEncoder.encode(attendantForm.getPassword()))
                .cpf(attendantForm.getCpf())
                .crmv(attendantForm.getCrmv())
                .state(attendantForm.getAddress().getState())
                .city(attendantForm.getAddress().getCity())
                .address(attendantForm.getAddress().getAddress())
                .neighbourhood(attendantForm.getAddress().getNeighbourhood())
                .complement(attendantForm.getAddress().getComplement())
                .number(attendantForm.getAddress().getNumber())
                .cep(attendantForm.getAddress().getCep())
                .phone(attendantForm.getPhone())
                .birthDate(attendantForm.getBirthDate())
                .build();
    }

    public void merge(Attendant attendant, AttendantForm attendantForm) {
        attendant.setName(attendantForm.getName());
        attendant.setEmail(attendantForm.getEmail());
        attendant.setPassword(passwordEncoder.encode(attendantForm.getPassword()));
        attendant.setCpf(attendantForm.getCpf());
        attendant.setCrmv(attendantForm.getCrmv());
        attendant.setState(attendantForm.getAddress().getState());
        attendant.setCity(attendantForm.getAddress().getCity());
        attendant.setAddress(attendantForm.getAddress().getAddress());
        attendant.setNeighbourhood(attendantForm.getAddress().getNeighbourhood());
        attendant.setComplement(attendantForm.getAddress().getComplement());
        attendant.setNumber(attendantForm.getAddress().getNumber());
        attendant.setCpf(attendantForm.getCpf());
        attendant.setPhone(attendantForm.getPhone());
        attendant.setBirthDate(attendantForm.getBirthDate());
    }

}
