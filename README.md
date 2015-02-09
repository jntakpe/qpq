# Qui paie quoi

> Application permettant de faire les comptes et les dépenses entre amis

# Getting started

## Prérequis
Avant de pouvoir démarrer l'application les éléments suivants doivent être installés :
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [PostgreSQL](http://www.postgresql.org/download/)
* [NodeJS](http://nodejs.org/download/)
* [Grunt](http://gruntjs.com/getting-started)
* [Bower](http://bower.io/#install-bower)
* [Git](http://git-scm.com/)
* [Maven](http://maven.apache.org/)

## Installation
* Installer le JDK 8
* Installer Git et faire configurer username et email : `git config --global user.name "USER_GITHUB"` et `git config --global user.email "MON_EMAIL"`
* Installer NodeJS
* Installer Grunt `npm install -g grunt-cli`
* Installer Bower `npm install -g bower`
* Créer des bases de données PostgreSQL qpq et qpq-test avec en username : postgres et mdp : sopra*
* Récupérer le projet sur Github `git clone https://github.com/jntakpe/qpq.git`
* Installer les plugins grunt `npm install` à la racine du projet
* Télécharger les ressources statiques `bower install` à la racine du projet

## Démarrage
* Builder les ressources statiques `grunt`
* Démarrer l'application `mvn spring-boot:run`, l'application demarre à l'adresse [http://localhost:8080](http://localhost:8080) sur un serveur Tomcat embedded. Il est également possible de démarrer l'application sur un Tomcat classique depuis votre IDE (IntelliJ j'espère)
* Pour que les ressources statiques soient rechargées à la volée `grunt watch`

# Usage

L'application est déployée sur le Heroku à cette adresse : [http://qpq.herokuapp.com/](http://qpq.herokuapp.com/)

# Technos

* AngularJS
* Bootstrap
* Spring Boot
* Spring Security OAuth2
* Spring Data JPA
* PostgreSQL
* Heroku