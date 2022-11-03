package io.github.rogerfilipes.infrastructure.rest.error;

import io.github.rogerfilipes.application.models.GenericErrorModel;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<String> violations = e.getConstraintViolations().stream().map(violation -> StringUtils.join(StringUtils.substringAfterLast(violation.getPropertyPath().toString(), "."), " : ", violation.getMessage())).collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity(new GenericErrorModel(violations.toArray(String[]::new))).build();
    }


}
