package filemanagement.api.manager;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import filemanagement.api.dao.FilemamanagementDao;
import filemanagement.api.ro.FileRO;
import org.apache.tika.Tika;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static filemanagement.api.util.DriveServiceUtil.getDriveService;
import static filemanagement.api.util.JsonUtils.convertToJson;

public class FileManagementManager {
    @Inject
    private FilemamanagementDao filemamanagementDao;

    public String getAllFilesUsingFolderID(String folderId) throws NotFoundException {
        try {
            if (!(filemamanagementDao.isFolderExists(getDriveService(), folderId)))
                throw new NotFoundException();
            List<FileRO> fileROS = new ArrayList<>();
            filemamanagementDao.gettFilesInFolder(getDriveService(), folderId)
                    .forEach((file) -> fileROS.add(new FileRO(file.getId(),
                            file.getMimeType().equals("application/vnd.google-apps.folder") ? "folder" : "file",
                            file.getName())));
            return convertToJson(fileROS);
        } catch (IOException | GeneralSecurityException e) {
            throw new InternalServerErrorException();
        }
    }

    public boolean uploadFiletoParentFolder(String parentFolderid, InputStream content, String filename) throws NotFoundException {
        try {
            if (!filemamanagementDao.isFolderExists(getDriveService(), parentFolderid))
                throw new NotFoundException();
            AbstractInputStreamContent uploadStreamContent = new InputStreamContent(new Tika().detect(filename), content);
            filemamanagementDao.createGoogleFile(getDriveService(), parentFolderid, filename, uploadStreamContent);
            return true;
        } catch (IOException | GeneralSecurityException e) {
            throw new InternalServerErrorException("Error in Uploading");
        }
    }

    public Response downloadFileById(String fileId) {
        if (fileId == null || fileId.equals(""))
            throw new BadRequestException("FileId is missing");
        File file = null;
        try {
            file = getDriveService().files().get(fileId).execute();
        } catch (IOException e) {
            throw new BadRequestException("invalid FileID ");
        } catch (GeneralSecurityException e) {
            throw new InternalServerErrorException("Internal Server Error Try Again");
        }
        System.out.println(file.getMimeType());
        System.out.println(file.getName());

        InputStream inputStream = null;
        try {
            inputStream = filemamanagementDao.downloadFileContentSream(getDriveService(), fileId);
        } catch (IOException | GeneralSecurityException e) {
            throw new InternalServerErrorException("Internal Server Error Try Again");
        }
        ResponseBuilder builder = Response.ok();
        builder.entity(inputStream);
        builder.type(file.getMimeType());
        builder.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return builder.build();
    }

}