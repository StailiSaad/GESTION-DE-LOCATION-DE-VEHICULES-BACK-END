package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

/**
 * Entité représentant une location de véhicule.
 * Relie un client à un véhicule pour une période donnée.
 *
 * @property id Identifiant unique de la location
 * @property customer Client effectuant la location
 * @property vehicle Véhicule loué
 * @property startDate Date de début de location
 * @property endDate Date de fin de location
 * @property totalPrice Prix total de la location
 * @property status Statut actuel de la location
 * @property createdAt Date et heure de création de la location
 */
@Entity
@Table(name = "rentals")
class Rental(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is mandatory")
    val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "Vehicle is mandatory")
    val vehicle: Vehicle,

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is mandatory")
    val startDate: LocalDateTime,

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date is mandatory")
    @Future(message = "End date must be in the future")
    val endDate: LocalDateTime,

    @Column(name = "total_price", nullable = false)
    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: RentalStatus = RentalStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * Calcule le prix total de la location basé sur la durée et le tarif du véhicule.
     *
     * @return Double représentant le prix total calculé
     */
    fun calculateTotalPrice(): Double {
        val days = java.time.Duration.between(startDate, endDate).toDays().toInt()
        return vehicle.calculateRentalPrice(days)
    }

    /**
     * Vérifie si la location est active.
     *
     * @return Boolean true si la location est active, false sinon
     */
    fun isActive(): Boolean = status == RentalStatus.ACTIVE
}

/**
 * Enumération des statuts possibles d'une location.
 */
enum class RentalStatus {
    ACTIVE, COMPLETED, CANCELLED
}