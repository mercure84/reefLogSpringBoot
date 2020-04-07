#APPLICATION REEFLOG -- BackEnd

#Version de Java : 11

##Fichier application.properties:

A ajouter dans src/main/resources au format suivant  : (les *** doivent être remplacées par vos valeurs)

********
#base de donnees
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://***
spring.datasource.username=***
spring.datasource.password=***

#envoi des mails
spring.mail.default-encoding=UTF-8
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=***@gmail.com
spring.mail.password=***
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


jwt.secret=***
spring.jpa.hibernate.ddl-auto=update
application.name=@project.artifactId@
build.version=@project.version@

#logging file
logging.file.name=logsSpringBoot/logs.log
logging.level.root=INFO


#upload files properties
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=5MB
# max request size
spring.servlet.multipart.max-request-size=5MB
# files storage location (stores all files uploaded via REST API)
storage.location=./uploads

***

##Créer un build : 

mvn clean package

##Déployer l'application :

java -jar xxxxxxxxx.jar