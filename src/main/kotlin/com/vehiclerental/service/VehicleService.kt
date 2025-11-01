package com.vehiclerental.service

import com.vehiclerental.dto.request.CarRequest
import com.vehiclerental.dto.request.MotorcycleRequest
import com.vehiclerental.dto.request.TruckRequest
import com.vehiclerental.dto.response.VehicleResponse
import com.vehiclerental.entity.Car
import com.vehiclerental.entity.FuelType
import com.vehiclerental.entity.Motorcycle
import com.vehiclerental.entity.Truck
import com.vehiclerental.repository.CarRepository
import com.vehiclerental.repository.MotorcycleRepository
import com.vehiclerental.repository.TruckRepository
import com.vehiclerental.repository.VehicleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VehicleService(
    private val vehicleRepository: VehicleRepository,
    private val carRepository: CarRepository,
    private val motorcycleRepository: MotorcycleRepository,
    private val truckRepository: TruckRepository
) {

    fun getAllVehicles(): List<VehicleResponse> {
        return vehicleRepository.findAll().map { VehicleResponse.fromEntity(it) }
    }

    fun getAvailableVehicles(): List<VehicleResponse> {
        return vehicleRepository.findByAvailableTrue().map { VehicleResponse.fromEntity(it) }
    }

    fun getVehicleById(id: Long): VehicleResponse {
        val vehicle = vehicleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Vehicle not found with id: $id") }
        return VehicleResponse.fromEntity(vehicle)
    }

    fun createCar(carRequest: CarRequest): VehicleResponse {
        val fuelType = try {
            FuelType.valueOf(carRequest.fuelType.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid fuel type: ${carRequest.fuelType}")
        }

        val car = Car(
            brand = carRequest.brand,
            model = carRequest.model,
            year = carRequest.year,
            dailyRate = carRequest.dailyRate,
            doors = carRequest.doors,
            fuelType = fuelType,
            automatic = carRequest.automatic
        )

        val savedCar = carRepository.save(car)
        return VehicleResponse.fromEntity(savedCar)
    }

    fun createMotorcycle(motorcycleRequest: MotorcycleRequest): VehicleResponse {
        val motorcycle = Motorcycle(
            brand = motorcycleRequest.brand,
            model = motorcycleRequest.model,
            year = motorcycleRequest.year,
            dailyRate = motorcycleRequest.dailyRate,
            engineSize = motorcycleRequest.engineSize,
            type = motorcycleRequest.type
        )

        val savedMotorcycle = motorcycleRepository.save(motorcycle)
        return VehicleResponse.fromEntity(savedMotorcycle)
    }

    fun createTruck(truckRequest: TruckRequest): VehicleResponse {
        val truck = Truck(
            brand = truckRequest.brand,
            model = truckRequest.model,
            year = truckRequest.year,
            dailyRate = truckRequest.dailyRate,
            capacity = truckRequest.capacity,
            fourWheelDrive = truckRequest.fourWheelDrive
        )

        val savedTruck = truckRepository.save(truck)
        return VehicleResponse.fromEntity(savedTruck)
    }

    fun updateVehicleAvailability(id: Long, available: Boolean): VehicleResponse {
        val vehicle = vehicleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Vehicle not found with id: $id") }

        vehicle.available = available
        val updatedVehicle = vehicleRepository.save(vehicle)
        return VehicleResponse.fromEntity(updatedVehicle)
    }

    fun deleteVehicle(id: Long) {
        if (!vehicleRepository.existsById(id)) {
            throw IllegalArgumentException("Vehicle not found with id: $id")
        }
        vehicleRepository.deleteById(id)
    }

    fun searchVehicles(
        brand: String?,
        model: String?,
        minYear: Int?,
        maxYear: Int?,
        minPrice: Double?,
        maxPrice: Double?
    ): List<VehicleResponse> {
        var vehicles = vehicleRepository.findAll()

        // Filtre par marque
        brand?.let { searchBrand ->
            vehicles = vehicles.filter { it.brand.contains(searchBrand, ignoreCase = true) }
        }

        // Filtre par modèle
        model?.let { searchModel ->
            vehicles = vehicles.filter { it.model.contains(searchModel, ignoreCase = true) }
        }

        // Filtre par année minimum
        minYear?.let { yearMin ->
            vehicles = vehicles.filter { it.year >= yearMin }
        }

        // Filtre par année maximum
        maxYear?.let { yearMax ->
            vehicles = vehicles.filter { it.year <= yearMax }
        }

        // Filtre par prix minimum
        minPrice?.let { priceMin ->
            vehicles = vehicles.filter { it.dailyRate >= priceMin }
        }

        // Filtre par prix maximum
        maxPrice?.let { priceMax ->
            vehicles = vehicles.filter { it.dailyRate <= priceMax }
        }

        return vehicles.map { VehicleResponse.fromEntity(it) }
    }
}