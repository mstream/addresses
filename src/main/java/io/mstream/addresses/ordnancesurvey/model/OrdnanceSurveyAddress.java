package io.mstream.addresses.ordnancesurvey.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


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
