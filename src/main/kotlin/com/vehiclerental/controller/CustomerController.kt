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

/**
 * Contrôleur REST pour la gestion des clients.
 * Fournit des endpoints CRUD pour les opérations sur les clients.
 *
 * @property customerService Service pour la logique métier des clients
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management APIs")
class CustomerController(private val customerService: CustomerService) {

    /**
     * Récupère la liste de tous les clients.
     *
     * @return ResponseEntity contenant la liste des clients
     */
    @GetMapping
    @Operation(summary = "Get all customers")
    fun getAllCustomers(): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.getAllCustomers()
        return ResponseEntity.ok(customers)
    }

    /**
     * Récupère un client par son identifiant.
     *
     * @param id Identifiant du client
     * @return ResponseEntity contenant le client trouvé
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer = customerService.getCustomerById(id)
        return ResponseEntity.ok(customer)
    }

    /**
     * Crée un nouveau client.
     *
     * @param customerRequest Données de création du client
     * @return ResponseEntity contenant le client créé avec statut 201
     */
    @PostMapping
    @Operation(summary = "Create a new customer")
    fun createCustomer(@Valid @RequestBody customerRequest: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = customerService.createCustomer(customerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(customer)
    }

    /**
     * Met à jour un client existant.
     *
     * @param id Identifiant du client à mettre à jour
     * @param customerRequest Nouvelles données du client
     * @return ResponseEntity contenant le client mis à jour
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    fun updateCustomer(
        @PathVariable id: Long,
        @Valid @RequestBody customerRequest: CustomerRequest
    ): ResponseEntity<CustomerResponse> {
        val customer = customerService.updateCustomer(id, customerRequest)
        return ResponseEntity.ok(customer)
    }

    /**
     * Supprime un client par son identifiant.
     *
     * @param id Identifiant du client à supprimer
     * @return ResponseEntity vide avec statut 204
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.deleteCustomer(id)
        return ResponseEntity.noContent().build()
    }

    /**
     * Recherche des clients par nom (prénom ou nom de famille).
     *
     * @param name Terme de recherche pour le nom
     * @return ResponseEntity contenant la liste des clients correspondants
     */
    @GetMapping("/search")
    @Operation(summary = "Search customers by name")
    fun searchCustomers(@RequestParam name: String): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.searchCustomersByName(name)
        return ResponseEntity.ok(customers)
    }
}