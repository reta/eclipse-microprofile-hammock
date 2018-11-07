package com.example.people.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.net.URI;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.people.config.PersistenceConfig;
import com.example.people.persistence.PeopleJpaRepository;
import com.example.people.service.PeopleServiceImpl;

import io.restassured.http.ContentType;
import ws.ament.hammock.test.support.EnableRandomWebServerPort;

@RunWith(Arquillian.class)
@EnableRandomWebServerPort
public class PeopleApiTest {
    @ArquillianResource private URI uri;
    
    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap
            .create(JavaArchive.class)
            .addClasses(PeopleResource.class, PeopleApplication.class)
            .addClasses(PeopleServiceImpl.class, PeopleJpaRepository.class, PersistenceConfig.class)
            .addPackages(true, "org.apache.deltaspike");
    }
            
    @Test
    public void shouldAddNewPerson() throws Exception {
        final Person person = new Person("a@b.com", "John", "Smith");
        
        given()
            .contentType(ContentType.JSON)
            .body(person)
            .post(uri + "/api/people")
            .then()
            .assertThat()
            .statusCode(201)
            .body("email", equalTo("a@b.com"))
            .body("firstName", equalTo("John"))
            .body("lastName", equalTo("Smith"));
    }
}
