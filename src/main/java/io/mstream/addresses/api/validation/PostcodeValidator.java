package io.mstream.addresses.api.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;


@Component
public class PostcodeValidator implements Validator<String> {

    private static final Set<String> POSTCODE_FORMATS = new HashSet<>(
            Arrays.asList(
                    "AA0A0AA",
                    "A0A0AA",
                    "A00AA",
                    "A000AA",
                    "AA00AA",
                    "AA000AA"
            )
    );

    private static final ValidationResult validationFailureResult =
            ValidationResult.fromViolations(
                    Collections.singleton(
                            format(
                                    "Invalid postcode format. Supported formats: %s",
                                    POSTCODE_FORMATS
                            )
                    )
            );

    private final Pattern postcodePattern;

    public PostcodeValidator() {
        String postcodeFormatsRegex = POSTCODE_FORMATS
                .stream()
                .map(format -> format
                        .replaceAll("A", "[A-Z]")
                        .replaceAll("0", "\\\\d"))
                .collect(Collectors.joining("|"));
        postcodePattern = Pattern.compile(postcodeFormatsRegex);
    }

    @Override
    public ValidationResult validate(String postcode) {
        boolean matches = postcodePattern.matcher(postcode).matches();
        return matches ?
                ValidationResult.noViolations() :
                validationFailureResult;
    }
}
