package br.com.raulalvesre.petshopcustomerservice.dtos;

import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAuthDto {

    private String email;
    private String password;

    public CustomerAuthDto(Customer customer) {
        this.email = customer.getEmail();
        this.password = customer.getPassword();
    }

}
