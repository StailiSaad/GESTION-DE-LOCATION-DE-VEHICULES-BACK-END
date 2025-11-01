package com.vehiclerental.controller

import com.vehiclerental.dto.request.CustomerRequest
import com.vehiclerental.dto.response.CustomerResponse
import com.vehiclerental.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management APIs")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping
    @Operation(summary = "Get all customers")
    fun getAllCustomers(): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.getAllCustomers()
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer = customerService.getCustomerById(id)
        return ResponseEntity.ok(customer)
    }

    @PostMapping
    @Operation(summary = "Create a new customer")
    fun createCustomer(@Valid @RequestBody customerRequest: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = customerService.createCustomer(customerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(customer)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    fun updateCustomer(
        @PathVariable id: Long,
        @Valid @RequestBody customerRequest: CustomerRequest
    ): ResponseEntity<CustomerResponse> {
        val customer = customerService.updateCustomer(id, customerRequest)
        return ResponseEntity.ok(customer)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.deleteCustomer(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers by name")
    fun searchCustomers(@RequestParam name: String): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.searchCustomersByName(name)
        return ResponseEntity.ok(customers)
    }
}