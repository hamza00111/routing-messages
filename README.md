# Documentation du Projet Routing Service

## Introduction

Bonjour ! Je suis ravi de vous présenter **Routing Service**.

---

## Vidéo de Démonstration

https://screenrec.com/share/d4jRlHCsZU

## Suppositions et Décisions Techniques

### 1. **Unicité et Absence d'Ordre des Messages**

J’ai fait l’hypothèse que **les messages sont uniques et qu’ils n’ont pas besoin d’être traités dans un ordre particulier**. Cette décision a été prise pour simplifier la conception et garantir des performances optimales.

### 2. **Un Modèle Unique pour les Messages**

J’ai choisi de **ne pas utiliser plusieurs modèles ou objets pour représenter les messages**. Comme nous allons traiter potentiellement des millions de messages, ajouter des couches de mapping aurait alourdi le système et réduit les performances. Avec cette approche, je m’assure que chaque message est traité efficacement.

---

## Fonctionnalités Clés

### Gestion des Messages
- Lecture des messages depuis IBM MQ et stockage dans une base de données relationnelle.
- Liste des messages affichée dans une interface utilisateur claire et paginée.
- Affichage des détails d’un message lorsque vous cliquez dessus dans la liste.
- API REST pour la consultation des messages et leur manipulation.

### Gestion des Partenaires
- Création, mise à jour et suppression de partenaires via une API REST.
- Affichage et gestion des partenaires dans l’interface utilisateur.

### Gestion des Échecs de Lecture

J’ai intégré un mécanisme pour gérer les échecs de lecture des messages :
- Si la lecture échoue, j’ai configuré un **nombre maximal de tentatives**.
- Après ce nombre de tentatives, le message est automatiquement **envoyé vers une file de lettres mortes (Dead Letter Queue)** pour être analysé ou traité ultérieurement.
- Cette logique assure la robustesse du système et évite de bloquer le flux principal.

---

## Instructions pour Exécuter le Projet

### Enlever la sécurité en exécutant les commandes suivantes:

### Pré-requis
- Docker et Docker Compose installés.
- Ports disponibles :
    - `8080` pour le backend.
    - `4200` pour le frontend.
    - `1414` pour IBM MQ.

### Lancer l’Application

1. **Cloner le projet et lancer docker compose:**
   ```bash
   docker-compose up -d

IMPORTANT: Pour que l’application puisse écouter les messages sur IBM MQ, il est **important de désactiver certaines sécurités par défaut**. Voici les commandes nécessaires à exécuter dans l’environnement IBM MQ :

2. **Désactiver les contrôles de sécurité au niveau du gestionnaire de files :**
   ```bash
   docker exec -it ibm-mq bash
   runmqsc QM1
   ALTER QMGR CHLAUTH(DISABLED)
   ALTER QMGR CONNAUTH('')
   REFRESH SECURITY TYPE(CONNAUTH)

3. **Lien Swagger :**
   http://localhost:8080/swagger-ui/index.html#
