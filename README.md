# FilRougeBack
API REST SPRING BOOT + JPA + SCRIPT SQL + PREPARESTATEMENT

### Installation du Projet Fil Rouge
 ###### Prérequis (cliquer sur le badge pour télécharger la version logicielle)
1. [![Jdk (custom registry)](https://img.shields.io/badge/JDK-1.8.0-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [![Maven (custom registry)](https://img.shields.io/badge/Maven-3.5.2-green.svg)](https://maven.apache.org/docs/3.5.2/release-notes.html)
3. [![Mysql (custom registry)](https://img.shields.io/badge/MySQL-5.6-red.svg)](https://dev.mysql.com/downloads/mysql/5.6.html)
4. IDE 
[![Eclipse (custom registry)](https://img.shields.io/badge/Eclipse-Oxigen-F6FF33.svg)](http://www.eclipse.org/downloads/eclipse-packages/)
 ou 
[![IntelliJ (custom registry)](https://img.shields.io/badge/IntelliJ-Community-%23E10000.svg)](https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC)

5. [![lombok (custom registry)](https://img.shields.io/badge/lombok-1.16.20-yellowgreen.svg)](https://projectlombok.org/download)




 ###### Démarrage
1. Ouvrir le projet avec l'IDE
2. Installer le plugin `lombok`
3. Update des dépendances avec MAVEN
4. Modifier les propriétés MySQL du fichier `application.properties` le port `localhost:3306 `le Username `username = `et le Password `password = `en fonction de l'environnement du poste d'execution
```
##Proprietes Mysql
spring.datasource.url = jdbc:mysql://localhost:3306/police_app?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username = admin
spring.datasource.password = admin
```
4. Démarrer l'application
###### Ou
Démarrer directement le fichier `filRouge-0.0.1-SNAPSHOT.jar` dans le dossier target avec la commande  
`java -jar .\filRouge-0.0.1-SNAPSHOT.jar`  (sous réserve que la config Mysql corresponde)


### Initialisation (à partir des Scripts .sql présent dans le dossier ressources)
 ###### Schema.sql
 * La Base de Données est créée si elle n'existe pas et la base est selectionnée
 
 ```SQL
 CREATE DATABASE IF NOT EXISTS `police_app` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `police_app`;
 ```
 
* Les Tables sont crées si elle n'existe pas  
Exemple :
 
 ```SQL
 CREATE TABLE IF NOT EXISTS `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commentary` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 ```
 ###### Data.sql
 * Un jeux de données de test est chargé en base par défaut  
 Exemple :
 ```SQL
 INSERT IGNORE INTO `police_case` (`id`,`name`,`description`) VALUES (1,'avion détouné','mi, ac mattis velit justo nec')
 ```
 
 ### Utilisation des API
 ##### Routes
 ###### CASE
 * http://localhost:8080/api/case   Pour GET ALL ou POST 
 * http://localhost:8080/api/case/{id} Pour GET ONE, PUT ou DELETE
 ###### VEHICULE
 * http://localhost:8080/api/vehicule   Pour GET ALL ou POST
 * http://localhost:8080/api/vehicule/{id}   Pour GET ONE, PUT ou DELETE
 ###### PEOPLE
 * http://localhost:8080/api/people   Pour GET ALL ou POST
 * http://localhost:8080/api/people/{id}   Pour GET ONE, PUT ou DELETE
 ###### WEAPON
 * http://localhost:8080/api/weapon   Pour GET ALL ou POST
 * http://localhost:8080/api/weapon/{id}   Pour GET ONE, PUT ou DELETE
 ###### PIECE OF EVIDENCE
 * http://localhost:8080/api/piece   Pour GET ALL ou POST
 * http://localhost:8080/api/piece/{id}   Pour GET ONE, PUT ou DELETE
###### DESASSOCIATION 
Exemple de requète de 'Désassociation':
```SQL
 DELETE FROM police_case_piece_of_evidence WHERE police_case_id = ? AND piece_of_evidence_id = ?
 ```
*  http://localhost:8080/api/linkPeople/{idCase}/{idPeople}   Pour désassocier une personne d'une affaire
*  http://localhost:8080/api/linkVehicule/{idCase}/{idVehicule}   Pour désassocier un véhicule d'une affaire
*  http://localhost:8080/api/linkWeapon/{idCase}/{idWeapon}   Pour désassocier une arme d'une affaire
*  http://localhost:8080/api/linkPiece/{idCase}/{idPiece}   Pour désassocier une Pièce à conviction d'une affaire
###### DANS QUELLES AFFAIRES
Exemple de requète 'Dans quelles affaires':
```SQL
 FROM people AS p
 LEFT JOIN police_case_people AS cp ON cp.people_id  = p.id
 LEFT JOIN police_case AS c ON cp.police_case_id  = c.id 
 WHERE p.id = ?
 ```
 * http://localhost:8080/api/case_vehicule/{idVehicule}   Dans quelles affaires et le véhicule
 * http://localhost:8080/api/case_people/{idPeople}   Dans quelles affaires et la personne  
 
 ### Test  
 
 Tester le code retour de la requete '200 OK'
 ```JAVA
 @Test
	public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then200IsReceived()
			throws ClientProtocolException, IOException {
		// Given
		HttpUriRequest request = new HttpGet( "http://localhost:8080/api/case" );
		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
		// Then
		assertThat(
				httpResponse.getStatusLine().getStatusCode(),
				equalTo(HttpStatus.SC_OK));
	}
 ```
 
 
 ## Diagramme de classe  

Diagramme de Classe: ![Alt Text](https://github.com/stephp30/FilRougeBack/blob/master/img/diagrammeDeClasse.jpg)
 
## Schema Base de Données MySql
 
Schema: ![Alt Text](https://github.com/stephp30/FilRougeBack/blob/master/img/SchemaBDD.PNG)  

## Diagramme d'activité  

[Diagramme d'activité](https://github.com/stephp30/FilRougeBack/blob/master/img/diagrammeActivite.pdf)  

## Use Case  
 
 Use Case: ![Alt Text](https://github.com/stephp30/FilRougeBack/blob/master/img/useCase.png)  
 




