package io.mstream.addresses.api.validation;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ValidationResult {

    private static final ValidationResult SUCCESSFUL =
            new ValidationResult(Collections.emptySet());

    private final Set<String> violations;

    private ValidationResult(Set<String> violations) {
        this.violations = Collections.unmodifiableSet(new HashSet<>(violations));
    }

    public static ValidationResult noViolations() {
        return SUCCESSFUL;
    }

    public static ValidationResult fromViolations(Set<String> violations) {
        return new ValidationResult(violations);
    }

    public boolean isSuccessful() {
        return violations.isEmpty();
    }

    public Set<String> getViolations() {
        return violations;
    }
}
