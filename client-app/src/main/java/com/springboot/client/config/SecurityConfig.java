package com.springboot.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String issuerUri;
	
	@Bean
	SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((authHttp) -> authHttp
				.requestMatchers("/authorized").permitAll()
				.requestMatchers(HttpMethod.GET, "/list").hasAnyAuthority("SCOPE_read","SCOPE_write")
				.requestMatchers(HttpMethod.POST, "/create").hasAnyAuthority("SCOPE_read","SCOPE_write")
				.anyRequest().authenticated())
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
		.oauth2Client(Customizer.withDefaults())
		.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))));
		return http.build();
	}
}
