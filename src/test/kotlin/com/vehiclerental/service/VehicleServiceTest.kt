package com.vehiclerental.service

import com.vehiclerental.dto.request.CarRequest
import com.vehiclerental.entity.Car
import com.vehiclerental.entity.FuelType
import com.vehiclerental.repository.CarRepository
import com.vehiclerental.repository.VehicleRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

/**
 * Classe de tests unitaires pour VehicleService.
 * Teste les fonctionnalités de gestion des véhicules.
 */
@ExtendWith(MockitoExtension::class)
class VehicleServiceTest {

    @Mock
    private lateinit var vehicleRepository: VehicleRepository

    @Mock
    private lateinit var carRepository: CarRepository

    @InjectMocks
    private lateinit var vehicleService: VehicleService

    /**
     * Teste la création réussie d'une voiture avec une requête valide.
     */
    @Test
    fun `createCar should return car response when valid request`() {
        // Given
        val carRequest = CarRequest(
            brand = "Range Rover",
            model = "Autobiographie",
            year = 2022,
            dailyRate = 250.0,
            doors = 4,
            fuelType = "GASOLINE",
            automatic = true
        )

        val savedCar = Car(

            brand = "Range Rover",
            model = "Autobiographie",
            year = 2022,
            dailyRate = 250.0,
            doors = 4,
            fuelType = FuelType.GASOLINE,
            automatic = true
        )

        Mockito.`when`(carRepository.save(ArgumentMatchers.any(Car::class.java))).thenReturn(savedCar)

        // When
        val result = vehicleService.createCar(carRequest)

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(1L, result.id)
        Assertions.assertEquals("Range Rover", result.brand)
        Assertions.assertEquals("Autobiographie", result.model)
        Mockito.verify(carRepository, Mockito.times(1)).save(ArgumentMatchers.any(Car::class.java))
    }

    /**
     * Teste qu'une exception est levée lors de la création d'une voiture avec un type de carburant invalide.
     */
    @Test
    fun `createCar should throw exception when invalid fuel type`() {
        // Arrange
        val carRequest = CarRequest(
            brand = "Range Rover",
            model = "Autobiographie",
            year = 2022,
            dailyRate = 250.0,
            doors = 4,
            fuelType = "INVALID",
            automatic = true
        )

        // When & Then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            vehicleService.createCar(carRequest)
        }
    }

    /**
     * Teste la récupération réussie d'un véhicule par son identifiant lorsqu'il existe.
     */
    @Test
    fun `getVehicleById should return vehicle when exists`() {
        // Given
        val car = Car(

            brand = "Range Rover",
            model = "Autobiographie",
            year = 2022,
            dailyRate = 250.0,
            doors = 4,
            fuelType = FuelType.GASOLINE,
            automatic = true
        )

        Mockito.`when`(vehicleRepository.findById(1L)).thenReturn(Optional.of(car))

        // When
        val result = vehicleService.getVehicleById(1L)

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals("Range Rover", result.brand)
    }

    /**
     * Teste qu'une exception est levée lors de la récupération d'un véhicule qui n'existe pas.
     */
    @Test
    fun `getVehicleById should throw exception when not exists`() {
        // Given
        Mockito.`when`(vehicleRepository.findById(1L)).thenReturn(Optional.empty())

        // When & Then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            vehicleService.getVehicleById(1L)
        }
    }
}