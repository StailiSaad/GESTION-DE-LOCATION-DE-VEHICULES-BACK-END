package com.vehiclerental.controller

import com.vehiclerental.dto.request.RentalRequest
import com.vehiclerental.dto.response.RentalResponse
import com.vehiclerental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Rental management APIs")
class RentalController(private val rentalService: RentalService) {

    @GetMapping
    @Operation(summary = "Get all rentals")
    fun getAllRentals(): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getAllRentals()
        return ResponseEntity.ok(rentals)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental by ID")
    fun getRentalById(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.getRentalById(id)
        return ResponseEntity.ok(rental)
    }

    @PostMapping
    @Operation(summary = "Create a new rental")
    fun createRental(@Valid @RequestBody rentalRequest: RentalRequest): ResponseEntity<RentalResponse> {
        val rental = rentalService.createRental(rentalRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(rental)
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete a rental")
    fun completeRental(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.completeRental(id)
        return ResponseEntity.ok(rental)
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a rental")
    fun cancelRental(@PathVariable id: Long): ResponseEntity<RentalResponse> {
        val rental = rentalService.cancelRental(id)
        return ResponseEntity.ok(rental)
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get rentals by customer ID")
    fun getCustomerRentals(@PathVariable customerId: Long): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getCustomerRentals(customerId)
        return ResponseEntity.ok(rentals)
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue rentals")
    fun getOverdueRentals(): ResponseEntity<List<RentalResponse>> {
        val rentals = rentalService.getOverdueRentals()
        return ResponseEntity.ok(rentals)
    }
}