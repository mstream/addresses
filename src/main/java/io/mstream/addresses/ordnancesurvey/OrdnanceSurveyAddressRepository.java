package io.mstream.addresses.ordnancesurvey;


import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mstream.addresses.model.Address;
import io.mstream.addresses.model.AddressRepository;
import io.mstream.addresses.ordnancesurvey.model.OrdnanceSurveyAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;


@Component
public class OrdnanceSurveyAddressRepository implements AddressRepository {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OrdnanceSurveyAddressRepository.class);

    private static final JsonPointer RESULTS_POINTER =
            JsonPointer.compile("/results");

    private static final JsonPointer ADDRESS_POINTER =
            JsonPointer.compile("/DPA");

    private final OrdnanceSurveyClient ordnanceSurveyClient;
    private final ObjectMapper mapper;

    @Autowired
    public OrdnanceSurveyAddressRepository(OrdnanceSurveyClient ordnanceSurveyClient, ObjectMapper mapper) {
        this.ordnanceSurveyClient = ordnanceSurveyClient;
        this.mapper = mapper;
    }

    private static Address mapAddress(OrdnanceSurveyAddress ordnanceSurveyAddress) {
        return new Address(
                ordnanceSurveyAddress.getBuildingNumber(),
                ordnanceSurveyAddress.getBuildingName(),
                ordnanceSurveyAddress.getThoroughfareName(),
                ordnanceSurveyAddress.getOrganisationName()
        );
    }

    @Override
    public List<Address> byPostcode(String postcode) {
        Optional<JsonNode> responseNodeOpt =
                ordnanceSurveyClient.addressesForPostcode(postcode);

        if (!responseNodeOpt.isPresent()) {
            LOGGER.warn("Can't get address from the Ordnance Survey API. Falling back to an empty address list");
            return Collections.emptyList();
        }

        JsonNode responseNode = responseNodeOpt.get();

        return stream(spliteratorUnknownSize(
                responseNode.at(RESULTS_POINTER).elements(), Spliterator.ORDERED),
                false
        ).map(this::extractAddress)
                .map(OrdnanceSurveyAddressRepository::mapAddress)
                .collect(Collectors.toList());
    }

    private OrdnanceSurveyAddress extractAddress(JsonNode resultNode) {
        try {
            return mapper.treeToValue(resultNode.at(ADDRESS_POINTER), OrdnanceSurveyAddress.class);
        } catch (Exception e) {
            throw new RuntimeException("can't extract an address", e);
        }
    }
}
