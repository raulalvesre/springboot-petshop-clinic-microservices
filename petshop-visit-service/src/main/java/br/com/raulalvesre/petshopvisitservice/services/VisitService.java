package br.com.raulalvesre.petshopvisitservice.services;

import br.com.raulalvesre.petshopvisitservice.dtos.*;
import br.com.raulalvesre.petshopvisitservice.models.Visit;
import br.com.raulalvesre.petshopvisitservice.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final CustomerService customerService;
    private final VeterinarianService veterinarianService;
    Logger logger = LoggerFactory.getLogger(VeterinarianService.class);

    public VisitDto getById(Long id) {
        logger.info("Getting visit with id=" + id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Visit with id " + id + " not found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit with id " + id + " not found!");
                });

        Mono<CustomerDto> customerMono = customerService.getById(visit.getCustomerId());
        Mono<VeterinarianDto> veterinarianMono = veterinarianService.getById(visit.getVeterinarianId());
        Tuple2<CustomerDto, VeterinarianDto> resultTuple = Mono.zip(customerMono, veterinarianMono).block();


        VisitDto visitDto = new VisitDto(visit, resultTuple.getT1(), resultTuple.getT2());

        return visitDto;
    }

    public VisitMinifiedDto getByIdMinified(Long id) {
        logger.info("Getting visit with id=" + id + " (minified)");
        VisitMinifiedDto visitMinifiedDto = visitRepository.findById(id)
                .map(VisitMinifiedDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit with id " + id + " not found!"));


        return visitMinifiedDto;
    }

    public Page<VisitDto> getPage(Pageable pageable) {
        logger.info("Getting visits page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize());

        Page<Visit> visitPage = visitRepository.findAll(pageable);

        Set<Long> customersIds = visitPage.getContent().stream()
                .map(Visit::getCustomerId)
                .collect(Collectors.toSet());

        Set<Long> veterinariansIds = visitPage.getContent().stream()
                .map(Visit::getVeterinarianId)
                .collect(Collectors.toSet());

        Mono<Set<CustomerDto>> customerMono = customerService.getByIdIn(customersIds);
        Mono<Set<VeterinarianDto>> veterinarianMono = veterinarianService.getByIdIn(veterinariansIds);
        Tuple2<Set<CustomerDto>, Set<VeterinarianDto>> resultTuple = Mono.zip(customerMono, veterinarianMono).block();

        Map<Long, CustomerDto> customers = resultTuple.getT1().stream()
                .collect(Collectors.toMap(CustomerDto::getId, Function.identity()));

        Map<Long, VeterinarianDto> veterinarians = resultTuple.getT2().stream()
                .collect(Collectors.toMap(VeterinarianDto::getId, Function.identity()));

        return visitPage
                .map(x -> new VisitDto(x, customers.get(x.getCustomerId()), veterinarians.get(x.getVeterinarianId())));
    }

    public Page<VisitMinifiedDto> getPageMinified(Pageable pageable) {
        logger.info("Getting veterinarian page=" + pageable.getPageNumber() + " with size=" + pageable.getPageSize() + " (minified)");

        return visitRepository.findAll(pageable)
                .map(VisitMinifiedDto::new);
    }
    
    public VisitDto create(VisitForm form) {
        logger.info("Creating visit");
        Mono<CustomerDto> customerMono = customerService.getById(form.getCustomerId());
        Mono<VeterinarianDto> veterinarianMono = veterinarianService.getById(form.getVeterinarianId());
        Tuple2<CustomerDto, VeterinarianDto> resultTuple = Mono.zip(customerMono, veterinarianMono).block();

        Visit visit = new Visit(form);
        visitRepository.save(visit);
        logger.info("Visit with id=" + visit.getId() + " created");
        return new VisitDto(visit, resultTuple.getT1(), resultTuple.getT2());
    }


    public void update(Long id, VisitForm form) {
        logger.info("Updating visit with id=" + id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Visit with id=" + id + " not found");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit with id " + id + " not found!");
                });

        visit.merge(form);
        visitRepository.save(visit);
        logger.info("Visit with id=" + id + " updated");
    }

    public void updateDiagnostic(Long id, String diagnostic) {
        logger.info("Updating diagnostic of visit with id=" + id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Visit with id=" + id + " not found");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit with id " + id + " not found!");
                });

        visit.setDiagnostic(diagnostic);
        logger.info("Diagnostic of visit with id=" + id + " updated");
    }

    public void delete(Long id) {
        logger.info("Deleting visit with id=" + id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Visit with id=" + id + " not found");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit with id " + id + " not found!");
                });

        visitRepository.delete(visit);
        logger.info("Visit with id=" + id + " deleted");

    }

}
