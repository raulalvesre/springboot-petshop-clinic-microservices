package br.com.raulalvesre.petshopcustomerservice.dtos;

import br.com.raulalvesre.petshopcustomerservice.models.Customer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private AddressDto address;
    private String phone;
    private LocalDate birthDate;
    private Set<PetDto> pets;
    private LocalDateTime creationDate;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.cpf = customer.getCpf();
        this.address = new AddressDto(customer);
        this.phone = customer.getPhone();
        this.birthDate = customer.getBirthDate();
        this.pets = customer.getPets().stream().map(PetDto::new).collect(Collectors.toSet());
        this.creationDate = customer.getCreationDate();
    }

}