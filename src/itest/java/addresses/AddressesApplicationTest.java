package addresses;

import io.mstream.addresses.AddressesApplication;
import io.mstream.addresses.api.v1.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = AddressesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AddressesApplicationTest {

    private static final ParameterizedTypeReference<List<Address>> LIST_OF_ADDRESSES =
            new ParameterizedTypeReference<List<Address>>() {
            };

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {

        ResponseEntity<List<Address>> response =
                restTemplate.exchange(
                        "/api/v1/addresses/{postcode}",
                        HttpMethod.GET,
                        null,
                        LIST_OF_ADDRESSES,
                        "VALID_POSTCODE"
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Address> addresses = response.getBody();

        assertThat(addresses).contains(
                new Address("1", "High Street"),
                new Address("2", "High Street"),
                new Address("3", "High Street")
        );
    }
}
