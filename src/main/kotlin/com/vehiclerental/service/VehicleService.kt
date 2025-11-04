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

/**
 * Service pour la gestion des véhicules.
 * Fournit les opérations CRUD et de recherche pour tous les types de véhicules.
 *
 * @property vehicleRepository Repository générique pour l'accès aux données des véhicules
 * @property carRepository Repository spécifique pour les voitures
 * @property motorcycleRepository Repository spécifique pour les motos
 * @property truckRepository Repository spécifique pour les camions
 */
@Service
@Transactional
class VehicleService(
    private val vehicleRepository: VehicleRepository,
    private val carRepository: CarRepository,
    private val motorcycleRepository: MotorcycleRepository,
    private val truckRepository: TruckRepository
) {

    /**
     * Récupère tous les véhicules.
     *
     * @return Liste de tous les véhicules sous forme de VehicleResponse
     */
    fun getAllVehicles(): List<VehicleResponse> {
        return vehicleRepository.findAll().map { VehicleResponse.fromEntity(it) }
    }

    /**
     * Récupère tous les véhicules disponibles.
     *
     * @return Liste des véhicules disponibles sous forme de VehicleResponse
     */
    fun getAvailableVehicles(): List<VehicleResponse> {
        return vehicleRepository.findByAvailableTrue().map { VehicleResponse.fromEntity(it) }
    }

    /**
     * Récupère un véhicule par son identifiant.
     *
     * @param id Identifiant du véhicule
     * @return VehicleResponse correspondant au véhicule
     * @throws IllegalArgumentException si le véhicule n'est pas trouvé
     */
    fun getVehicleById(id: Long): VehicleResponse {
        val vehicle = vehicleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Vehicle not found with id: $id") }
        return VehicleResponse.fromEntity(vehicle)
    }

    /**
     * Crée une nouvelle voiture.
     *
     * @param carRequest Données de création de la voiture
     * @return VehicleResponse de la voiture créée
     * @throws IllegalArgumentException si le type de carburant est invalide
     */
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

    /**
     * Crée une nouvelle moto.
     *
     * @param motorcycleRequest Données de création de la moto
     * @return VehicleResponse de la moto créée
     */
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

    /**
     * Crée un nouveau camion.
     *
     * @param truckRequest Données de création du camion
     * @return VehicleResponse du camion créé
     */
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

    /**
     * Met à jour la disponibilité d'un véhicule.
     *
     * @param id Identifiant du véhicule
     * @param available Nouveau statut de disponibilité
     * @return VehicleResponse du véhicule mis à jour
     * @throws IllegalArgumentException si le véhicule n'est pas trouvé
     */
    fun updateVehicleAvailability(id: Long, available: Boolean): VehicleResponse {
        val vehicle = vehicleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Vehicle not found with id: $id") }

        vehicle.available = available
        val updatedVehicle = vehicleRepository.save(vehicle)
        return VehicleResponse.fromEntity(updatedVehicle)
    }

    /**
     * Supprime un véhicule par son identifiant.
     *
     * @param id Identifiant du véhicule à supprimer
     * @throws IllegalArgumentException si le véhicule n'est pas trouvé
     */
    fun deleteVehicle(id: Long) {
        if (!vehicleRepository.existsById(id)) {
            throw IllegalArgumentException("Vehicle not found with id: $id")
        }
        vehicleRepository.deleteById(id)
    }

    /**
     * Recherche des véhicules selon différents critères.
     * Tous les paramètres sont optionnels - seuls les critères fournis sont appliqués.
     *
     * @param brand Marque recherchée (optionnel)
     * @param model Modèle recherché (optionnel)
     * @param minYear Année minimale (optionnel)
     * @param maxYear Année maximale (optionnel)
     * @param minPrice Prix minimum par jour (optionnel)
     * @param maxPrice Prix maximum par jour (optionnel)
     * @return Liste des véhicules correspondants aux critères sous forme de VehicleResponse
     */
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