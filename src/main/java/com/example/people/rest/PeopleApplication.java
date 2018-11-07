package com.example.people.rest;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Dependent
@ApplicationPath("api")
@OpenAPIDefinition(
    info = @Info(
        title = "People Management Web APIs", 
        version = "1.0.0", 
        license = @License(
            name = "Apache License", 
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    )
)
public class PeopleApplication extends Application {
}