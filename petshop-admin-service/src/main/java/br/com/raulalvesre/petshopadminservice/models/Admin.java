package br.com.raulalvesre.petshopadminservice.models;

import br.com.raulalvesre.petshopadminservice.enums.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Admin {

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

    public Admin(Long id,
                 String name,
                 String email,
                 String password,
                 String cpf,
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
