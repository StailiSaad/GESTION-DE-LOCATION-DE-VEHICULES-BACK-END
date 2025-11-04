package com.vehiclerental.dto.request

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

/**
 * DTO de requête pour la création d'une location de véhicule.
 * Contient les données nécessaires pour initier une nouvelle location.
 *
 * @property customerId Identifiant du client effectuant la location
 * @property vehicleId Identifiant du véhicule à louer
 * @property startDate Date de début de la location
 * @property endDate Date de fin de la location
 */
data class RentalRequest(
    @field:NotNull(message = "Customer ID is mandatory")
    val customerId: Long,

    @field:NotNull(message = "Vehicle ID is mandatory")
    val vehicleId: Long,

    @field:NotNull(message = "Start date is mandatory")
    val startDate: LocalDateTime,

    @field:NotNull(message = "End date is mandatory")
    @field:Future(message = "End date must be in the future")
    val endDate: LocalDateTime
)