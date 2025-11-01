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

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Vehicle management APIs")
class VehicleController(private val vehicleService: VehicleService) {

    @GetMapping
    @Operation(summary = "Get all vehicles")
    fun getAllVehicles(): ResponseEntity<List<VehicleResponse>> {
        val vehicles = vehicleService.getAllVehicles()
        return ResponseEntity.ok(vehicles)
    }

    @GetMapping("/available")
    @Operation(summary = "Get available vehicles")
    fun getAvailableVehicles(): ResponseEntity<List<VehicleResponse>> {
        val vehicles = vehicleService.getAvailableVehicles()
        return ResponseEntity.ok(vehicles)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID")
    fun getVehicleById(@PathVariable id: Long): ResponseEntity<VehicleResponse> {
        val vehicle = vehicleService.getVehicleById(id)
        return ResponseEntity.ok(vehicle)
    }

    @PostMapping("/cars")
    @Operation(summary = "Create a new car")
    fun createCar(@Valid @RequestBody carRequest: CarRequest): ResponseEntity<VehicleResponse> {
        val car = vehicleService.createCar(carRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(car)
    }

    @PostMapping("/motorcycles")
    @Operation(summary = "Create a new motorcycle")
    fun createMotorcycle(@Valid @RequestBody motorcycleRequest: MotorcycleRequest): ResponseEntity<VehicleResponse> {
        val motorcycle = vehicleService.createMotorcycle(motorcycleRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(motorcycle)
    }

    @PostMapping("/trucks")
    @Operation(summary = "Create a new truck")
    fun createTruck(@Valid @RequestBody truckRequest: TruckRequest): ResponseEntity<VehicleResponse> {
        val truck = vehicleService.createTruck(truckRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(truck)
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Update vehicle availability")
    fun updateVehicleAvailability(
        @PathVariable id: Long,
        @RequestParam available: Boolean
    ): ResponseEntity<VehicleResponse> {
        val vehicle = vehicleService.updateVehicleAvailability(id, available)
        return ResponseEntity.ok(vehicle)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle")
    fun deleteVehicle(@PathVariable id: Long): ResponseEntity<Void> {
        vehicleService.deleteVehicle(id)
        return ResponseEntity.noContent().build()
    }

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