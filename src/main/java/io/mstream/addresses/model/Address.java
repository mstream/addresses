package io.mstream.addresses.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    private final String propertyNumber;
    private final String streetAddress;
    private String businessName;

    @JsonCreator
    public Address(
            @JsonProperty(value = "propertyNumber") String propertyNumber,
            @JsonProperty(value = "streetAddress") String streetAddress) {
        this.propertyNumber = propertyNumber;
        this.streetAddress = streetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address addressDto = (Address) o;
        return Objects.equals(propertyNumber, addressDto.propertyNumber) &&
                Objects.equals(streetAddress, addressDto.streetAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyNumber, streetAddress);
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "propertyNumber='" + propertyNumber + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
