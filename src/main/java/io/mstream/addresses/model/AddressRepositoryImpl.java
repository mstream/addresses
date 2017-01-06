package io.mstream.addresses.model;


import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public List<Address> byPostcode(String postcode) {
        return Collections.emptyList();
    }
}
