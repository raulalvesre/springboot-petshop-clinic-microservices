package br.com.raulalvesre.petshopattendantservice.controllers;

import br.com.raulalvesre.petshopattendantservice.dtos.AttendantAuthDto;
import br.com.raulalvesre.petshopattendantservice.dtos.AttendantDto;
import br.com.raulalvesre.petshopattendantservice.dtos.AttendantForm;
import br.com.raulalvesre.petshopattendantservice.services.AttendantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/attendant")
@RequiredArgsConstructor
public class AttendantController {

    private final AttendantService attendantService;

    @GetMapping("{id}")
    public ResponseEntity<AttendantDto> getById(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(attendantService.getById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<AttendantAuthDto> getByEmail(@RequestParam("email") String email) throws Throwable {
        return ResponseEntity.ok(attendantService.getByEmail(email));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AttendantDto>> getPage(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(attendantService.getPage(pageable));
    }

    private int parsePageNumber(Integer pageNumber) {
        return pageNumber < 0 ? 0 : pageNumber;
    }

    @GetMapping("/pageByIdInList")
    public ResponseEntity<Set<AttendantDto>> getByIdIn(@RequestParam("idList") Collection<Long> idList) {
        return ResponseEntity.ok(attendantService.getByIdIn(idList));
    }

    @PostMapping
    public ResponseEntity<AttendantDto> create(@RequestBody @Valid AttendantForm attendantForm) {
        AttendantDto customerDto = attendantService.create(attendantForm);
        //TODO fix this in everybody
        UriComponents uri = UriComponentsBuilder.newInstance()
                .pathSegment("attendant", customerDto.getId().toString())
                .build();

        return ResponseEntity.created(uri.toUri()).body(customerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @RequestBody @Valid AttendantForm attendantForm) {
        attendantService.update(id, attendantForm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AttendantDto> delete(@PathVariable("id") Long id) {
        attendantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
