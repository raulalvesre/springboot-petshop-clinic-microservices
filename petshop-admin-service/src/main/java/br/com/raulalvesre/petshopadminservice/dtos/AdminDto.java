package br.com.raulalvesre.petshopadminservice.dtos;

import br.com.raulalvesre.petshopadminservice.models.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AdminDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private AddressDto address;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime creationDate;

    public AdminDto(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.cpf = admin.getCpf();
        this.address = new AddressDto(admin);
        this.phone = admin.getPhone();
        this.birthDate = admin.getBirthDate();
        this.creationDate = admin.getCreationDate();
    }

}