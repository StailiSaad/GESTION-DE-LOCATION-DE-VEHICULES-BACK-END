package com.vehiclerental.repository

import com.vehiclerental.entity.Car
import com.vehiclerental.entity.FuelType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository pour l'accès aux données des voitures.
 * Fournit des méthodes de requête pour les entités Car.
 */
@Repository
interface CarRepository : JpaRepository<Car, Long> {

    /**
     * Trouve toutes les voitures disponibles.
     *
     * @return Liste des voitures disponibles
     */
    fun findByAvailableTrue(): List<Car>

    /**
     * Trouve les voitures par type de carburant.
     *
     * @param fuelType Type de carburant
     * @return Liste des voitures avec le type de carburant spécifié
     */
    fun findByFuelType(fuelType: FuelType): List<Car>

    /**
     * Trouve les voitures par nombre de portes.
     *
     * @param doors Nombre de portes
     * @return Liste des voitures avec le nombre de portes spécifié
     */
    fun findByDoors(doors: Int): List<Car>

    /**
     * Trouve les voitures par type de transmission.
     *
     * @param automatic True pour automatique, false pour manuelle
     * @return Liste des voitures avec le type de transmission spécifié
     */
    fun findByAutomatic(automatic: Boolean): List<Car>

    /**
     * Trouve les voitures disponibles dans une fourchette de prix.
     *
     * @param minPrice Prix minimum par jour
     * @param maxPrice Prix maximum par jour
     * @return Liste des voitures disponibles dans la fourchette de prix
     */
    @Query("SELECT c FROM Car c WHERE c.dailyRate BETWEEN :minPrice AND :maxPrice AND c.available = true")
    fun findAvailableCarsByPriceRange(@Param("minPrice") minPrice: Double, @Param("maxPrice") maxPrice: Double): List<Car>
}