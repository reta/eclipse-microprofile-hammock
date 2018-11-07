package com.example.people.rest;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.people.service.PeopleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@ApplicationScoped
@Path( "/people" ) 
@Tag(name = "people")
public class PeopleResource {
    @Inject private PeopleService service;
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Operation(
        description = "List all people", 
        responses = {
            @ApiResponse(
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Person.class))),
                responseCode = "200"
            )
        }
    )
    public Collection<Person> getPeople() {
        return service.getAll();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    @GET
    @Operation(
        description = "Find person by e-mail", 
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Person.class)), 
                responseCode = "200"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Person with such e-mail doesn't exists"
            )
        }
    )
    public Person findPerson(@Parameter(description = "E-Mail address to lookup for", required = true) @PathParam("email") final String email) {
        return service
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Person with such e-mail doesn't exists"));
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Operation(
        description = "Create new person",
        requestBody = @RequestBody(
            content = @Content(schema = @Schema(implementation = Person.class))
        ), 
        responses = {
            @ApiResponse(
                 content = @Content(schema = @Schema(implementation = Person.class)),
                 headers = @Header(name = "Location"),
                 responseCode = "201"
            ),
            @ApiResponse(
                responseCode = "409", 
                description = "Person with such e-mail already exists"
            )
        }
    )
    public Response addPerson(@Context final UriInfo uriInfo,
            @Parameter(description = "Person", required = true) @Valid Person payload) {

        final Person person = service.add(payload);
        return Response
             .created(uriInfo.getRequestUriBuilder().path(person.getEmail()).build())
             .entity(person)
             .build();
    }
    
    @Path("/{email}")
    @DELETE
    @Operation(
        description = "Delete existing person",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Person has been deleted"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Person with such e-mail doesn't exists"
            )
        }
    )
    public Response deletePerson(@Parameter(description = "E-Mail address to lookup for", required = true ) @PathParam("email") final String email) {
        return service
            .remove(email)
            .map(r -> Response.noContent().build())
            .orElseThrow(() -> new NotFoundException("Person with such e-mail doesn't exists"));
    }
}
