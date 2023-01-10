package br.com.raulalvesre.petshopvisitsservice.dtos;

import br.com.raulalvesre.petshopvisitsservice.enums.State;
import br.com.raulalvesre.petshopvisitsservice.models.Visit;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
