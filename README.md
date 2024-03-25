# Image Generation Store Prototype 

## Instructions for building and running: 

* install Java 21 (or later) and make it your default: `sdk install java 21-graalce` and then run `sdk default java 21-graalce`
* open a new shell 
* build it: `./mvnw package`
* run it: `./mvnw spring-boot:run`
* build a native image: `./mvnw -Pnative -DskipTests native:compile`
* make sure you specify important credentials as environment variables before running the program:
  * `SPRING_DATASOURCE_URL`: `jdbc:postgresql://host/db`
  * `SPRING_DATASOURCE_PASSWORD`: `password`
  * `SPRING_DATASOURCE_USERNAME`: `username`
  * `SPRING_AI_OPENAI_API_KEY`: `your open AI key`


  
## Instructions for use 

* see all the prompts in the PostgreSQL instance as an HTML page: `curl http://localhost:8080/prompts.html`
* see all the prompts in the PostgreSQL instance as a REST endpoint: `curl http://localhost:8080/prompts`
* add a new record to the DB: `curl -XPOST -dprompt="what does the fox say?" http://localhost:8080/prompts`
