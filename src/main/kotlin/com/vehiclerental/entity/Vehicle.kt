package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

/**
 * Classe scellée représentant un véhicule de base.
 * Sert de classe parente pour tous les types de véhicules dans le système.
 * Utilise une stratégie d'héritage JOINED avec une table par classe enfant.
 *
 * @property id Identifiant unique du véhicule
 * @property brand Marque du véhicule
 * @property model Modèle du véhicule
 * @property year Année de fabrication
 * @property dailyRate Tarif journalier de location
 * @property available Statut de disponibilité du véhicule
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "vehicles")
@DiscriminatorColumn(name = "vehicle_type")
sealed class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank(message = "Brand is mandatory")
    @Column(nullable = false)
    val brand: String,

    @NotBlank(message = "Model is mandatory")
    @Column(nullable = false)
    val model: String,

    @NotNull
    @Min(value = 1900, message = "Year must be after 1900")
    @Column(nullable = false)
    val year: Int,

    @NotNull
    @Positive(message = "Daily rate must be positive")
    @Column(name = "daily_rate", nullable = false)
    val dailyRate: Double,

    @Column(nullable = false)
    var available: Boolean = true
) {
    /**
     * Calcule le prix de location pour une durée donnée.
     * Doit être implémenté par chaque sous-classe avec sa propre logique de calcul.
     *
     * @param days Nombre de jours de location
     * @return Prix total de la location
     */
    abstract fun calculateRentalPrice(days: Int): Double

    /**
     * Retourne le type de véhicule.
     * Doit être implémenté par chaque sous-classe.
     *
     * @return String représentant le type de véhicule
     */
    abstract fun getVehicleType(): String
}