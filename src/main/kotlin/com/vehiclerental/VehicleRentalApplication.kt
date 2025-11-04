package com.vehiclerental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Classe principale de l'application Vehicle Rental.
 * Point d'entrée de l'application Spring Boot.
 */
@SpringBootApplication
class VehicleRentalApplication

/**
 * Fonction principale lançant l'application Spring Boot.
 *
 * @param args Arguments de ligne de commande
 */
fun main(args: Array<String>) {
    runApplication<VehicleRentalApplication>(*args)
}