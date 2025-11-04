package com.vehiclerental.repository

import com.vehiclerental.entity.Motorcycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository pour l'accès aux données des motos.
 * Fournit des méthodes de requête pour les entités Motorcycle.
 */
@Repository
interface MotorcycleRepository : JpaRepository<Motorcycle, Long> {

    /**
     * Trouve toutes les motos disponibles.
     *
     * @return Liste des motos disponibles
     */
    fun findByAvailableTrue(): List<Motorcycle>

    /**
     * Trouve les motos avec une cylindrée supérieure ou égale à la valeur spécifiée.
     *
     * @param engineSize Cylindrée minimale en cc
     * @return Liste des motos avec la cylindrée requise
     */
    fun findByEngineSizeGreaterThanEqual(engineSize: Int): List<Motorcycle>

    /**
     * Trouve les motos par type, insensible à la casse.
     *
     * @param type Type de moto recherché
     * @return Liste des motos du type spécifié
     */
    fun findByTypeContainingIgnoreCase(type: String): List<Motorcycle>

    /**
     * Trouve les motos disponibles dans une fourchette de cylindrée.
     *
     * @param minEngine Cylindrée minimale en cc
     * @param maxEngine Cylindrée maximale en cc
     * @return Liste des motos disponibles dans la fourchette de cylindrée
     */
    @Query("SELECT m FROM Motorcycle m WHERE m.engineSize BETWEEN :minEngine AND :maxEngine AND m.available = true")
    fun findAvailableByEngineSizeRange(
        @Param("minEngine") minEngine: Int,
        @Param("maxEngine") maxEngine: Int
    ): List<Motorcycle>
}