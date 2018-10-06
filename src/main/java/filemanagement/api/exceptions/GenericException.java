package filemanagement.api.exceptions;

import filemanagement.api.ro.Error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static filemanagement.api.util.JsonUtils.convertToJson;

public class GenericException implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        int errorcode;
        String errormsg;
        if (exception.getMessage().startsWith("HTTP")) {
            errorcode = Integer.parseInt(exception.getMessage().substring(5, 8));
            errormsg = exception.getMessage().substring(9);
        } else {
            errorcode = 500;
            errormsg = "Internal Server Eror";
        }
        return Response.status(errorcode).entity(
                convertToJson(new Error(errorcode, errormsg, exception.getMessage()))).build();
    }
}
