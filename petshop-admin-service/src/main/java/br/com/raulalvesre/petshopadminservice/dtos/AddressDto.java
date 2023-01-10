package br.com.raulalvesre.petshopadminservice.dtos;

import br.com.raulalvesre.petshopadminservice.enums.State;
import br.com.raulalvesre.petshopadminservice.models.Admin;
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

    public AddressDto(Admin admin) {
        this.state = admin.getState();
        this.city = admin.getCity();
        this.address = admin.getAddress();
        this.neighbourhood = admin.getNeighbourhood();
        this.complement = admin.getComplement();
        this.number = admin.getNumber();
        this.cep = admin.getCep();
    }

}
