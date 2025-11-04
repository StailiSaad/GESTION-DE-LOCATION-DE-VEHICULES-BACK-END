package com.vehiclerental.dto.response

import com.vehiclerental.entity.Vehicle

/**
 * Classe scellée représentant une réponse de véhicule de base.
 * Sert de classe parente pour les types spécifiques de véhicules.
 *
 * @property id Identifiant unique du véhicule
 * @property brand Marque du véhicule
 * @property model Modèle du véhicule
 * @property year Année de fabrication
 * @property dailyRate Tarif journalier de location
 * @property available Statut de disponibilité du véhicule
 * @property vehicleType Type de véhicule
 */
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
        /**
         * Convertit une entité Vehicle en VehicleResponse approprié.
         *
         * @param vehicle Entité Vehicle à convertir
         * @return VehicleResponse correspondant au type de véhicule
         * @throws IllegalArgumentException si le type de véhicule est inconnu
         */
        fun fromEntity(vehicle: Vehicle): VehicleResponse = when (vehicle) {
            is com.vehiclerental.entity.Car -> CarResponse.fromEntity(vehicle)
            is com.vehiclerental.entity.Motorcycle -> MotorcycleResponse.fromEntity(vehicle)
            is com.vehiclerental.entity.Truck -> TruckResponse.fromEntity(vehicle)
            else -> throw IllegalArgumentException("Unknown vehicle type")
        }
    }
}

/**
 * DTO de réponse pour les données d'une voiture.
 *
 * @property doors Nombre de portes
 * @property fuelType Type de carburant
 * @property automatic Si la voiture est automatique
 */
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
        /**
         * Convertit une entité Car en CarResponse.
         *
         * @param car Entité Car à convertir
         * @return CarResponse correspondant
         */
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

/**
 * DTO de réponse pour les données d'une moto.
 *
 * @property engineSize Cylindrée du moteur en cc
 * @property type Type de moto
 */
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
        /**
         * Convertit une entité Motorcycle en MotorcycleResponse.
         *
         * @param motorcycle Entité Motorcycle à convertir
         * @return MotorcycleResponse correspondant
         */
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

/**
 * DTO de réponse pour les données d'un camion.
 *
 * @property capacity Capacité de charge en kg
 * @property fourWheelDrive Si le camion a une transmission intégrale
 */
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
        /**
         * Convertit une entité Truck en TruckResponse.
         *
         * @param truck Entité Truck à convertir
         * @return TruckResponse correspondant
         */
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