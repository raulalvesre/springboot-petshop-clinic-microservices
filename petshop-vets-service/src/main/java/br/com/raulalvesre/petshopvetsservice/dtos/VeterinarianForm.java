package br.com.raulalvesre.petshopvetsservice.dtos;

import br.com.raulalvesre.petshopvetsservice.validation.AdultAge;
import br.com.raulalvesre.petshopvetsservice.validation.Email;
import br.com.raulalvesre.petshopvetsservice.validation.Telephone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class VeterinarianForm {

    @NotBlank
    private String name;

    @Email
    private String email;

    @CPF
    private String cpf;

    @NotEmpty
    private String crmv;

    @NotNull
    private AddressDto address;

    @Telephone
    private String phone;

    @NotNull
    @AdultAge(message = "Birth date not greater than 18 years")
    private LocalDate birthDate;

}
