package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

/**
 * Entité représentant un client du système de location de véhicules.
 *
 * @property id Identifiant unique du client
 * @property firstName Prénom du client
 * @property lastName Nom de famille du client
 * @property email Adresse email unique du client
 * @property phoneNumber Numéro de téléphone du client
 * @property driverLicenseNumber Numéro de permis de conduire unique
 * @property createdAt Date et heure de création du client
 * @property rentals Liste des locations associées à ce client
 */
@Entity
@Table(name = "customers")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    val email: String,

    @Pattern(regexp = "\\+?[0-9.\\-\\s()]{10,}", message = "Phone number should be valid")
    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(name = "driver_license_number", unique = true, nullable = false)
    val driverLicenseNumber: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val rentals: MutableSet<Rental> = mutableSetOf()
) {
    /**
     * Retourne le nom complet du client.
     *
     * @return String contenant le prénom et nom concaténés
     */
    fun getFullName(): String = "$firstName $lastName"
}