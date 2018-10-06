package filemanagement.api.resources;


import com.google.api.services.drive.model.File;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import filemanagement.api.manager.FileManagementManager;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.security.GeneralSecurityException;

import static filemanagement.api.util.DriveServiceUtil.getDriveService;

@Path("/files")
public class FileManagementResource {
    @Inject
    FileManagementManager fileManagementManager;

    @GET
    @Path("/allFilesInFolder/{parentFolderId}")
    @Produces("application/json")
    public Response getFies(@PathParam("parentFolderId") String folderId) {
        return Response.ok(fileManagementManager.getAllFilesUsingFolderID(folderId)).build();
    }

    @POST
    @Path("uploadFileToFolder/{parentFolderId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Valid @NotNull @FormDataParam("file") InputStream uploadedInputStream,
                               @Valid @NotNull @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @PathParam("parentFolderId") String parentFolderId) {
        if (fileDetail.getFileName() == null) {
            throw new BadRequestException("uploded File missing");
        }
        fileManagementManager.uploadFiletoParentFolder(parentFolderId, uploadedInputStream, fileDetail.getFileName());
        return Response.status(201).entity("uploaded").build();

    }

    //ex Id: 1TEL5a1A7pIzPjvFhH-6_fKzX0R2XgwdC
    @GET
    @Path("/downloadFileById/{fileId}")
    public Response downloadFile(@PathParam("fileId") String fileId) {
        return fileManagementManager.downloadFileById(fileId);
    }

}
