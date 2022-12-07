package br.com.raulalvesre.petshopcustomerservice.dtos;

import br.com.raulalvesre.petshopcustomerservice.models.Pet;
import br.com.raulalvesre.petshopcustomerservice.validation.AdultAge;
import br.com.raulalvesre.petshopcustomerservice.validation.Email;
import br.com.raulalvesre.petshopcustomerservice.validation.Telephone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CustomerForm {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 8, message = "Password must have at least eight digits")
    private String password;

    @CPF
    private String cpf;

    @NotNull
    private AddressDto address;

    @Telephone
    private String phone;

    @NotNull
    @AdultAge(message = "Birth date must be greater than 18 years")
    private LocalDate birthDate;

    @NotNull(message = "Pet list should not be empty")
    @Size(min = 1)
    private Set<PetForm> pets;

}
