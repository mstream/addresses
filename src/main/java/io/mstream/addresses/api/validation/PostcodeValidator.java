package io.mstream.addresses.api.validation;


import org.springframework.stereotype.Component;

@Component
public class PostcodeValidator implements Validator<String> {

    @Override
    public ValidationResult validate(String object) {
        return ValidationResult.noViolations();
    }
}
