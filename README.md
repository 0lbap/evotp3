# evotp3

## Installation

> Avant de commencer, assurez vous d'avoir Docker et Docker Compose.

Pour installer et lancer le projet, suivez les commandes suivantes :

```bash
git clone https://github.com/0lbap/evotp3
cd evotp3
cp .env.example .env # Vous pouvez ensuite modifier le fichier d'environnement
docker-compose up --build # Le build est uniquement nécessaire la première fois
```

Pour arrêter le projet, appuyez sur CTRL C puis lancez la commande suivante :

```bash
docker-compose down
```

## Services

Après déploiement, vous pouvez accéder aux services suivants :

- La plateforme web Kibana, qui permet la visualisation et l'exécution de requêtes sur les logs collectés, est exposée à l'adresse http://localhost:5601

- L'interface graphique de notre application est exposée à l'adresse http://localhost:3000

- Notre API REST Spring Boot est exposée à l'adresse http://localhost:8000/api/v1
