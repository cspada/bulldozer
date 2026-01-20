# Bulldozer interview

## Prérequis

- Java 25
- Postgresql 18.1
  - Avec une database `bar` et un utilisateur `bulldozer`avec mot de passe `bulldozer`.
  - Avec une database `inventory` et un utilisateur `bulldozer`avec mot de passe `bulldozer`.
  - Avec une database `go_home` et un utilisateur `bulldozer`avec mot de passe `bulldozer`.
  - (le script de création `init_databases.sql` est dans le répertoire `db`)
  - Note: les différents schémas des services sont crées avec Flyway au démarrage.
- RabbitMQ 3.12.x
  - Avec un utilisateur `bulldozer` et mot de passe `bulldozer`
  - Avec un virtual host `bulldozer`
  - Note: les exchange et stream sont sont crées au démarrage des services si absents. 

## Execution des services

`./mvnw spring-boot:run -pl oauth-server` -> serveur Oauth2 sur le port 8090
`./mvnw spring-boot:run -pl bar-service` -> le service se lance avec le port 8080
`./mvnw spring-boot:run -pl inventory-service` -> le service se lance avec le port 8081
`./mvnw spring-boot:run -pl go-back-home-service` -> le service se lance avec le port 8082

## Les différents modules proposés

- oauth-server : Serveur oauth.
- messaging/messaging-api : définit l'ensemble des events et l'API commune de publication.
- messaging/messaging-amqp : implémentation de l'API pour AMQP.
- bar-service : application spring-boot-web-mvc pour servir l'API REST du bar
- inventory-service : application spring-boot-web-mvc pour servir l'API REST de l'inventaire
- go-back-home-service : application spring-boot.

Les requêtes HTTP pour les différents services sont disponibles dans les différents fichiers
`auth-admin.http`, `employee-admin.http`, `bar-requests.http`, `inventory-requests.http` (utilisation du client HTTP IntelliJ IDEA).

## Choix de simplification pour l'exercice

- Mise en place d'un authorization-server avec utilisateurs définis dans le code et implémentation 
  in-memory d'un UserDetailsService
- Utilisation du même utilisateur DB
- Utilisation des mêmes secret pour les clients oauth
- Utilisation d'un seul stream rabbitMQ. Ce n'est pas forcément une simplication mais une approche alternative 
  à une configuration d'une queue spécifique par service et d'une configuration d'un exchange avec routing keys.

## Ce qui n'a pas été couvert dans l'exercice

- Mise en place de Swagger pour documentation des API.

