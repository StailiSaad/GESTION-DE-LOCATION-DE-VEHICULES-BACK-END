package com.vehiclerental.service

import com.vehiclerental.dto.request.RentalRequest
import com.vehiclerental.dto.response.RentalResponse
import com.vehiclerental.entity.Rental
import com.vehiclerental.entity.RentalStatus
import com.vehiclerental.repository.CustomerRepository
import com.vehiclerental.repository.RentalRepository
import com.vehiclerental.repository.VehicleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * Service pour la gestion des locations de véhicules.
 * Gère le cycle de vie complet des locations.
 *
 * @property rentalRepository Repository pour l'accès aux données des locations
 * @property customerRepository Repository pour l'accès aux données des clients
 * @property vehicleRepository Repository pour l'accès aux données des véhicules
 */
@Service
@Transactional
class RentalService(
    private val rentalRepository: RentalRepository,
    private val customerRepository: CustomerRepository,
    private val vehicleRepository: VehicleRepository
) {

    /**
     * Récupère toutes les locations.
     *
     * @return Liste de toutes les locations sous forme de RentalResponse
     */
    fun getAllRentals(): List<RentalResponse> {
        return rentalRepository.findAll().map { it.toResponse() }
    }

    /**
     * Récupère une location par son identifiant.
     *
     * @param id Identifiant de la location
     * @return RentalResponse correspondant à la location
     * @throws IllegalArgumentException si la location n'est pas trouvée
     */
    fun getRentalById(id: Long): RentalResponse {
        val rental = rentalRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Rental not found with id: $id") }
        return rental.toResponse()
    }

    /**
     * Crée une nouvelle location.
     *
     * @param rentalRequest Données de création de la location
     * @return RentalResponse de la location créée
     * @throws IllegalArgumentException si le client, le véhicule n'existe pas,
     * si le véhicule n'est pas disponible, ou s'il y a des chevauchements de dates
     */
    fun createRental(rentalRequest: RentalRequest): RentalResponse {
        val customer = customerRepository.findById(rentalRequest.customerId)
            .orElseThrow { IllegalArgumentException("Customer not found with id: ${rentalRequest.customerId}") }

        val vehicle = vehicleRepository.findById(rentalRequest.vehicleId)
            .orElseThrow { IllegalArgumentException("Vehicle not found with id: ${rentalRequest.vehicleId}") }

        // Vérifier la disponibilité du véhicule
        if (!vehicle.available) {
            throw IllegalArgumentException("Vehicle is not available for rental")
        }

        // Vérifier les chevauchements de location
        val overlappingRentals = rentalRepository.findOverlappingRentals(
            vehicle.id,
            rentalRequest.startDate,
            rentalRequest.endDate
        )
        if (overlappingRentals.isNotEmpty()) {
            throw IllegalArgumentException("Vehicle is already rented for the selected dates")
        }

        // Vérifier que la date de fin est après la date de début
        if (rentalRequest.endDate.isBefore(rentalRequest.startDate)) {
            throw IllegalArgumentException("End date must be after start date")
        }

        // Calculer le prix total
        val days = java.time.Duration.between(rentalRequest.startDate, rentalRequest.endDate).toDays().toInt()
        val totalPrice = vehicle.calculateRentalPrice(days)

        val rental = Rental(
            customer = customer,
            vehicle = vehicle,
            startDate = rentalRequest.startDate,
            endDate = rentalRequest.endDate,
            totalPrice = totalPrice
        )

        // Marquer le véhicule comme indisponible
        vehicle.available = false

        val savedRental = rentalRepository.save(rental)
        return savedRental.toResponse()
    }

    /**
     * Termine une location active.
     *
     * @param id Identifiant de la location à terminer
     * @return RentalResponse de la location terminée
     * @throws IllegalArgumentException si la location n'est pas trouvée ou n'est pas active
     */
    fun completeRental(id: Long): RentalResponse {
        val rental = rentalRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Rental not found with id: $id") }

        if (rental.status != RentalStatus.ACTIVE) {
            throw IllegalArgumentException("Rental is not active")
        }

        rental.status = RentalStatus.COMPLETED
        rental.vehicle.available = true

        val updatedRental = rentalRepository.save(rental)
        return updatedRental.toResponse()
    }

    /**
     * Annule une location active.
     *
     * @param id Identifiant de la location à annuler
     * @return RentalResponse de la location annulée
     * @throws IllegalArgumentException si la location n'est pas trouvée ou n'est pas active
     */
    fun cancelRental(id: Long): RentalResponse {
        val rental = rentalRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Rental not found with id: $id") }

        if (rental.status != RentalStatus.ACTIVE) {
            throw IllegalArgumentException("Only active rentals can be cancelled")
        }

        rental.status = RentalStatus.CANCELLED
        rental.vehicle.available = true

        val updatedRental = rentalRepository.save(rental)
        return updatedRental.toResponse()
    }

    /**
     * Récupère toutes les locations d'un client spécifique.
     *
     * @param customerId Identifiant du client
     * @return Liste des locations du client sous forme de RentalResponse
     */
    fun getCustomerRentals(customerId: Long): List<RentalResponse> {
        return rentalRepository.findByCustomerId(customerId).map { it.toResponse() }
    }

    /**
     * Récupère les locations en retard.
     *
     * @return Liste des locations en retard sous forme de RentalResponse
     */
    fun getOverdueRentals(): List<RentalResponse> {
        return rentalRepository.findOverdueRentals(LocalDateTime.now()).map { it.toResponse() }
    }

    /**
     * Extension function pour convertir une entité Rental en RentalResponse.
     *
     * @return RentalResponse correspondant à l'entité Rental
     */
    private fun Rental.toResponse(): RentalResponse = RentalResponse(
        id = this.id,
        customer = this.customer.toResponse(),
        vehicle = com.vehiclerental.dto.response.VehicleResponse.fromEntity(this.vehicle),
        startDate = this.startDate.toString(),
        endDate = this.endDate.toString(),
        totalPrice = this.totalPrice,
        status = this.status.name,
        createdAt = this.createdAt.toString()
    )

    /**
     * Extension function pour convertir une entité Customer en CustomerResponse.
     *
     * @return CustomerResponse correspondant à l'entité Customer
     */
    private fun com.vehiclerental.entity.Customer.toResponse(): com.vehiclerental.dto.response.CustomerResponse =
        com.vehiclerental.dto.response.CustomerResponse(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            fullName = this.getFullName(),
            email = this.email,
            phoneNumber = this.phoneNumber,
            driverLicenseNumber = this.driverLicenseNumber,
            createdAt = this.createdAt.toString()
        )
}