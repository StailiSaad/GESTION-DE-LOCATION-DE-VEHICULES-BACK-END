package com.vehiclerental.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CustomerRequest(
    @field:NotBlank(message = "First name is mandatory")
    val firstName: String,

    @field:NotBlank(message = "Last name is mandatory")
    val lastName: String,

    @field:Email(message = "Email should be valid")
    val email: String,

    @field:Pattern(regexp = "\\+?[0-9.\\-\\s()]{10,}", message = "Phone number should be valid")
    val phoneNumber: String,

    @field:NotBlank(message = "Driver license number is mandatory")
    val driverLicenseNumber: String
)