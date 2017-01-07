package io.mstream.addresses.api.v1;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class AddressesController {

    @RequestMapping(method = RequestMethod.GET, path = "/addresses/{postcode}")
    public List<Address> byPostcode(
            @PathVariable(name = "postcode") String postcode) {
        return Arrays.asList(
                new Address("1", "High Street"),
                new Address("2", "High Street"),
                new Address("3", "High Street")
        );
    }

}
