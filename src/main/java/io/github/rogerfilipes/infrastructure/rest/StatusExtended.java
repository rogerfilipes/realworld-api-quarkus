package io.github.rogerfilipes.infrastructure.rest;

import javax.ws.rs.core.Response;

public enum StatusExtended implements Response.StatusType {

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

    private final int code;
    private final String reason;
    private final Response.Status.Family family;


    StatusExtended(int code, String reason) {
        this.code = code;
        this.reason = reason;
        this.family = Response.Status.Family.familyOf(code);
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public Response.Status.Family getFamily() {
        return family;
    }

    @Override
    public String getReasonPhrase() {
        return reason;
    }
}
