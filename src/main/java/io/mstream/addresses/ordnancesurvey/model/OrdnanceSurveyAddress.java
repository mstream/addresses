package io.mstream.addresses.ordnancesurvey.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdnanceSurveyAddress {

    private final String buildingNumber;
    private final String buildingName;
    private final String thoroughfareName;
    private final String organisationName;

    @JsonCreator
    public OrdnanceSurveyAddress(
            @JsonProperty(value = "BUILDING_NUMBER") String buildingNumber,
            @JsonProperty(value = "BUILDING_NAME") String buildingName,
            @JsonProperty(value = "THOROUGHFARE_NAME") String thoroughfareName,
            @JsonProperty(value = "ORGANISATION_NAME") String organisationName) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.thoroughfareName = thoroughfareName;
        this.organisationName = organisationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdnanceSurveyAddress that = (OrdnanceSurveyAddress) o;
        return Objects.equals(buildingNumber, that.buildingNumber) &&
                Objects.equals(buildingName, that.buildingName) &&
                Objects.equals(thoroughfareName, that.thoroughfareName) &&
                Objects.equals(organisationName, that.organisationName);
    }

    @Override
    public String toString() {
        return "OrdnanceSurveyAddress{" +
                "buildingNumber='" + buildingNumber + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", thoroughfareName='" + thoroughfareName + '\'' +
                ", organisationName='" + organisationName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingNumber, buildingName, thoroughfareName, organisationName);
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getThoroughfareName() {
        return thoroughfareName;
    }

    public String getOrganisationName() {
        return organisationName;
    }
}
