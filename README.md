Eclipse Microprofile with Hammock
====
 - Build and run:

        mvn clean package
	java -jar target/eclipse-microprofile-hammock-0.0.1-SNAPSHOT-capsule.jar 
 
 - OpenAPI v3.0 documentation: 
 
       http://localhost:10900/api/openapi.json
 
 - Add new person:
 
       curl -X POST http://localhost:10900/api/people -H "Content-Type: application\json" \
         -d '{"email": "a@b.com", "firstName": "John", "lastName": "Smith"}'

 - Find person by email:
     
       curl http://localhost:10900/api/people/a@b.com

 - Get all people:
     
       curl http://localhost:10900/api/people

 - Remove person:
     
       curl -X DELETE http://localhost:10900/api/people/a@b.com
