package com.vehiclerental.dto.response

data class CustomerResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val driverLicenseNumber: String,
    val createdAt: String
)

data class RentalResponse(
    val id: Long,
    val customer: CustomerResponse,
    val vehicle: VehicleResponse,
    val startDate: String,
    val endDate: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: String
)