package br.com.raulalvesre.petshopadminservice.dtos;

import br.com.raulalvesre.petshopadminservice.validation.AdultAge;
import br.com.raulalvesre.petshopadminservice.validation.Email;
import br.com.raulalvesre.petshopadminservice.validation.Telephone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AdminForm {

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
    @AdultAge(message = "Birth date not greater than 18 years")
    private LocalDate birthDate;

}
