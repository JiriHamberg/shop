package fi.hartikat.shopproductservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Value("${swagger.baseurl}")
    private String baseUrl;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().addServersItem(new Server().url(baseUrl));
    }
}