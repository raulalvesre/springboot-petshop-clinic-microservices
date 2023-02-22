package br.com.raulalvesre.petshopvetsservice.models;

import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianForm;
import br.com.raulalvesre.petshopvetsservice.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
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
    private String password;

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
    @CreatedDate
    private LocalDateTime creationDate = LocalDateTime.now();

    public Veterinarian(Long id,
                        String name,
                        String email,
                        String password,
                        String cpf,
                        String crmv,
                        State state,
                        String city,
                        String address,
                        String neighbourhood,
                        String complement,
                        String number,
                        String cep,
                        String phone,
                        LocalDate birthDate,
                        LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.crmv = crmv;
        this.state = state;
        this.city = city;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.complement = complement;
        this.number = number;
        this.cep = cep;
        this.phone = phone;
        this.birthDate = birthDate;
        this.creationDate = creationDate == null ? LocalDateTime.now() : creationDate;
    }

}
