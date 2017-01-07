package io.mstream.addresses.model;


import java.util.List;

public interface AddressRepository {

    List<Address> byPostcode(String postcode);
}
