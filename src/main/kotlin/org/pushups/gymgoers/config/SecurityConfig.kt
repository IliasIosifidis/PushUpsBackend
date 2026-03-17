package org.pushups.gymgoers.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/member/**").hasRole("ADMIN")
                    .requestMatchers("/api/booking/weekly").authenticated()
                    .requestMatchers("/api/booking/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2Login {  }
        return http.build()
    }
}