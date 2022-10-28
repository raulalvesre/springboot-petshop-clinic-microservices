package br.com.raulalvesre.petshopcustomerservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    private final List<String> details;

    public UnprocessableEntityException(List<String> details) {
        super();
        this.details = details;
    }

}