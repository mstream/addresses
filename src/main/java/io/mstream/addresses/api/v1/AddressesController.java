package io.mstream.addresses.api.v1;


import io.mstream.addresses.api.validation.PostcodeValidator;
import io.mstream.addresses.api.validation.ValidationException;
import io.mstream.addresses.api.validation.ValidationResult;
import io.mstream.addresses.model.Address;
import io.mstream.addresses.model.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class
AddressesController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AddressesController.class);

    private final AddressRepository addressRepository;
    private final PostcodeValidator postcodeValidator;

    @Autowired
    public AddressesController(
            AddressRepository addressRepository,
            PostcodeValidator postcodeValidator) {
        this.addressRepository = addressRepository;
        this.postcodeValidator = postcodeValidator;
    }

    private static AddressDto mapAddress(Address address) {
        return new AddressDto(
                address.getBuildingNumber(),
                address.getBuildingName(),
                address.getStreetName(),
                address.getBusinessName());
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

        Set<AddressDto> addresses;

        try {
            addresses = addressRepository
                    .byPostcode(postcode)
                    .stream()
                    .map(AddressesController::mapAddress)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (Exception e) {
            LOGGER.error("can't retrieve addresses from the repository", e);
            addresses = Collections.emptySet();
        }

        return ResponseEntity.ok(addresses);
    }

}
