package br.com.raulalvesre.petshopattendantservice.dtos;

import br.com.raulalvesre.petshopattendantservice.models.Attendant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendantAuthDto {

    private String email;
    private String password;

    public AttendantAuthDto(Attendant veterinarian) {
        this.email = veterinarian.getEmail();
        this.password = veterinarian.getPassword();
    }

}
