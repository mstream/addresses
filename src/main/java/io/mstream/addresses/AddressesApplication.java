package io.mstream.addresses;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SpringBootApplication
@EnableSwagger2
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

    @Bean
    public Docket docket() {
        String springBootPackage = "org.springframework.boot";
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(not(basePackage(springBootPackage)))
                .paths(PathSelectors.any())
                .build();
    }
}
