package io.mstream.addresses.api.v1;


import io.mstream.addresses.api.validation.PostcodeValidator;
import io.mstream.addresses.api.validation.ValidationException;
import io.mstream.addresses.api.validation.ValidationResult;
import io.mstream.addresses.model.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class AddressesController {

    private final AddressRepository addressRepository;
    private final PostcodeValidator postcodeValidator;

    @Autowired
    public AddressesController(
            AddressRepository addressRepository,
            PostcodeValidator postcodeValidator) {
        this.addressRepository = addressRepository;
        this.postcodeValidator = postcodeValidator;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addresses/{postcode}")
    public ResponseEntity<Set<AddressDto>> byPostcode(
            @PathVariable(name = "postcode") String postcode) {

        String normalizedPostcode = postcode.replaceAll("\\s+", "").toUpperCase();

        ValidationResult validationResult =
                postcodeValidator.validate(normalizedPostcode);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getViolations());
        }

        Set<AddressDto> addresses = addressRepository
                .byPostcode(postcode)
                .stream()
                .map(address ->
                        new AddressDto(
                                address.getBuildingName(),
                                address.getStreetName(),
                                address.getBusinessName())
                )
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return ResponseEntity.ok(addresses);
    }

}
