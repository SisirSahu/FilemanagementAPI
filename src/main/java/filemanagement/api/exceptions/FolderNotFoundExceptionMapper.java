package filemanagement.api.exceptions;

import filemanagement.api.ro.Error;
import filemanagement.api.util.JsonUtils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static filemanagement.api.util.JsonUtils.convertToJson;

public class FolderNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        Error error = new Error(404, "Parent Folder ID is not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(convertToJson(error)).build();
    }
}
