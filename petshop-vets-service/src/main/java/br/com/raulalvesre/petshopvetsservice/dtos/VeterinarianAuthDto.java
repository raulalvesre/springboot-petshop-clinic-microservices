package br.com.raulalvesre.petshopvetsservice.dtos;

import br.com.raulalvesre.petshopvetsservice.models.Veterinarian;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VeterinarianAuthDto {

    private String email;
    private String password;

    public VeterinarianAuthDto(Veterinarian veterinarian) {
        this.email = veterinarian.getEmail();
        this.password = veterinarian.getPassword();
    }

}
