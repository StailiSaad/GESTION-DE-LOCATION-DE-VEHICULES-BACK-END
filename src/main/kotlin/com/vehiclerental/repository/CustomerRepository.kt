package com.vehiclerental.repository

import com.vehiclerental.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository pour l'accès aux données des clients.
 * Fournit des méthodes de requête pour les entités Customer.
 */
@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    /**
     * Trouve un client par son adresse email.
     *
     * @param email Adresse email du client
     * @return Optional contenant le client s'il existe
     */
    fun findByEmail(email: String): Optional<Customer>

    /**
     * Trouve un client par son numéro de permis de conduire.
     *
     * @param driverLicenseNumber Numéro de permis de conduire
     * @return Optional contenant le client s'il existe
     */
    fun findByDriverLicenseNumber(driverLicenseNumber: String): Optional<Customer>

    /**
     * Recherche des clients par nom (prénom ou nom de famille).
     * La recherche est insensible à la casse.
     *
     * @param name Terme de recherche pour le nom
     * @return Liste des clients dont le prénom ou nom contient le terme recherché
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Customer>
}