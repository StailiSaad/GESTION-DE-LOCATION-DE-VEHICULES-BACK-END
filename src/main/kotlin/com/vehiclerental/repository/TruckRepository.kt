package com.vehiclerental.repository

import com.vehiclerental.entity.Truck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TruckRepository : JpaRepository<Truck, Long> {

    fun findByAvailableTrue(): List<Truck>

    fun findByFourWheelDrive(fourWheelDrive: Boolean): List<Truck>

    @Query("SELECT t FROM Truck t WHERE t.capacity >= :minCapacity AND t.available = true")
    fun findAvailableByMinCapacity(@Param("minCapacity") minCapacity: Int): List<Truck>
}