package br.com.raulalvesre.petshopcustomerservice.exceptions;

import br.com.raulalvesre.petshopcustomerservice.dtos.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex,
                                                                WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Resource Not Found", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public final ResponseEntity<Object> handleUnprocessableEntityException(UnprocessableEntityException ex,
                                                                WebRequest request) {
        List<String> details = ex.getDetails();
        ErrorResponse error = new ErrorResponse("Entity is not processable", details);
        return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders header,
                                                                  HttpStatus status,
                                                                  WebRequest webRequest) {
        List<String> details = ex.getBindingResult().getAllErrors().stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse("Validation Failed", details);

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
