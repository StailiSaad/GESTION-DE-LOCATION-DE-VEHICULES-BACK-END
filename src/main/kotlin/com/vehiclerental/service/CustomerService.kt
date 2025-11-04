package com.vehiclerental.service

import com.vehiclerental.dto.request.CustomerRequest
import com.vehiclerental.dto.response.CustomerResponse
import com.vehiclerental.entity.Customer
import com.vehiclerental.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service pour la gestion des clients.
 * Fournit les opérations CRUD et de recherche pour les clients.
 *
 * @property customerRepository Repository pour l'accès aux données des clients
 */
@Service
@Transactional
class CustomerService(private val customerRepository: CustomerRepository) {

    /**
     * Récupère tous les clients.
     *
     * @return Liste de tous les clients sous forme de CustomerResponse
     */
    fun getAllCustomers(): List<CustomerResponse> {
        return customerRepository.findAll().map { it.toResponse() }
    }

    /**
     * Récupère un client par son identifiant.
     *
     * @param id Identifiant du client
     * @return CustomerResponse correspondant au client
     * @throws IllegalArgumentException si le client n'est pas trouvé
     */
    fun getCustomerById(id: Long): CustomerResponse {
        val customer = customerRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Customer not found with id: $id") }
        return customer.toResponse()
    }

    /**
     * Crée un nouveau client.
     *
     * @param customerRequest Données de création du client
     * @return CustomerResponse du client créé
     * @throws IllegalArgumentException si l'email ou le numéro de permis existe déjà
     */
    fun createCustomer(customerRequest: CustomerRequest): CustomerResponse {
        // Vérifier si l'email existe déjà
        if (customerRepository.findByEmail(customerRequest.email).isPresent) {
            throw IllegalArgumentException("Email already exists: ${customerRequest.email}")
        }

        // Vérifier si le numéro de permis existe déjà
        if (customerRepository.findByDriverLicenseNumber(customerRequest.driverLicenseNumber).isPresent) {
            throw IllegalArgumentException("Driver license number already exists: ${customerRequest.driverLicenseNumber}")
        }

        val customer = Customer(
            firstName = customerRequest.firstName,
            lastName = customerRequest.lastName,
            email = customerRequest.email,
            phoneNumber = customerRequest.phoneNumber,
            driverLicenseNumber = customerRequest.driverLicenseNumber
        )

        val savedCustomer = customerRepository.save(customer)
        return savedCustomer.toResponse()
    }

    /**
     * Met à jour un client existant.
     *
     * @param id Identifiant du client à mettre à jour
     * @param customerRequest Nouvelles données du client
     * @return CustomerResponse du client mis à jour
     * @throws IllegalArgumentException si le client n'est pas trouvé ou si l'email/permis existe déjà
     */
    fun updateCustomer(id: Long, customerRequest: CustomerRequest): CustomerResponse {
        val existingCustomer = customerRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Customer not found with id: $id") }

        // Vérifier les doublons d'email
        customerRepository.findByEmail(customerRequest.email).ifPresent { customer ->
            if (customer.id != id) {
                throw IllegalArgumentException("Email already exists: ${customerRequest.email}")
            }
        }

        // Vérifier les doublons de permis
        customerRepository.findByDriverLicenseNumber(customerRequest.driverLicenseNumber).ifPresent { customer ->
            if (customer.id != id) {
                throw IllegalArgumentException("Driver license number already exists: ${customerRequest.driverLicenseNumber}")
            }
        }

        // Créer un nouveau customer avec les données mises à jour
        val updatedCustomer = Customer(
            id = existingCustomer.id,
            firstName = customerRequest.firstName,
            lastName = customerRequest.lastName,
            email = customerRequest.email,
            phoneNumber = customerRequest.phoneNumber,
            driverLicenseNumber = customerRequest.driverLicenseNumber,
            createdAt = existingCustomer.createdAt
        )

        val savedCustomer = customerRepository.save(updatedCustomer)
        return savedCustomer.toResponse()
    }

    /**
     * Supprime un client par son identifiant.
     *
     * @param id Identifiant du client à supprimer
     * @throws IllegalArgumentException si le client n'est pas trouvé
     */
    fun deleteCustomer(id: Long) {
        if (!customerRepository.existsById(id)) {
            throw IllegalArgumentException("Customer not found with id: $id")
        }
        customerRepository.deleteById(id)
    }

    /**
     * Recherche des clients par nom (prénom ou nom de famille).
     *
     * @param name Terme de recherche
     * @return Liste des clients correspondants sous forme de CustomerResponse
     */
    fun searchCustomersByName(name: String): List<CustomerResponse> {
        return customerRepository.findByNameContainingIgnoreCase(name).map { it.toResponse() }
    }

    /**
     * Extension function pour convertir une entité Customer en CustomerResponse.
     *
     * @return CustomerResponse correspondant à l'entité Customer
     */
    private fun Customer.toResponse(): CustomerResponse = CustomerResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        fullName = this.getFullName(),
        email = this.email,
        phoneNumber = this.phoneNumber,
        driverLicenseNumber = this.driverLicenseNumber,
        createdAt = this.createdAt.toString()
    )
}