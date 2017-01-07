package io.mstream.addresses.api.v1;


import io.mstream.addresses.model.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class AddressesController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressesController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addresses/{postcode}")
    public List<AddressDto> byPostcode(
            @PathVariable(name = "postcode") String postcode) {
        return addressRepository
                .byPostcode(postcode)
                .stream()
                .map(address ->
                        new AddressDto(
                                address.getPropertyNumber(),
                                address.getStreetAddress())
                )
                .collect(Collectors.toList());
    }

}
