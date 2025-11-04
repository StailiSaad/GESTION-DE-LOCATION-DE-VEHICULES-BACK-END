package com.vehiclerental.config

import com.vehiclerental.entity.*
import com.vehiclerental.repository.CarRepository
import com.vehiclerental.repository.CustomerRepository
import com.vehiclerental.repository.MotorcycleRepository
import com.vehiclerental.repository.TruckRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Composant Spring responsable de l'initialisation des données de démonstration
 * au démarrage de l'application.
 *
 * @property carRepository Repository pour la gestion des voitures
 * @property motorcycleRepository Repository pour la gestion des motos
 * @property truckRepository Repository pour la gestion des camions
 * @property customerRepository Repository pour la gestion des clients
 */
@Component
class DataInitializer(
    private val carRepository: CarRepository,
    private val motorcycleRepository: MotorcycleRepository,
    private val truckRepository: TruckRepository,
    private val customerRepository: CustomerRepository
) : CommandLineRunner {

    /**
     * Méthode exécutée au démarrage de l'application Spring Boot.
     * Initialise les véhicules et clients si la base est vide.
     *
     * @param args Arguments de ligne de commande
     */
    override fun run(vararg args: String?) {
        initVehicles()
        initCustomers()
        println(" Données d'initialisation chargées avec succès! 01/11/2025")
    }

    /**
     * Initialise les données des véhicules (voitures, motos, camions)
     * dans la base de données si elle est vide.
     */
    private fun initVehicles() {

        if (carRepository.count() == 0L) {
            val cars = listOf(
                Car(
                    brand = "Golf",
                    model = "mk 8.5R",
                    year = 2025,
                    dailyRate = 300.0,
                    doors = 4,
                    fuelType = FuelType.GASOLINE,
                    automatic = true
                ),
                Car(
                    brand = "BMW",
                    model = "M5 Cs",
                    year = 2023,
                    dailyRate = 500.0,
                    doors = 4,
                    fuelType = FuelType.HYBRID,
                    automatic = true
                )
            )
            carRepository.saveAll(cars)
        }


        if (motorcycleRepository.count() == 0L) {
            val motorcycles = listOf(
                Motorcycle(
                    brand = "Yamaha",
                    model = "MT-10",
                    year = 2022,
                    dailyRate = 75.0,
                    engineSize = 998,
                    type = "NAKED"
                ),
                Motorcycle(
                    brand = "CBR ",
                    model = "FIREBLADE 1000RRR",
                    year = 2022,
                    dailyRate = 85.0,
                    engineSize = 999,
                    type = "NAKED"
                )
            )
            motorcycleRepository.saveAll(motorcycles)
        }


        if (truckRepository.count() == 0L) {
            val trucks = listOf(
                Truck(
                    brand = "Ford",
                    model = "Raptor",
                    year = 2025,
                    dailyRate = 275.0,
                    capacity = 3500,
                    fourWheelDrive = true
                )
            )
            truckRepository.saveAll(trucks)
        }
    }

    /**
     * Initialise les données des clients dans la base de données si elle est vide.
     */
    private fun initCustomers() {

        if (customerRepository.count() == 0L) {
            val customers = listOf(
                Customer(
                    firstName = "Staili",
                    lastName = "Saad",
                    email = "saadstaili1903@gmail.com",
                    phoneNumber = "+212766902029",
                    driverLicenseNumber = "GG123456789"
                ),
                Customer(
                    firstName = "Saadi",
                    lastName = "Sara",
                    email = "saadisara07@gmail.com",
                    phoneNumber = "+212703877511",
                    driverLicenseNumber = "BG987654321"
                ) ,
                Customer(
                    firstName = "customer2",
                    lastName = "OPT1",
                    email = "OPT1@gmail.com",
                    phoneNumber = "+212755551",
                    driverLicenseNumber = "FF2222321"
                )
            )
            customerRepository.saveAll(customers)
        }
    }
}