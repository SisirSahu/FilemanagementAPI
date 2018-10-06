package filemanagement.api.exceptions;

import filemanagement.api.ro.Error;
import filemanagement.api.util.JsonUtils;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static filemanagement.api.util.JsonUtils.convertToJson;

public class InternalServerException implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException exception) {
        Error error = new Error(500, "Internal Server Error", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(convertToJson(error)).build();
    }
}
