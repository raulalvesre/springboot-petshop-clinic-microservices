package br.com.raulalvesre.petshopvisitservice.dtos;

import br.com.raulalvesre.petshopvisitservice.enums.State;
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


}
