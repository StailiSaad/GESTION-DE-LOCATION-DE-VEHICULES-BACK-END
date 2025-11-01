package com.vehiclerental.repository

import com.vehiclerental.entity.Car
import com.vehiclerental.entity.FuelType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<Car, Long> {

    fun findByAvailableTrue(): List<Car>

    fun findByFuelType(fuelType: FuelType): List<Car>

    fun findByDoors(doors: Int): List<Car>

    fun findByAutomatic(automatic: Boolean): List<Car>

    @Query("SELECT c FROM Car c WHERE c.dailyRate BETWEEN :minPrice AND :maxPrice AND c.available = true")
    fun findAvailableCarsByPriceRange(@Param("minPrice") minPrice: Double, @Param("maxPrice") maxPrice: Double): List<Car>
}