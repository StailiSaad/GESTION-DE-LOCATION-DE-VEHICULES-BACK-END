package com.vehiclerental.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "rentals")
class Rental(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is mandatory")
    val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "Vehicle is mandatory")
    val vehicle: Vehicle,

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is mandatory")
    val startDate: LocalDateTime,

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date is mandatory")
    @Future(message = "End date must be in the future")
    val endDate: LocalDateTime,

    @Column(name = "total_price", nullable = false)
    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: RentalStatus = RentalStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun calculateTotalPrice(): Double {
        val days = java.time.Duration.between(startDate, endDate).toDays().toInt()
        return vehicle.calculateRentalPrice(days)
    }

    fun isActive(): Boolean = status == RentalStatus.ACTIVE
}

enum class RentalStatus {
    ACTIVE, COMPLETED, CANCELLED
}