package br.com.raulalvesre.petshopvetsservice.models;

import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianForm;
import br.com.raulalvesre.petshopvetsservice.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String cpf;

    @NotEmpty
    private String crmv;

    @Enumerated(EnumType.STRING)
    private State state;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    private String neighbourhood;
    private String complement;
    private String number;

    @NotEmpty
    private String cep;

    @NotEmpty
    @Size(max = 15)
    private String phone;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();

    public Veterinarian(VeterinarianForm customerForm) {
        this.name = customerForm.getName();
        this.email = customerForm.getEmail();
        this.cpf = customerForm.getCpf();
        this.crmv = customerForm.getCrmv();
        this.state = customerForm.getAddress().getState();;
        this.city = customerForm.getAddress().getCity();
        this.address = customerForm.getAddress().getAddress();
        this.neighbourhood = customerForm.getAddress().getNeighbourhood();
        this.complement = customerForm.getAddress().getComplement();
        this.number = customerForm.getAddress().getNumber();
        this.cep = customerForm.getCpf();
        this.phone = customerForm.getPhone();
        this.birthDate = customerForm.getBirthDate();
    }

    public void merge(VeterinarianForm customerForm) {
        this.name = customerForm.getName();
        this.email = customerForm.getEmail();
        this.cpf = customerForm.getCpf();
        this.crmv = customerForm.getCrmv();
        this.state = customerForm.getAddress().getState();;
        this.city = customerForm.getAddress().getCity();
        this.address = customerForm.getAddress().getAddress();
        this.neighbourhood = customerForm.getAddress().getNeighbourhood();
        this.complement = customerForm.getAddress().getComplement();
        this.number = customerForm.getAddress().getNumber();
        this.cep = customerForm.getCpf();
        this.phone = customerForm.getPhone();
        this.birthDate = customerForm.getBirthDate();
    }

}
