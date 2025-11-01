package com.vehiclerental.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

sealed class VehicleRequest(
    @field:NotBlank(message = "Brand is mandatory")
    open val brand: String,

    @field:NotBlank(message = "Model is mandatory")
    open val model: String,

    @field:NotNull
    @field:Min(value = 1900, message = "Year must be after 1900")
    open val year: Int,

    @field:NotNull
    @field:Positive(message = "Daily rate must be positive")
    open val dailyRate: Double
)

data class CarRequest(
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,

    @field:NotNull
    @field:Min(value = 2, message = "Doors must be at least 2")
    val doors: Int,

    val fuelType: String,
    val automatic: Boolean
) : VehicleRequest(brand, model, year, dailyRate)

data class MotorcycleRequest(
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,

    @field:NotNull
    @field:Min(value = 50, message = "Engine size must be at least 50cc")
    val engineSize: Int,

    @field:NotBlank(message = "Type is mandatory")
    val type: String
) : VehicleRequest(brand, model, year, dailyRate)

data class TruckRequest(
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,

    @field:NotNull
    @field:Min(value = 1000, message = "Capacity must be at least 1000kg")
    val capacity: Int,
    val fourWheelDrive: Boolean
) : VehicleRequest(brand, model, year, dailyRate)