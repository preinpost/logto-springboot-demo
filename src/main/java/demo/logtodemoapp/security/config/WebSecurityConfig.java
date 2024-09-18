package demo.logtodemoapp.security.config;

import demo.logtodemoapp.security.CustomLogoutHandler;
import demo.logtodemoapp.security.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;

import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public JwtDecoderFactory<ClientRegistration> idTokenDecoderFactory() {
        OidcIdTokenDecoderFactory idTokenDecoderFactory = new OidcIdTokenDecoderFactory();
        idTokenDecoderFactory.setJwsAlgorithmResolver(clientRegistration -> SignatureAlgorithm.ES384);
        return idTokenDecoderFactory;
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/", "/home").permitAll()
//                                .antMatchers("/", "/home").permitAll() // Allow access to the home page
                                        .anyRequest().authenticated() // All other requests require authentication
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .successHandler(new CustomSuccessHandler())
                )
                .logout(logout ->
                        logout
                                .logoutSuccessHandler(new CustomLogoutHandler())
                );
        return http.build();
    }

}
