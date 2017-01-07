package io.mstream.addresses.ordnancesurvey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mstream.addresses.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OrdnanceSurveyOrdnanceSurveyAddressRepositoryTest {

    private static final String POSTCODE = "POSTCODE";

    private OrdnanceSurveyAddressRepository instance;

    @Mock
    private OrdnanceSurveyClient ordnanceSurveyClient;

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        instance = new OrdnanceSurveyAddressRepository(ordnanceSurveyClient, mapper);
    }

    @Test
    public void mapsValidResponse() throws Exception {
        JsonNode jsonNode = jsonResponse("valid");
        when(ordnanceSurveyClient.addressesForPostcode(POSTCODE)).thenReturn(Optional.of(jsonNode));

        List<Address> addresses = instance.byPostcode(POSTCODE);

        assertThat(addresses).contains(
                new Address("1", "building1", "street1", "organisation1"),
                new Address("2", "building1", "street1", "organisation2"),
                new Address(null, "building2", "street1", null)
        );
    }

    @Test
    public void mapsEmptyResponse() throws Exception {
        JsonNode jsonNode = jsonResponse("empty");
        when(ordnanceSurveyClient.addressesForPostcode(POSTCODE)).thenReturn(Optional.of(jsonNode));

        List<Address> addresses = instance.byPostcode(POSTCODE);

        assertThat(addresses).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void handlesInvalidResponse() {
        JsonNode jsonNode = jsonResponse("invalid");
        when(ordnanceSurveyClient.addressesForPostcode(POSTCODE)).thenReturn(Optional.of(jsonNode));

        instance.byPostcode(POSTCODE);
    }

    @Test
    public void handlesMissingResponse() {
        when(ordnanceSurveyClient.addressesForPostcode(POSTCODE)).thenReturn(Optional.empty());

        List<Address> addresses = instance.byPostcode(POSTCODE);

        assertThat(addresses).isEmpty();
    }

    private JsonNode jsonResponse(String name) {
        try (InputStream is = getClass().getResourceAsStream(format("%s_response.json", name))) {
            return mapper.readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("can't read json response: " + name, e);
        }
    }
}