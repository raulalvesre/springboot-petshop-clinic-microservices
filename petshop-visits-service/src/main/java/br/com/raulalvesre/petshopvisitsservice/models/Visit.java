package br.com.raulalvesre.petshopvisitsservice.models;

import br.com.raulalvesre.petshopvisitsservice.dtos.VisitForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private Long veterinarianId;

    @NotEmpty
    private String description;

    private String diagnostic;
    private LocalDateTime scheduledAt;
    private LocalDateTime creationDate = LocalDateTime.now();

    public Visit(VisitForm form) {
        this.customerId = form.getCustomerId();
        this.veterinarianId = form.getVeterinarianId();
        this.description = form.getDescription();
        this.diagnostic = form.getDiagnostic();
        this.scheduledAt = form.getScheduledAt();
    }

    public void merge(VisitForm form) {
        this.customerId = form.getCustomerId();
        this.veterinarianId = form.getVeterinarianId();
        this.description = form.getDescription();
        this.diagnostic = form.getDiagnostic();
        this.scheduledAt = form.getScheduledAt();
    }

}
