package com.vehiclerental.repository

import com.vehiclerental.entity.Rental
import com.vehiclerental.entity.RentalStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Repository pour l'accès aux données des locations.
 * Fournit des méthodes de requête pour les entités Rental.
 */
@Repository
interface RentalRepository : JpaRepository<Rental, Long> {

    /**
     * Trouve toutes les locations d'un client spécifique.
     *
     * @param customerId Identifiant du client
     * @return Liste des locations du client
     */
    fun findByCustomerId(customerId: Long): List<Rental>

    /**
     * Trouve toutes les locations d'un véhicule spécifique.
     *
     * @param vehicleId Identifiant du véhicule
     * @return Liste des locations du véhicule
     */
    fun findByVehicleId(vehicleId: Long): List<Rental>

    /**
     * Trouve les locations par statut.
     *
     * @param status Statut des locations recherchées
     * @return Liste des locations avec le statut spécifié
     */
    fun findByStatus(status: RentalStatus): List<Rental>

    /**
     * Trouve les locations actives qui chevauchent une période donnée pour un véhicule.
     *
     * @param vehicleId Identifiant du véhicule
     * @param startDate Date de début de la période
     * @param endDate Date de fin de la période
     * @return Liste des locations actives qui chevauchent la période
     */
    @Query("SELECT r FROM Rental r WHERE r.vehicle.id = :vehicleId AND r.status = 'ACTIVE' AND ((r.startDate BETWEEN :startDate AND :endDate) OR (r.endDate BETWEEN :startDate AND :endDate))")
    fun findOverlappingRentals(
        @Param("vehicleId") vehicleId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<Rental>

    /**
     * Trouve les locations en retard (date de fin dépassée mais toujours actives).
     *
     * @param now Date et heure actuelles
     * @return Liste des locations en retard
     */
    @Query("SELECT r FROM Rental r WHERE r.endDate < :now AND r.status = 'ACTIVE'")
    fun findOverdueRentals(@Param("now") now: LocalDateTime): List<Rental>
}