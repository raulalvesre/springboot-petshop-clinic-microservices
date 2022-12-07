package br.com.raulalvesre.petshopcustomerservice.controllers;

import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerAuthDto;
import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerForm;
import br.com.raulalvesre.petshopcustomerservice.services.CustomerService;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerAuthDto> getByEmail(@RequestParam("email") String email) throws Throwable {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CustomerDto>> getPage(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(customerService.getPage(pageable));
    }

    private int parsePageNumber(Integer pageNumber) {
        return pageNumber < 0 ? 0 : pageNumber;
    }

    @GetMapping("/pageByIdInList")
    public ResponseEntity<Set<CustomerDto>> getByIdIn(@RequestParam("idList") Collection<Long> idList) {
        return ResponseEntity.ok(customerService.getByIdIn(idList));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody @Valid CustomerForm customerForm) {
        CustomerDto customerDto = customerService.create(customerForm);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("8081")
                .pathSegment("customer", customerDto.getId().toString())
                .build();

        return ResponseEntity.created(uri.toUri()).body(customerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable("id") Long id,
                                              @RequestBody @Valid CustomerForm customerForm) {
        customerService.update(id, customerForm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomerDto> delete(@PathVariable("id") Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
