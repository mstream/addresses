package io.mstream.addresses.model;


import java.util.Objects;

public class Address {

    private final String buildingName;
    private final String streetName;
    private final String businessName;

    public Address(
            String buildingName,
            String streetName,
            String businessName) {
        this.buildingName = buildingName;
        this.streetName = streetName;
        this.businessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(buildingName, address.buildingName) &&
                Objects.equals(streetName, address.streetName) &&
                Objects.equals(businessName, address.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingName, streetName, businessName);
    }

    @Override
    public String toString() {
        return "OrdnanceSurveyAddress{" +
                "buildingName='" + buildingName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getBusinessName() {
        return businessName;
    }
}
