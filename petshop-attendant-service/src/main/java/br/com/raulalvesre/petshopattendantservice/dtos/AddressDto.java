package br.com.raulalvesre.petshopattendantservice.dtos;

import br.com.raulalvesre.petshopattendantservice.enums.State;
import br.com.raulalvesre.petshopattendantservice.models.Attendant;
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

    public AddressDto(Attendant attendant) {
        this.state = attendant.getState();
        this.city = attendant.getCity();
        this.address = attendant.getAddress();
        this.neighbourhood = attendant.getNeighbourhood();
        this.complement = attendant.getComplement();
        this.number = attendant.getNumber();
        this.cep = attendant.getCep();
    }

}
