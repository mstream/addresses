package io.mstream.addresses;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AddressesApplication {

    public static final String ORDNANCE_SURVEY = "ordnanceSurvey";

    public static void main(String[] args) {
        SpringApplication.run(AddressesApplication.class, args);
    }

    @Bean
    @Qualifier(ORDNANCE_SURVEY)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri("https://api.ordnancesurvey.co.uk")
                .build();
    }
}
