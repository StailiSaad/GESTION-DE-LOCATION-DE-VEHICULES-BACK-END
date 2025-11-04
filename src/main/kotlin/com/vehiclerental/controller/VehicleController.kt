package com.vehiclerental.controller

import com.vehiclerental.dto.request.CarRequest
import com.vehiclerental.dto.request.MotorcycleRequest
import com.vehiclerental.dto.request.TruckRequest
import com.vehiclerental.dto.response.VehicleResponse
import com.vehiclerental.service.VehicleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Contrôleur REST pour la gestion des véhicules.
 * Fournit des endpoints CRUD pour les voitures, motos et camions.
 *
 * @property vehicleService Service pour la logique métier des véhicules
 */
@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Vehicle management APIs")
class VehicleController(private val vehicleService: VehicleService) {

    /**
     * Récupère la liste de tous les véhicules.
     *
     * @return ResponseEntity contenant la liste de tous les véhicules
     */
    @GetMapping
    @Operation(summary = "Get all vehicles")
    fun getAllVehicles(): ResponseEntity<List<VehicleResponse>> {
        val vehicles = vehicleService.getAllVehicles()
        return ResponseEntity.ok(vehicles)
    }

    /**
     * Récupère la liste des véhicules disponibles à la location.
     *
     * @return ResponseEntity contenant la liste des véhicules disponibles
     */
    @GetMapping("/available")
    @Operation(summary = "Get available vehicles")
    fun getAvailableVehicles(): ResponseEntity<List<VehicleResponse>> {
        val vehicles = vehicleService.getAvailableVehicles()
        return ResponseEntity.ok(vehicles)
    }

    /**
     * Récupère un véhicule par son identifiant.
     *
     * @param id Identifiant du véhicule
     * @return ResponseEntity contenant le véhicule trouvé
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID")
    fun getVehicleById(@PathVariable id: Long): ResponseEntity<VehicleResponse> {
        val vehicle = vehicleService.getVehicleById(id)
        return ResponseEntity.ok(vehicle)
    }

    /**
     * Crée une nouvelle voiture.
     *
     * @param carRequest Données de création de la voiture
     * @return ResponseEntity contenant la voiture créée avec statut 201
     */
    @PostMapping("/cars")
    @Operation(summary = "Create a new car")
    fun createCar(@Valid @RequestBody carRequest: CarRequest): ResponseEntity<VehicleResponse> {
        val car = vehicleService.createCar(carRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(car)
    }

    /**
     * Crée une nouvelle moto.
     *
     * @param motorcycleRequest Données de création de la moto
     * @return ResponseEntity contenant la moto créée avec statut 201
     */
    @PostMapping("/motorcycles")
    @Operation(summary = "Create a new motorcycle")
    fun createMotorcycle(@Valid @RequestBody motorcycleRequest: MotorcycleRequest): ResponseEntity<VehicleResponse> {
        val motorcycle = vehicleService.createMotorcycle(motorcycleRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(motorcycle)
    }

    /**
     * Crée un nouveau camion.
     *
     * @param truckRequest Données de création du camion
     * @return ResponseEntity contenant le camion créé avec statut 201
     */
    @PostMapping("/trucks")
    @Operation(summary = "Create a new truck")
    fun createTruck(@Valid @RequestBody truckRequest: TruckRequest): ResponseEntity<VehicleResponse> {
        val truck = vehicleService.createTruck(truckRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(truck)
    }

    /**
     * Met à jour la disponibilité d'un véhicule.
     *
     * @param id Identifiant du véhicule
     * @param available Nouveau statut de disponibilité
     * @return ResponseEntity contenant le véhicule mis à jour
     */
    @PatchMapping("/{id}/availability")
    @Operation(summary = "Update vehicle availability")
    fun updateVehicleAvailability(
        @PathVariable id: Long,
        @RequestParam available: Boolean
    ): ResponseEntity<VehicleResponse> {
        val vehicle = vehicleService.updateVehicleAvailability(id, available)
        return ResponseEntity.ok(vehicle)
    }

    /**
     * Supprime un véhicule par son identifiant.
     *
     * @param id Identifiant du véhicule à supprimer
     * @return ResponseEntity vide avec statut 204
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle")
    fun deleteVehicle(@PathVariable id: Long): ResponseEntity<Void> {
        vehicleService.deleteVehicle(id)
        return ResponseEntity.noContent().build()
    }

    /**
     * Recherche des véhicules selon différents critères.
     *
     * @param brand Marque du véhicule (optionnel)
     * @param model Modèle du véhicule (optionnel)
     * @param minYear Année minimale (optionnel)
     * @param maxYear Année maximale (optionnel)
     * @param minPrice Prix minimum par jour (optionnel)
     * @param maxPrice Prix maximum par jour (optionnel)
     * @return ResponseEntity contenant la liste des véhicules correspondants aux critères
     */
    @GetMapping("/search")
    @Operation(summary = "Search vehicles by criteria")
    fun searchVehicles(
        @RequestParam(required = false) brand: String?,
        @RequestParam(required = false) model: String?,
        @RequestParam(required = false) minYear: Int?,
        @RequestParam(required = false) maxYear: Int?,
        @RequestParam(required = false) minPrice: Double?,
        @RequestParam(required = false) maxPrice: Double?
    ): ResponseEntity<List<VehicleResponse>> {
        val vehicles = vehicleService.searchVehicles(brand, model, minYear, maxYear, minPrice, maxPrice)
        return ResponseEntity.ok(vehicles)
    }
}