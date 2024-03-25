# Image Generation Store Prototype 

Instructions: 

* install Java 21 (or later) and make it your default: `sdk install java 21-graalce` and then run `sdk default java 21-graalce`
* open a new shell 
* build it: `./mvnw package`
* run it: `./mvnw spring-boot:run`
* build a native image: `./mvnw -Pnative -DskipTests native:compile`
