package br.com.raulalvesre.petshopvetsservice.dtos;

import br.com.raulalvesre.petshopvetsservice.models.Veterinarian;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VeterinarianDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String crmv;
    private AddressDto address;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime creationDate;

    public VeterinarianDto(Veterinarian veterinarian) {
        this.id = veterinarian.getId();
        this.name = veterinarian.getName();
        this.email = veterinarian.getEmail();
        this.cpf = veterinarian.getCpf();
        this.crmv = veterinarian.getCrmv();
        this.address = new AddressDto(veterinarian);
        this.phone = veterinarian.getPhone();
        this.birthDate = veterinarian.getBirthDate();
        this.creationDate = veterinarian.getCreationDate();
    }

}