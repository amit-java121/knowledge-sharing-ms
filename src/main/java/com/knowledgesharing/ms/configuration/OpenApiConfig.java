package com.knowledgesharing.ms.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Knowledge Sharing API").description(
                        "This project basically interacts with the database to get the details of the links of TED talks, Modify the details,\n" +
                                "Insert new details, Delete the existing ones (CRUD operations)"));
    }
}
