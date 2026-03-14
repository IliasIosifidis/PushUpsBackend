package org.pushups.gymgoers.config

import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://pushupsbackend-production.up.railway.app/api"
            )
            .allowedMethods("GET","POST","PUT","DELETE")
            .allowedHeaders("*")
    }
}