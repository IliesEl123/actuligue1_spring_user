Ce projet est une application web qui recueuil informations et actualités concernant la ligue 1 de football.

## Fonctionnalités

L'application permet à un utilisateur de :

- Créer un compte
- Se connecter
- Consulter les dernières actualités ligue 1
- Consulter les classements ligue 1 mises à jour
- Consulter les statistiques de la saison par équipe
- Consulter un comparateur de cotes des principaux bookmakers français pour les matchs de la semaine
- Mettre des matchs en favori

# Installation

## Prérequis

Pour pouvoir installer et exécuter cette application, vous devez avoir :

- Une version de Java 8 ou supérieure installée sur votre ordinateur
- Une base de données MySQL installée sur votre ordinateur

## Étapes d'installation

1. Clonez ce projet sur votre ordinateur à l'aide de la commande :

`git clone https://github.com/votre_utilisateur/projet4back.git`

2. Ouvrez un terminal à la racine du projet et exécutez la commande suivante pour installer les dépendances :

`mvn clean install`

3. Configurez les propriétés de la base de données en éditant le fichier src/main/resources/application.properties en fonction de votre environnement. Assurez-vous d'avoir créé au préalable une base de données vide dans MySQL.

4. Démarrez l'application en exécutant la commande suivante :

`mvn spring-boot:run`

# Utilisation
## Accéder à l'API

L'API de cette application est accessible à l'adresse http://localhost:8080/. Vous pouvez utiliser un client HTTP (par exemple Postman) pour interagir avec l'API.

## Authentification

L'authentification se fait à l'aide d'un token JWT (JSON Web Token). Le token est envoyé dans le header Authorization de chaque requête à l'API. Le token est valide pendant une durée limitée.
