package br.com.raulalvesre.petshopauthservice.dtos;

import br.com.raulalvesre.petshopauthservice.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String name;
    private String email;
    private String role;
    private String cpf;
    private String phone;
    private LocalDate birthDate;
    private AddressDto address;
    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRoleName();
        this.cpf = user.getCpf();
        this.phone = user.getPhone();
        this.birthDate = user.getBirthDate();
        this.address = new AddressDto(user);
    }

}
