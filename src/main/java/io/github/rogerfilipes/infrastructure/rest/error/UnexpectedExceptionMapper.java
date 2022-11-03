package io.github.rogerfilipes.infrastructure.rest.error;

import io.github.rogerfilipes.application.models.GenericErrorModel;
import io.quarkus.logging.Log;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnexpectedExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Log.error(exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new GenericErrorModel("Unexpected error occurred")).build();
    }
}
