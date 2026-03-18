package org.pushups.gymgoers.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/**").authenticated()
                    .requestMatchers("/api/member/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/booking/**").hasRole("ADMIN")
                    .requestMatchers("/api/booking/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2Login {
                it.successHandler(oAuth2LoginSuccessHandler)
            }
        return http.build()
    }
}