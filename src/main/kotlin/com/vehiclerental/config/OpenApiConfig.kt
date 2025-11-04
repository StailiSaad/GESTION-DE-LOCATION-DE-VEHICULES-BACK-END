package com.vehiclerental.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration Spring pour la documentation OpenAPI/Swagger.
 * Définit les métadonnées de l'API REST pour la location de véhicules.
 */
@Configuration
class OpenApiConfig {

    /**
     * Configure et personnalise la documentation OpenAPI.
     *
     * @return Instance configurée de OpenAPI avec les informations de l'API
     */
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Vehicle Rental API")
                    .description("API REST pour la gestion de location de véhicules")
                    .version("1.0.0")
            )
    }
}
