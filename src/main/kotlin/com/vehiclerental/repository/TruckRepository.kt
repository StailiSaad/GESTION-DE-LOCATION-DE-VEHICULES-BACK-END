package com.vehiclerental.repository

import com.vehiclerental.entity.Truck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository pour l'accès aux données des camions.
 * Fournit des méthodes de requête pour les entités Truck.
 */
@Repository
interface TruckRepository : JpaRepository<Truck, Long> {

    /**
     * Trouve tous les camions disponibles.
     *
     * @return Liste des camions disponibles
     */
    fun findByAvailableTrue(): List<Truck>

    /**
     * Trouve les camions par type de transmission.
     *
     * @param fourWheelDrive True pour transmission intégrale, false pour propulsion
     * @return Liste des camions avec le type de transmission spécifié
     */
    fun findByFourWheelDrive(fourWheelDrive: Boolean): List<Truck>

    /**
     * Trouve les camions disponibles avec une capacité minimale.
     *
     * @param minCapacity Capacité minimale requise en kg
     * @return Liste des camions disponibles avec la capacité requise
     */
    @Query("SELECT t FROM Truck t WHERE t.capacity >= :minCapacity AND t.available = true")
    fun findAvailableByMinCapacity(@Param("minCapacity") minCapacity: Int): List<Truck>
}