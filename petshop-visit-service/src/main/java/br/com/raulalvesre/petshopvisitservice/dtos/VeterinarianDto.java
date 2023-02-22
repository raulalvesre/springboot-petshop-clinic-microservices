package br.com.raulalvesre.petshopvisitservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VeterinarianDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String crmv;
    private AddressDto address;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime creationDate;

}