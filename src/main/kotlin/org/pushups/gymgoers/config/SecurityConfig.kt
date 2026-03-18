package org.pushups.gymgoers.config

import org.pushups.gymgoers.service.CustomOidcUserService
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
    fun securityFilterChain(http: HttpSecurity, customOidcUserService: CustomOidcUserService): SecurityFilterChain {
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
                it.userInfoEndpoint { ui -> ui.oidcUserService(customOidcUserService) }
                it.successHandler(oAuth2LoginSuccessHandler)
            }
            .logout {
                it.logoutSuccessUrl("https://pushupsfrontend.up.railway.app")
            }
            .exceptionHandling {
                it.authenticationEntryPoint { request, response, _ ->
                    response.status = 401
                }
            }
        return http.build()
    }
}