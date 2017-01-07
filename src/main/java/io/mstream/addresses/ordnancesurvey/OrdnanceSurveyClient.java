package io.mstream.addresses.ordnancesurvey;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
public class OrdnanceSurveyClient {

    public JsonNode addressesForPostcode(String postcode) {
        return new ObjectMapper().createObjectNode();
    }
}
