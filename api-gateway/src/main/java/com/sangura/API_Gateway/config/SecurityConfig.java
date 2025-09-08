package com.sangura.API_Gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception{
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        //Assignment Service
                        .pathMatchers((HttpMethod.GET), "/assignments/all").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.POST), "/assignments/add").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.PUT), "/assignments/update/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.DELETE),"/assignments/delete/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET), "/assignments/{id}").hasRole("USER")

                        //Errand Service
                        .pathMatchers((HttpMethod.POST), "/errands/create").hasRole("USER")
                        .pathMatchers((HttpMethod.DELETE),"/errands/delete").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.PUT),"/errands/update").hasRole("USER")
                        .pathMatchers((HttpMethod.GET), "/errands/all").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET),"/errands/{id}").hasRole("USER")

                        .pathMatchers((HttpMethod.POST), "/tasker/create").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.DELETE), "/tasker/delete/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET),"/tasker/all").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.PUT),"/tasker/update/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET),"/tasker/{id}").hasRole("USER")

                        //Auth Service
                        .pathMatchers((HttpMethod.PUT),"users/update/{id}").hasRole("USER")
                        .pathMatchers((HttpMethod.GET),"/users/all").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET), "users/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.DELETE),"/users/delete/{id}").hasRole("USER")

                        //Tracking Service
                        .pathMatchers((HttpMethod.POST), "/tracking/create").hasRole("USER")
                        .pathMatchers((HttpMethod.PUT),"/tracking/complete/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.PUT),"/tracking/update/{id}").hasRole("ADMIN")
                        .pathMatchers((HttpMethod.GET),"/tracking/{id}").hasRole("USER")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
