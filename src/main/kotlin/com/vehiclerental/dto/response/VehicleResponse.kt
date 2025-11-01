package com.vehiclerental.dto.response

import com.vehiclerental.entity.Vehicle

sealed class VehicleResponse(
    open val id: Long,
    open val brand: String,
    open val model: String,
    open val year: Int,
    open val dailyRate: Double,
    open val available: Boolean,
    open val vehicleType: String
) {
    companion object {
        fun fromEntity(vehicle: Vehicle): VehicleResponse = when (vehicle) {
            is com.vehiclerental.entity.Car -> CarResponse.fromEntity(vehicle)
            is com.vehiclerental.entity.Motorcycle -> MotorcycleResponse.fromEntity(vehicle)
            is com.vehiclerental.entity.Truck -> TruckResponse.fromEntity(vehicle)
            else -> throw IllegalArgumentException("Unknown vehicle type")
        }
    }
}

data class CarResponse(
    override val id: Long,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,
    override val available: Boolean,
    override val vehicleType: String,
    val doors: Int,
    val fuelType: String,
    val automatic: Boolean
) : VehicleResponse(id, brand, model, year, dailyRate, available, vehicleType) {
    companion object {
        fun fromEntity(car: com.vehiclerental.entity.Car): CarResponse = CarResponse(
            id = car.id,
            brand = car.brand,
            model = car.model,
            year = car.year,
            dailyRate = car.dailyRate,
            available = car.available,
            vehicleType = car.getVehicleType(),
            doors = car.doors,
            fuelType = car.fuelType.name,
            automatic = car.automatic
        )
    }
}

data class MotorcycleResponse(
    override val id: Long,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,
    override val available: Boolean,
    override val vehicleType: String,
    val engineSize: Int,
    val type: String
) : VehicleResponse(id, brand, model, year, dailyRate, available, vehicleType) {
    companion object {
        fun fromEntity(motorcycle: com.vehiclerental.entity.Motorcycle): MotorcycleResponse = MotorcycleResponse(
            id = motorcycle.id,
            brand = motorcycle.brand,
            model = motorcycle.model,
            year = motorcycle.year,
            dailyRate = motorcycle.dailyRate,
            available = motorcycle.available,
            vehicleType = motorcycle.getVehicleType(),
            engineSize = motorcycle.engineSize,
            type = motorcycle.type
        )
    }
}

data class TruckResponse(
    override val id: Long,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val dailyRate: Double,
    override val available: Boolean,
    override val vehicleType: String,
    val capacity: Int,
    val fourWheelDrive: Boolean
) : VehicleResponse(id, brand, model, year, dailyRate, available, vehicleType) {
    companion object {
        fun fromEntity(truck: com.vehiclerental.entity.Truck): TruckResponse = TruckResponse(
            id = truck.id,
            brand = truck.brand,
            model = truck.model,
            year = truck.year,
            dailyRate = truck.dailyRate,
            available = truck.available,
            vehicleType = truck.getVehicleType(),
            capacity = truck.capacity,
            fourWheelDrive = truck.fourWheelDrive
        )
    }
}