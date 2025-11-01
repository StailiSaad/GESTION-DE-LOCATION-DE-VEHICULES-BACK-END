package com.vehiclerental.repository

import com.vehiclerental.entity.Motorcycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MotorcycleRepository : JpaRepository<Motorcycle, Long> {

    fun findByAvailableTrue(): List<Motorcycle>

    fun findByEngineSizeGreaterThanEqual(engineSize: Int): List<Motorcycle>

    fun findByTypeContainingIgnoreCase(type: String): List<Motorcycle>

    @Query("SELECT m FROM Motorcycle m WHERE m.engineSize BETWEEN :minEngine AND :maxEngine AND m.available = true")
    fun findAvailableByEngineSizeRange(
        @Param("minEngine") minEngine: Int,
        @Param("maxEngine") maxEngine: Int
    ): List<Motorcycle>
}