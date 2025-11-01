package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

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

    override fun calculateRentalPrice(days: Int): Double {
        var price = dailyRate * days
        // Supplément pour voiture automatique
        if (automatic) {
            price += 10.0 * days
        }
        return price
    }

    override fun getVehicleType(): String = "CAR"
}

enum class FuelType {
    GASOLINE, DIESEL, ELECTRIC, HYBRID
}