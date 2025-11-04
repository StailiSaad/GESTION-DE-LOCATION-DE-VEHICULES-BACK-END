package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Entité représentant une moto.
 * Hérite de Vehicle et ajoute des propriétés spécifiques aux motos.
 *
 * @property engineSize Cylindrée du moteur en cc
 * @property type Type de moto (SPORT, CRUISER, TOURING, etc.)
 */
@Entity
@Table(name = "motorcycles")
@DiscriminatorValue("MOTORCYCLE")
class Motorcycle(
    brand: String,
    model: String,
    year: Int,
    dailyRate: Double,
    available: Boolean = true,

    @NotNull
    @Min(value = 50, message = "Engine size must be at least 50cc")
    @Column(name = "engine_size", nullable = false)
    val engineSize: Int,

    @NotBlank(message = "Motorcycle type is mandatory")
    @Column(nullable = false)
    val type: String // SPORT, CRUISER, TOURING, etc.
) : Vehicle(brand = brand, model = model, year = year, dailyRate = dailyRate, available = available) {

    /**
     * Calcule le prix de location pour une durée donnée.
     * Ajoute un supplément pour les grosses cylindrées (>1000cc).
     *
     * @param days Nombre de jours de location
     * @return Prix total de la location
     */
    override fun calculateRentalPrice(days: Int): Double {
        var price = dailyRate * days
        // Supplément pour grosses cylindrées
        if (engineSize > 1000) {
            price += 15.0 * days
        }
        return price
    }

    /**
     * Retourne le type de véhicule.
     *
     * @return String représentant le type de véhicule
     */
    override fun getVehicleType(): String = "MOTORCYCLE"
}