package com.vehiclerental.dto.response
/**
 * DTO de réponse pour les données d'une location.
 * Contient les informations complètes d'une location avec client et véhicule.
 *
 * @property id Identifiant unique de la location
 * @property customer Informations du client
 * @property vehicle Informations du véhicule loué
 * @property startDate Date de début de location
 * @property endDate Date de fin de location
 * @property totalPrice Prix total de la location
 * @property status Statut de la location
 * @property createdAt Date de création de la location
 */
data class RentalResponse(
    val id: Long,
    val customer: CustomerResponse,
    val vehicle: VehicleResponse,
    val startDate: String,
    val endDate: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: String
)