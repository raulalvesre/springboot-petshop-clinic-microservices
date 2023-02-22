package br.com.raulalvesre.petshopvetsservice.dtos;

import br.com.raulalvesre.petshopvetsservice.enums.State;
import br.com.raulalvesre.petshopvetsservice.models.Veterinarian;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {

    @NotEmpty
    private State state;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    @NotEmpty
    private String neighbourhood;
    private String complement;

    @NotEmpty
    private String number;

    @NotEmpty
    private String cep;

    public AddressDto(Veterinarian veterinarian) {
        this.state = veterinarian.getState();
        this.city = veterinarian.getCity();
        this.address = veterinarian.getAddress();
        this.neighbourhood = veterinarian.getNeighbourhood();
        this.complement = veterinarian.getComplement();
        this.number = veterinarian.getNumber();
        this.cep = veterinarian.getCep();
    }

}
