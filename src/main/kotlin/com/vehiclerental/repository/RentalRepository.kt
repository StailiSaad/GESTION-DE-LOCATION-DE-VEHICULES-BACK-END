package com.vehiclerental.repository

import com.vehiclerental.entity.Rental
import com.vehiclerental.entity.RentalStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface RentalRepository : JpaRepository<Rental, Long> {

    fun findByCustomerId(customerId: Long): List<Rental>

    fun findByVehicleId(vehicleId: Long): List<Rental>

    fun findByStatus(status: RentalStatus): List<Rental>

    @Query("SELECT r FROM Rental r WHERE r.vehicle.id = :vehicleId AND r.status = 'ACTIVE' AND ((r.startDate BETWEEN :startDate AND :endDate) OR (r.endDate BETWEEN :startDate AND :endDate))")
    fun findOverlappingRentals(
        @Param("vehicleId") vehicleId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<Rental>

    @Query("SELECT r FROM Rental r WHERE r.endDate < :now AND r.status = 'ACTIVE'")
    fun findOverdueRentals(@Param("now") now: LocalDateTime): List<Rental>
}