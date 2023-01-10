package br.com.raulalvesre.petshopvisitsservice.dtos;

import br.com.raulalvesre.petshopvisitsservice.models.Visit;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VisitMinifiedDto implements Serializable {

    private Long id;
    private Long customerId;
    private Long veterinarianId;
    private String description;
    private String diagnostic;
    private LocalDateTime scheduledAt;

    public VisitMinifiedDto(Visit visit) {
        this.id = visit.getId();
        this.customerId = visit.getCustomerId();
        this.veterinarianId = visit.getVeterinarianId();
        this.description = visit.getDescription();
        this.diagnostic = visit.getDiagnostic();
        this.scheduledAt = visit.getScheduledAt();
    }

    public VisitMinifiedDto(VisitDto visit) {
        this.id = visit.getId();
        this.customerId = visit.getCustomer().getId();
        this.veterinarianId = visit.getVeterinarian().getId();
        this.description = visit.getDescription();
        this.diagnostic = visit.getDiagnostic();
        this.scheduledAt = visit.getScheduledAt();
    }

}