#APPLICATION REEFLOG -- BackEnd

#Version de Java : 11

##Fichier application.properties:

A ajouter dans src/main/resources au format suivant  :


####base de données
spring.datasource.platform=***
spring.datasource.url=***
spring.datasource.username=***
spring.datasource.password=***


####envoi des mails
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=***
spring.mail.password=***
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


jwt.secret=***

spring.jpa.hibernate.ddl-auto=update

application.name=@project.artifactId@
build.version=@project.version@ 




##Créer un build : 

mvn clean package

##Déployer l'application :

java -jar xxxxxxxxx.jar