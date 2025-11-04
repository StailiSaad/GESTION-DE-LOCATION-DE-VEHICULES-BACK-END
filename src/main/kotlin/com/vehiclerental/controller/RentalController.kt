package com.vehiclerental.controller

import com.vehiclerental.dto.request.RentalRequest
import com.vehiclerental.dto.response.RentalResponse
import com.vehiclerental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Contrôleur REST pour la gestion des locations de véhicules.
 * Fournit des endpoints pour gérer le cycle de vie des locations.
 *
 * @property rentalService Service pour la logique métier des locations
 */
@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Rental management APIs")
class RentalController(private val rentalService: RentalService) {

    /**
     * Récupère la liste de toutes les locations.
     *
     * @return ResponseEntity contenant la liste des locations
     */
    @GetMapping
    @Operation(summary = "Get all rentals")
    fun getAllRentals(): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getAllRentals()
        return ResponseEntity.ok(rentals)
    }

    /**
     * Récupère une location par son identifiant.
     *
     * @param id Identifiant de la location
     * @return ResponseEntity contenant la location trouvée
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get rental by ID")
    fun getRentalById(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.getRentalById(id)
        return ResponseEntity.ok(rental)
    }

    /**
     * Crée une nouvelle location.
     *
     * @param rentalRequest Données de création de la location
     * @return ResponseEntity contenant la location créée avec statut 201
     */
    @PostMapping
    @Operation(summary = "Create a new rental")
    fun createRental(@Valid @RequestBody rentalRequest: RentalRequest): ResponseEntity<RentalResponse> {
        val rental = rentalService.createRental(rentalRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(rental)
    }

    /**
     * Marque une location comme terminée.
     *
     * @param id Identifiant de la location à terminer
     * @return ResponseEntity contenant la location mise à jour
     */
    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete a rental")
    fun completeRental(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.completeRental(id)
        return ResponseEntity.ok(rental)
    }

    /**
     * Annule une location existante.
     *
     * @param id Identifiant de la location à annuler
     * @return ResponseEntity contenant la location annulée
     */
    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a rental")
    fun cancelRental(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.cancelRental(id)
        return ResponseEntity.ok(rental)
    }

    /**
     * Récupère les locations d'un client spécifique.
     *
     * @param customerId Identifiant du client
     * @return ResponseEntity contenant la liste des locations du client
     */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get rentals by customer ID")
    fun getCustomerRentals(@PathVariable customerId: Long): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getCustomerRentals(customerId)
        return ResponseEntity.ok(rentals)
    }

    /**
     * Récupère les locations en retard.
     *
     * @return ResponseEntity contenant la liste des locations en retard
     */
    @GetMapping("/overdue")
    @Operation(summary = "Get overdue rentals")
    fun getOverdueRentals(): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getOverdueRentals()
        return ResponseEntity.ok(rentals)
    }
}