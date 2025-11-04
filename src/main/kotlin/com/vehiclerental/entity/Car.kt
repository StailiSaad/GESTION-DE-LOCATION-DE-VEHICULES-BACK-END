package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

/**
 * Entité représentant une voiture.
 * Hérite de Vehicle et ajoute des propriétés spécifiques aux voitures.
 *
 * @property doors Nombre de portes
 * @property fuelType Type de carburant
 * @property automatic Si la voiture est automatique
 */
@Entity
@Table(name = "cars")
@DiscriminatorValue("CAR")
class Car(
    brand: String,
    model: String,
    year: Int,
    dailyRate: Double,
    available: Boolean = true,

    @NotNull
    @Min(value = 2, message = "Doors must be at least 2")
    @Column(nullable = false)
    val doors: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    val fuelType: FuelType,

    @Column(nullable = false)
    val automatic: Boolean
) : Vehicle(brand = brand, model = model, year = year, dailyRate = dailyRate, available = available) {

    /**
     * Calcule le prix de location pour une durée donnée.
     * Ajoute un supplément pour les voitures automatiques.
     *
     * @param days Nombre de jours de location
     * @return Prix total de la location
     */
    override fun calculateRentalPrice(days: Int): Double {
        var price = dailyRate * days
        // Supplément pour voiture automatique
        if (automatic) {
            price += 10.0 * days
        }
        return price
    }

    /**
     * Retourne le type de véhicule.
     *
     * @return String représentant le type de véhicule
     */
    override fun getVehicleType(): String = "CAR"
}

/**
 * Enumération des types de carburant disponibles pour les véhicules.
 */
enum class FuelType {
    GASOLINE, DIESEL, ELECTRIC, HYBRID
}