package io.mstream.addresses.api.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Error {

    private final String code;
    private final String description;

    @JsonCreator
    public Error(
            @JsonProperty(value = "code") String code,
            @JsonProperty(value = "description") String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(code, error.code) &&
                Objects.equals(description, error.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description);
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
