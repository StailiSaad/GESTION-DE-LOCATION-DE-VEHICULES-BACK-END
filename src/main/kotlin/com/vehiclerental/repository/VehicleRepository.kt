package com.vehiclerental.repository

import com.vehiclerental.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : JpaRepository<Vehicle, Long> {

    fun findByAvailableTrue(): List<Vehicle>

    fun findByBrandContainingIgnoreCase(brand: String): List<Vehicle>

    fun findByModelContainingIgnoreCase(model: String): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.year >= :minYear AND v.year <= :maxYear")
    fun findByYearBetween(@Param("minYear") minYear: Int, @Param("maxYear") maxYear: Int): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.dailyRate BETWEEN :minPrice AND :maxPrice")
    fun findByDailyRateBetween(@Param("minPrice") minPrice: Double, @Param("maxPrice") maxPrice: Double): List<Vehicle>
}