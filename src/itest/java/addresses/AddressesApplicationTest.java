package addresses;

import io.mstream.addresses.model.Address;
import io.mstream.addresses.model.AddressRepository;
import io.mstream.addresses.AddressesApplication;
import io.mstream.addresses.api.v1.AddressDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = AddressesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AddressesApplicationTest {

    private static final ParameterizedTypeReference<List<AddressDto>> LIST_OF_ADDRESSES =
            new ParameterizedTypeReference<List<AddressDto>>() {
            };

    private static final String POSTCODE = "POSTCODE";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    public void returnsAddressesListGivenByAddressRepository() {

        when(addressRepository.byPostcode(POSTCODE)).thenReturn(
                Arrays.asList(
                        new Address("1", "High Street"),
                        new Address("2", "High Street"),
                        new Address("3", "High Street")
                )
        );

        ResponseEntity<List<AddressDto>> response =
                restTemplate.exchange(
                        "/api/v1/addresses/{postcode}",
                        HttpMethod.GET,
                        null,
                        LIST_OF_ADDRESSES,
                        POSTCODE
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AddressDto> addresses = response.getBody();

        assertThat(addresses).contains(
                new AddressDto("1", "High Street"),
                new AddressDto("2", "High Street"),
                new AddressDto("3", "High Street")
        );
    }
}
