package com.vehiclerental.repository

import com.vehiclerental.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository pour l'accès aux données des véhicules.
 * Fournit des méthodes de requête génériques pour toutes les entités Vehicle.
 */
@Repository
interface VehicleRepository : JpaRepository<Vehicle, Long> {

    /**
     * Trouve tous les véhicules disponibles.
     *
     * @return Liste des véhicules disponibles
     */
    fun findByAvailableTrue(): List<Vehicle>

    /**
     * Trouve les véhicules par marque, insensible à la casse.
     *
     * @param brand Marque recherchée
     * @return Liste des véhicules de la marque spécifiée
     */
    fun findByBrandContainingIgnoreCase(brand: String): List<Vehicle>

    /**
     * Trouve les véhicules par modèle, insensible à la casse.
     *
     * @param model Modèle recherché
     * @return Liste des véhicules du modèle spécifié
     */
    fun findByModelContainingIgnoreCase(model: String): List<Vehicle>

    /**
     * Trouve les véhicules dans une fourchette d'années.
     *
     * @param minYear Année minimale
     * @param maxYear Année maximale
     * @return Liste des véhicules dans la fourchette d'années
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year >= :minYear AND v.year <= :maxYear")
    fun findByYearBetween(@Param("minYear") minYear: Int, @Param("maxYear") maxYear: Int): List<Vehicle>

    /**
     * Trouve les véhicules dans une fourchette de prix journalier.
     *
     * @param minPrice Prix minimum par jour
     * @param maxPrice Prix maximum par jour
     * @return Liste des véhicules dans la fourchette de prix
     */
    @Query("SELECT v FROM Vehicle v WHERE v.dailyRate BETWEEN :minPrice AND :maxPrice")
    fun findByDailyRateBetween(@Param("minPrice") minPrice: Double, @Param("maxPrice") maxPrice: Double): List<Vehicle>
}