package com.vehiclerental.service

import com.vehiclerental.dto.request.CustomerRequest
import com.vehiclerental.entity.Customer
import com.vehiclerental.repository.CustomerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

/**
 * Classe de tests unitaires pour CustomerService.
 * Teste les fonctionnalités de gestion des clients.
 */
@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @InjectMocks
    private lateinit var customerService: CustomerService

    /**
     * Teste la création réussie d'un client avec une requête valide.
     */
    @Test
    fun `createCustomer should return customer response when valid request`() {
        // Given
        val customerRequest = CustomerRequest(
            firstName = "Ntic",
            lastName = "dev",
            email = "nticdev@example.com",
            phoneNumber = "+1234567890",
            driverLicenseNumber = "XH123456"
        )

        `when`(customerRepository.findByEmail("nticdev@example.com")).thenReturn(Optional.empty())
        `when`(customerRepository.findByDriverLicenseNumber("XH123456")).thenReturn(Optional.empty())

        val savedCustomer = Customer(
            id = 1L,
            firstName = "Ntic",
            lastName = "dev",
            email = "nticdev@example.com",
            phoneNumber = "+1234567890",
            driverLicenseNumber = "XH123456"
        )

        `when`(customerRepository.save(any(Customer::class.java))).thenReturn(savedCustomer)

        // When
        val result = customerService.createCustomer(customerRequest)

        // Then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("Ntic", result.firstName)
        assertEquals("dev", result.lastName)
        verify(customerRepository, times(1)).save(any(Customer::class.java))
    }

    /**
     * Teste qu'une exception est levée lors de la création d'un client avec un email existant.
     */
    @Test
    fun `createCustomer should throw exception when email exists`() {
        // Given
        val customerRequest = CustomerRequest(
            firstName = "Ntic",
            lastName = "dev",
            email = "nticdev@example.com",
            phoneNumber = "+1234567890",
            driverLicenseNumber = "XH123456"
        )

        val existingCustomer = Customer(
            id = 1L,
            firstName = "CUSTOMER EXIXSTANT AVEC CETTE EMAIL",
            lastName = "PARFAIT",
            email = "nticdev@example.com",
            phoneNumber = "+0987654321",
            driverLicenseNumber = "DL654321"
        )

        `when`(customerRepository.findByEmail("nticdev@example.com")).thenReturn(Optional.of(existingCustomer))

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            customerService.createCustomer(customerRequest)
        }
    }

    /**
     * Teste la récupération réussie d'un client par son identifiant lorsqu'il existe.
     */
    @Test
    fun `getCustomerById should return customer when exists`() {
        // Given
        val customer = Customer(
            id = 1L,
            firstName = "Ntic",
            lastName = "dev",
            email = "nticdev@example.com",
            phoneNumber = "+1234567890",
            driverLicenseNumber = "XH123456"
        )

        `when`(customerRepository.findById(1L)).thenReturn(Optional.of(customer))

        // When
        val result = customerService.getCustomerById(1L)

        // Then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("Ntic", result.firstName)
    }

    /**
     * Teste qu'une exception est levée lors de la récupération d'un client qui n'existe pas.
     */
    @Test
    fun `getCustomerById should throw exception when not exists`() {
        // Given
        `when`(customerRepository.findById(1L)).thenReturn(Optional.empty())

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            customerService.getCustomerById(1L)
        }
    }
}