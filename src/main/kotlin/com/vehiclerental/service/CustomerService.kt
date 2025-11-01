package com.vehiclerental.service

import com.vehiclerental.dto.request.CustomerRequest
import com.vehiclerental.dto.response.CustomerResponse
import com.vehiclerental.entity.Customer
import com.vehiclerental.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerService(private val customerRepository: CustomerRepository) {

    fun getAllCustomers(): List<CustomerResponse> {
        return customerRepository.findAll().map { it.toResponse() }
    }

    fun getCustomerById(id: Long): CustomerResponse {
        val customer = customerRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Customer not found with id: $id") }
        return customer.toResponse()
    }

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

    fun deleteCustomer(id: Long) {
        if (!customerRepository.existsById(id)) {
            throw IllegalArgumentException("Customer not found with id: $id")
        }
        customerRepository.deleteById(id)
    }

    fun searchCustomersByName(name: String): List<CustomerResponse> {
        return customerRepository.findByNameContainingIgnoreCase(name).map { it.toResponse() }
    }

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