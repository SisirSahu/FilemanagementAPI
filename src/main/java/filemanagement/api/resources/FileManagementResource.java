package filemanagement.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/file")
public class FileManagementResource {
    @GET
    @Path("/search")
    public Response getFies() {
        return Response.ok("get files").build();
    }

    @GET
    @Path("/download")
    public Response downloadFile() {
        return Response.ok("download files").build();
    }

    @POST
    @Path("/upload")
    public Response uploadFile() {
        return Response.ok("upload File files").build();
    }

    @POST
    @Path(":parentID/upload")
    public Response uploadFileToParent() {
        return Response.ok("upload File files").build();
    }
}
