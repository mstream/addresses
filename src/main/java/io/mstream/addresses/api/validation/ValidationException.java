package io.mstream.addresses.api.validation;


import io.mstream.addresses.api.LightweightException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ValidationException extends LightweightException {

    private final Set<String> violations;

    public ValidationException(Set<String> violations) {
        this.violations = Collections.unmodifiableSet(new HashSet<>(violations));
    }

    public Set<String> getViolations() {
        return violations;
    }
}
