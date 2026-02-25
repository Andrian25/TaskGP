package com.example.hotelreservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public OpenAPI hotelReservationOpenApi()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Property View Hotel API")
                        .description("REST API for hotel management and analytics")
                        .version("1.0.0")
                        .contact(new Contact().name("Hotel API")));
    }
}
