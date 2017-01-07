package io.mstream.addresses.ordnancesurvey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrdnanceSurveyClientTest {

    private static final String API_KEY = "API_KEY";
    private static final String POST_CODE = "POST_CODE";
    @Mock
    ResponseEntity responseEntity;
    private OrdnanceSurveyClient instance;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenReturn(responseEntity);
        instance = new OrdnanceSurveyClient(restTemplate, API_KEY);
    }

    @Test
    public void buildsCorrectUri() throws Exception {
        instance.addressesForPostcode(POST_CODE);

        String expectedUri = format(
                "/places/v1/addresses/postcode?postcode=%s&key=%s",
                POST_CODE,
                API_KEY);

        verify(restTemplate).exchange(
                eq(expectedUri),
                eq(HttpMethod.GET),
                isNull(HttpEntity.class),
                eq(JsonNode.class));
    }

    @Test
    public void returnsResponseBodyOnSuccessfulResponse() throws Exception {
        JsonNode body = new ObjectMapper().createObjectNode();
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(body);
        Optional<JsonNode> jsonNodeOpt = instance.addressesForPostcode(POST_CODE);
        assertThat(jsonNodeOpt.isPresent()).isTrue();
        assertThat(jsonNodeOpt.get()).isEqualTo(body);
    }

    @Test
    public void returnsMissingBodyOnFailedResponse() throws Exception {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.SERVICE_UNAVAILABLE);
        Optional<JsonNode> jsonNodeOpt = instance.addressesForPostcode(POST_CODE);
        assertThat(jsonNodeOpt.isPresent()).isFalse();
    }

    @Test
    public void returnsMissingBodyOnExceptionDuringCommunication() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenThrow(new RuntimeException());
        Optional<JsonNode> jsonNodeOpt = instance.addressesForPostcode(POST_CODE);
        assertThat(jsonNodeOpt.isPresent()).isFalse();
    }
}