package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

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
    abstract fun calculateRentalPrice(days: Int): Double
    abstract fun getVehicleType(): String
}