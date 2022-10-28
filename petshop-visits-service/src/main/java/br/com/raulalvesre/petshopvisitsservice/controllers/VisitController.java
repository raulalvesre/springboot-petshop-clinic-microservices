package br.com.raulalvesre.petshopvisitsservice.controllers;

import br.com.raulalvesre.petshopvisitsservice.dtos.VisitDto;
import br.com.raulalvesre.petshopvisitsservice.dtos.VisitForm;
import br.com.raulalvesre.petshopvisitsservice.dtos.VisitMinifiedDto;
import br.com.raulalvesre.petshopvisitsservice.services.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @GetMapping("{id}")
    public ResponseEntity<VisitDto> getById(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(visitService.getById(id));
    }

    @GetMapping("/minified/{id}")
    public ResponseEntity<VisitMinifiedDto> getByIdMinified(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(visitService.getByIdMinified(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<VisitDto>> getPage(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(visitService.getPage(pageable));
    }

    private int parsePageNumber(Integer pageNumber) {
        return pageNumber < 0 ? 0 : pageNumber;
    }

    @GetMapping("/page/minified")
    public ResponseEntity<Page<VisitMinifiedDto>> getPageMinified(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(visitService.getPageMinified(pageable));
    }

    @PostMapping
    public ResponseEntity<VisitDto> create(@RequestBody @Valid VisitForm visitForm) {
        VisitDto customerDto = visitService.create(visitForm);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("8081")
                .pathSegment("visit", customerDto.getId().toString())
                .build();

        return ResponseEntity.created(uri.toUri()).body(customerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<VisitDto> update(@PathVariable("id") Long id,
                                           @RequestBody @Valid VisitForm visitForm) {
        visitService.update(id, visitForm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<VisitDto> delete(@PathVariable("id") Long id) {
        visitService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
