package io.mstream.addresses.api.validation;


public interface Validator<T> {


    ValidationResult validate(T object);
}
