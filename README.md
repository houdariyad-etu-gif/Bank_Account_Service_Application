# Bank Account Service

## Implémentation d'un micro-service bancaire avec Spring Boot

Cette activité pratique porte sur la conception et l'implémentation d'un micro-service dédié à la gestion de comptes bancaires. Développée avec Spring Boot, l'application propose une API REST personnalisée ainsi que des endpoints générés automatiquement grâce à Spring Data REST.

Elle met en œuvre les principales bonnes pratiques d'une application Spring : persistance des données avec Spring Data JPA, séparation des responsabilités au moyen d'une architecture en couches, utilisation de DTO (*Data Transfer Objects*) pour les échanges avec les clients, conversion des entités avec un mapper et filtrage des réponses à l'aide de projections.

## Objectifs pédagogiques

- Créer une API REST avec Spring Boot.
- Persister des comptes bancaires avec Spring Data JPA.
- Exposer des opérations CRUD avec un contrôleur REST manuel.
- Générer automatiquement des endpoints à partir d'un repository avec Spring Data REST.
- Filtrer les données avec une méthode de repository personnalisée.
- Utiliser une projection pour limiter les champs retournés.
- Séparer les responsabilités grâce aux couches Controller, Service, Repository et Mapper.

## Fonctionnalités

### 1. API REST personnalisée

`AccountRestController` expose des endpoints sous le préfixe `/api` :

| Méthode | URL | Description |
|---|---|---|
| `GET` | `/api/bankAccounts` | Liste des comptes |
| `GET` | `/api/bankAccounts/{id}` | Récupère un compte par son identifiant |
| `POST` | `/api/bankAccounts` | Crée un compte à partir d'un DTO |
| `PUT` | `/api/bankAccounts/{id}` | Met à jour partiellement un compte |
| `DELETE` | `/api/bankAccounts/{id}` | Supprime un compte |

Exemple de création :

```json
{
  "balance": 800,
  "currency": "USD",
  "type": "CURRENT_ACCOUNT"
}
```

### 2. Spring Data REST

`BankAccountRepository` est annoté avec `@RepositoryRestResource`. Spring Data REST expose donc automatiquement une ressource REST pour le repository, en complément du contrôleur personnalisé.

La ressource générée est accessible à l'adresse suivante :

```text
http://localhost:8081/bankAccounts
```

La méthode personnalisée suivante permet de rechercher les comptes par type :

```text
GET http://localhost:8081/bankAccounts/search/byType?t=CURRENT_ACCOUNT
```

### 3. Projection

La projection `AccountProjection` (`p1`) permet de sélectionner uniquement certains champs d'un compte :

```text
GET http://localhost:8081/bankAccounts?projection=p1
```

La réponse contient notamment `id`, `type` et `balance`.

### 4. Architecture en couches

- **Entity** : `BankAccount`, entité JPA persistée en base.
- **Repository** : `BankAccountRepository`, accès aux données avec `JpaRepository`.
- **Service** : `AccountService` et `AccountServiceImpl`, logique métier et transactions.
- **DTO** : `BankAccountRequestDTO` pour les entrées et `BankAccountResponseDTO` pour les sorties.
- **Mapper** : `AccountMapper`, conversion entre entités et DTO.
- **Web** : `AccountRestController`, exposition des endpoints personnalisés.

Au démarrage, un `CommandLineRunner` crée automatiquement dix comptes de démonstration.

## Technologies utilisées

- Java 17 (version cible)
- Java 21 recommandé pour l'exécution
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Data REST
- Springdoc OpenAPI / Swagger UI
- H2 Database
- Lombok
- Maven

## Configuration

La configuration actuelle se trouve dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:h2:mem:account_db
spring.h2.console.enabled=true
server.port=8081
```

La base H2 est en mémoire : les données sont supprimées lorsque l'application est arrêtée.

## Lancer le projet

Prérequis :

- JDK 17 ou une version ultérieure compatible, idéalement Java 21 ;
- Maven 3.9 ou une version ultérieure.

Depuis la racine du projet :

```bash
mvn clean package
mvn spring-boot:run
```

Ou exécuter le JAR généré :

```bash
java -jar target/bank-account-service-0.0.1-SNAPSHOT.jar
```

L'application démarre sur :

```text
http://localhost:8081
```

## Interfaces utiles

- API personnalisée : `http://localhost:8081/api/bankAccounts`
- API Spring Data REST : `http://localhost:8081/bankAccounts`
- Console H2 : `http://localhost:8081/h2-console`
- Documentation Swagger UI : `http://localhost:8081/swagger-ui/index.html`
- Spécification OpenAPI : `http://localhost:8081/v3/api-docs`


## Organisation du projet

```text
src/main/java/org/sid/bank_account_service
├── entities       # Entités JPA et projection
├── dto            # Objets d'échange avec l'API
├── enums          # Types de comptes
├── repositories   # Repositories Spring Data
├── service        # Logique métier
├── mappers        # Conversion Entity <-> DTO
└── web            # Contrôleur REST
```
