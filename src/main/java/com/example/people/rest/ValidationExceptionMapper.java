package com.example.people.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.validation.ResponseConstraintViolationException;

@Provider @ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        if (exception instanceof ConstraintViolationException) { 
            if (exception instanceof ResponseConstraintViolationException) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }        
}
