package org.pushups.gymgoers.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://pushupsbackend-production.up.railway.app/api",
                "https://pushupsgym.netlify.app"
            )
            .allowedMethods("GET","POST","PUT","PATCH","DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}