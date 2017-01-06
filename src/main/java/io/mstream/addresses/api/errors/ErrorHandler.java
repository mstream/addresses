package io.mstream.addresses.api.errors;

import io.mstream.addresses.api.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;


@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Set<Error>> validationException(ValidationException e) {
        Set<Error> errors = e.getViolations()
                .stream()
                .map(violation -> new Error("VALIDATION", violation))
                .collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(errors);
    }
}
