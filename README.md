# APPLICATION REEFLOG -- BackEnd


Cette API Java Spring Boot correspond à la partie Back End du projet "ReefLog".
Elle se couple avec le projet React Native : https://github.com/mercure84/ReefLogAppMobile

La version de java nécessaire est la 11

L'API se package et se déploie à l'aide de Maven.
Nous conseillons l'utilisation du profil "dev" afin que la persistence des données soit gérée directement à travers une base H2.
Il n'est pas prévu de pouvoir déployer les versions d'homologation et de production via ce repo.
 
Packager l'application : `mvn package -P dev` à la racine du projet

Puis dans le dossier target qui aura été généré : `java -jar nom_de_la_release_X.XX.jar `

Un serveur tomcat est alors déployé et les services peuvent être testés via POSTMAN sur l'url locale http://localhost:8080.

Un jeu de test est disponible dans le fichier _TestReefLog.postman_collection.json_
Si vous lancez toutes les requêtes (environ 50 tests au 08/04/2019), vous aurez testé la quasi totalité des services de l'application. 

Bonne utilisation !

Julien M.