package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "trucks")
@DiscriminatorValue("TRUCK")
class Truck(
    brand: String,
    model: String,
    year: Int,
    dailyRate: Double,
    available: Boolean = true,

    @NotNull
    @Min(value = 1000, message = "Capacity must be at least 1000kg")
    @Column(nullable = false)
    val capacity: Int, // en kg

    @Column(name = "four_wheel_drive", nullable = false)
    val fourWheelDrive: Boolean
) : Vehicle(brand = brand, model = model, year = year, dailyRate = dailyRate, available = available) {

    override fun calculateRentalPrice(days: Int): Double {
        var price = dailyRate * days
        // Supplément pour 4x4
        if (fourWheelDrive) {
            price += 25.0 * days
        }
        // Supplément pour grande capacité
        if (capacity > 5000) {
            price += 20.0 * days
        }
        return price
    }

    override fun getVehicleType(): String = "TRUCK"
}