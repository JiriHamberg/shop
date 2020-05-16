package fi.hartikat.shopproductservice.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Value("${swagger.baseurl}")
    private String baseUrl;

    @Value("${springdoc.swagger-ui.tokenUri}")
    private String tokenUri;

    @Value("${springdoc.swagger-ui.authorizationUri}")
    private String authorizationUri;

    @Bean
    public OpenAPI customOpenApi() {
        var oapi = new OpenAPI().addServersItem(new Server().url(baseUrl));

        oapi.components(new Components().addSecuritySchemes("oauth2",
                new SecurityScheme()
                        .flows(
                                new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(authorizationUri)
                                                .tokenUrl(tokenUri)))
                        .type(SecurityScheme.Type.OAUTH2)
                        .scheme("bearer").bearerFormat("jwt").in(SecurityScheme.In.HEADER)
                        .name("Authorization")))
                .info(new Info().title("App API").version("snapshot"))
                .addSecurityItem(
                        new SecurityRequirement().addList("oauth2", List.of("read", "write")));

        return oapi;
    }
}