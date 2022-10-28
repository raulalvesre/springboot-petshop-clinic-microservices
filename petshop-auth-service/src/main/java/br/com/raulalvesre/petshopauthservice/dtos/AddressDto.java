package br.com.raulalvesre.petshopauthservice.dtos;

import br.com.raulalvesre.petshopauthservice.enums.State;
import br.com.raulalvesre.petshopauthservice.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {

    @NotBlank
    private State state;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @NotBlank
    private String number;
    private String complement;
    private String neighborhood;
    @NotBlank
    private String postalCode;

    public AddressDto(User user) {
        this.address = user.getAddress();
        this.number = user.getNumber();
        this.city = user.getCity();
        this.complement = user.getComplement();
        this.neighborhood = user.getNeighbourhood();
        this.state = user.getState();
        this.postalCode = user.getPostalCode();
    }
}
