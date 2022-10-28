package br.com.raulalvesre.petshopcustomerservice.dtos;

import br.com.raulalvesre.petshopcustomerservice.enums.State;
import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    public AddressDto(Customer customer) {
        this.state = customer.getState();
        this.city = customer.getCity();
        this.address = customer.getAddress();
        this.neighbourhood = customer.getNeighbourhood();
        this.complement = customer.getComplement();
        this.number = customer.getNumber();
        this.cep = customer.getCep();
    }

}
