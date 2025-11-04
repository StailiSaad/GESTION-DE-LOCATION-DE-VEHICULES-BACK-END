package com.vehiclerental.dto.response

/**
 * DTO de réponse pour les données d'un client.
 * Utilisé pour exposer les informations client via l'API.
 *
 * @property id Identifiant unique du client
 * @property firstName Prénom du client
 * @property lastName Nom de famille du client
 * @property fullName Nom complet du client
 * @property email Adresse email du client
 * @property phoneNumber Numéro de téléphone du client
 * @property driverLicenseNumber Numéro de permis de conduire
 * @property createdAt Date de création du client
 */
data class CustomerResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val driverLicenseNumber: String,
    val createdAt: String
)

