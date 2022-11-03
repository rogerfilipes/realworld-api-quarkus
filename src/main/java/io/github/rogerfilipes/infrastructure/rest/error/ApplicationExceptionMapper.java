package io.github.rogerfilipes.infrastructure.rest.error;

import io.github.rogerfilipes.application.models.GenericErrorModel;
import io.github.rogerfilipes.domain.exception.*;
import io.github.rogerfilipes.infrastructure.rest.StatusExtended;
import io.quarkus.logging.Log;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    private static final HashMap<Class<? extends ApplicationException>, Response.StatusType> errorCodes = new HashMap<>();

    static {
        errorCodes.put(ArticleNotFoundException.class, Response.Status.NOT_FOUND);
        errorCodes.put(CommentNotFoundException.class, Response.Status.NOT_FOUND);
        errorCodes.put(EmailAlreadyExistsException.class, StatusExtended.UNPROCESSABLE_ENTITY);
        errorCodes.put(InvalidUserLoginException.class, Response.Status.UNAUTHORIZED);
        errorCodes.put(TagAlreadyExistsException.class, StatusExtended.UNPROCESSABLE_ENTITY);
        errorCodes.put(UsernameAlreadyExistsException.class, StatusExtended.UNPROCESSABLE_ENTITY);
        errorCodes.put(UsernameNotFoundException.class, Response.Status.NOT_FOUND);
        errorCodes.put(UserNotFoundException.class, Response.Status.BAD_REQUEST);
    }
    @Override
    public Response toResponse(ApplicationException exception) {

        if(errorCodes.containsKey(exception.getClass())){
            return handleMappedException(exception);
        }

        Log.error("Exception not mapped %s", exception.getClass(), exception );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private Response handleMappedException(ApplicationException exception) {
        return Response.status(errorCodes.get(exception.getClass())).entity(new GenericErrorModel(exception.getMessage())).build();
    }
}
