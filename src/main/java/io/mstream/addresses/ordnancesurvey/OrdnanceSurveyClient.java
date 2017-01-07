package io.mstream.addresses.ordnancesurvey;


import com.fasterxml.jackson.databind.JsonNode;
import io.mstream.addresses.AddressesApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@Component
public class OrdnanceSurveyClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OrdnanceSurveyClient.class);

    private final RestTemplate restTemplate;
    private final String apiKey;

    @Autowired
    public OrdnanceSurveyClient(
            @Qualifier(AddressesApplication.ORDNANCE_SURVEY) RestTemplate restTemplate,
            @Value("${ordnancesurvey.apikey}") String apiKey
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public Optional<JsonNode> addressesForPostcode(String postcode) {

        String uri = UriComponentsBuilder
                .fromUriString("/places/v1/addresses/postcode")
                .queryParam("postcode", postcode)
                .queryParam("key", apiKey)
                .toUriString();

        ResponseEntity<JsonNode> response = null;

        try {
            response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    JsonNode.class);
        } catch (Exception e) {
            LOGGER.warn("error during a postcode addresses request", e);
        }

        return (response != null && HttpStatus.OK.equals(response.getStatusCode())) ?
                Optional.of(response.getBody()) :
                Optional.empty();
    }
}
