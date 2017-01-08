package io.mstream.addresses.api.v1;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    private final String buildingNumber;
    private final String buildingName;
    private final String streetName;
    private final String businessName;

    @JsonCreator
    public AddressDto(
            @JsonProperty(value = "buildingNumber") String buildingNumber,
            @JsonProperty(value = "buildingName") String buildingName,
            @JsonProperty(value = "streetName", required = true) String streetName,
            @JsonProperty(value = "businessName") String businessName) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.streetName = streetName;
        this.businessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return Objects.equals(buildingNumber, that.buildingNumber) &&
                Objects.equals(buildingName, that.buildingName) &&
                Objects.equals(streetName, that.streetName) &&
                Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingNumber, buildingName, streetName, businessName);
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "buildingNumber='" + buildingNumber + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }

    public String getBuildingNumber() {
        return buildingNumber;
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
