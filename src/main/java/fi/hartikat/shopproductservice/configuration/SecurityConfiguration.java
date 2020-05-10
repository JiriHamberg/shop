package fi.hartikat.shopproductservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${oidc.jwk-set-uri}")
    private String jwkSetUri;

        @Override
        protected void configure(HttpSecurity security) throws Exception {
            security.sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS,"**").permitAll() //allow CORS option calls
                    .and()
                    .authorizeRequests()
                    .antMatchers("/actuator/**", "/swagger-ui/**",  "/api-docs/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .oauth2ResourceServer(
                            server -> server.authenticationManagerResolver(realmAuthenticationManager()));

            security.csrf().disable();
            security.headers().disable();
        }

        @Bean
        public AuthenticationManagerResolver<HttpServletRequest> realmAuthenticationManager() {
            var authenticationProvider =
                    new JwtAuthenticationProvider(NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build());

            authenticationProvider.setJwtAuthenticationConverter(new JwtBearerTokenAuthenticationConverter());

            return request -> authenticationProvider::authenticate;
        }

}
