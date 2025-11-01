# 📚 [Nom de votre Projet]
## 📝 Description
-GESTION DE LOCATION DE VEHICULES BACK END
## 🛠 Technologies Utilisées
- **Langage** : Kotlin
- **Framework** : Spring Boot
- **Base de données** :PostgreSQL
- **Build Tool** : Maven / Gradle
## 📊 Diagramme UML
![Diagramme UML](Diagramme-UML.PNG)
## 🗃 Structure de la Base de Données
![Structure de la Base de Données](Diagramme-De-Base-de-Données-PostgreSQL.PNG)
## 🚀 Installation et Exécution
### Prérequis
- JDK 17+
- MySQL/PostgreSQL installé
- Maven/Gradle
- ### Étapes d'installation
1. Clonez le repository
```bash
 git clone https://github.com/StailiSaad/GESTION-DE-LOCATION-DE-VEHICULES-BACK-END
```
2. Créez la base de données
```sql
 CREATE DATABASE vehicle_rent;
```
3. Configurez `application.properties`
```properties
spring.application.name=vehicle-rental-backend
spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_votre_base
spring.datasource.username=votre_username
spring.datasource.password=votre_password
springdoc.api-docs.path: /api-docs
swagger-ui.path: /swagger-ui.html
swagger-ui.operations-sorter: method
```
4. Lancez l'application
```bash
 ./mvnw spring-boot:run
```
## 📡 Endpoints Disponibles

### A) 🚗 Gestion du Parc Automobile

| Méthode | Endpoint | Description | Code Réponse |
|---------|----------|-------------|--------------|
| `GET` | `/api/vehicles` | Catalogue complet des véhicules | 200 OK |
| `GET` | `/api/vehicles/available` | Véhicules disponibles à la location | 200 OK |
| `GET` | `/api/vehicles/{id}` | Détails d'un véhicule spécifique | 200 OK |
| `GET` | `/api/vehicles/search` | Recherche multicritères | 200 OK |
| `POST` | `/api/vehicles/cars` | Ajout d'une automobile au parc | 201 Created |
| `POST` | `/api/vehicles/motorcycles` | Enregistrement d'une motocyclette | 201 Created |
| `POST` | `/api/vehicles/trucks` | Intégration d'un poids lourd | 201 Created |
| `PATCH` | `/api/vehicles/{id}/availability` | Mise à jour de la disponibilité | 200 OK |
| `DELETE` | `/api/vehicles/{id}` | Retrait du parc automobile | 204 No Content |

### B) 👥 Administration des Clients

| Méthode | Endpoint | Description | Code Réponse |
|---------|----------|-------------|--------------|
| `GET` | `/api/customers` | Portefeuille clients complet | 200 OK |
| `GET` | `/api/customers/{id}` | Profil client détaillé | 200 OK |
| `GET` | `/api/customers/search` | Recherche nominative | 200 OK |
| `POST` | `/api/customers` | Enregistrement nouveau client | 201 Created |
| `PUT` | `/api/customers/{id}` | Mise à jour profil client | 200 OK |
| `DELETE` | `/api/customers/{id}` | Archivage client | 204 No Content |

### C) 📄 Gestion des Contrats de Location

| Méthode | Endpoint | Description | Code Réponse |
|---------|----------|-------------|--------------|
| `GET` | `/api/rentals` | Historique des locations | 200 OK |
| `GET` | `/api/rentals/{id}` | Détails d'un contrat | 200 OK |
| `GET` | `/api/rentals/customer/{customerId}` | Historique client | 200 OK |
| `GET` | `/api/rentals/overdue` | Locations en retard | 200 OK |
| `POST` | `/api/rentals` | Création nouveau contrat | 201 Created |
| `POST` | `/api/rentals/{id}/complete` | Clôture de location | 200 OK |
| `POST` | `/api/rentals/{id}/cancel` | Annulation de contrat | 200 OK |
## 󰞵 Auteur
**[Staili Saad et Saadi Sara]** - Projet Back-End Kotlin/Spring Boot