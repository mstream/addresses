package io.mstream.addresses.api.v1;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {


    private final String buildingName;
    private final String streetName;
    private final String businessName;

    @JsonCreator
    public AddressDto(
            @JsonProperty(value = "buildingName", required = true) String buildingName,
            @JsonProperty(value = "streetName", required = true) String streetName,
            @JsonProperty(value = "businessName") String businessName) {
        this.buildingName = buildingName;
        this.streetName = streetName;
        this.businessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto address = (AddressDto) o;
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
