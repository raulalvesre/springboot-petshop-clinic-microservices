package br.com.raulalvesre.petshopadminservice.dtos;

import br.com.raulalvesre.petshopadminservice.models.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminAuthDto {

    private String email;
    private String password;

    public AdminAuthDto(Admin admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
    }

}
