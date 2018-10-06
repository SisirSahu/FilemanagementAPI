package filemanagement.api.dao;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilemamanagementDao {
    public List<File> gettFilesInFolder(Drive service, String folderId) throws IOException {
        List<File> result = new ArrayList<>();
        String query = ((folderId != null) && !folderId.equals("root")) ? ("trashed=false and '" + folderId + "' in parents") : "trashed=false and 'root' in parents";
        Drive.Files.List request = service.files().list().setQ(query).setSpaces("drive");
        do {
            try {
                FileList files = request.execute();
                result.addAll(files.getFiles());
                request.setPageToken(files.getNextPageToken());
            } catch (IOException e) {
                request.setPageToken(null);
                throw new InternalServerErrorException();
            }
        } while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);
        return result;
    }

    public File createGoogleFile(Drive service, String googleFolderIdParent,
                                 String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(customFileName);
        fileMetadata.setParents(Collections.singletonList(googleFolderIdParent));
        File file = service.files().create(fileMetadata, uploadStreamContent)
                .setFields("id").execute();
        return file;
    }

    public InputStream downloadFileContentSream(Drive service, String fileId) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            service.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            throw new BadRequestException("invalid File Format");
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public boolean isFolderExists(Drive service, String folderId) {
        if (folderId != null && !folderId.equals("") && !folderId.equalsIgnoreCase("root")) {
            File file = null;
            try {
                file = service.files().get(folderId).execute();
            } catch (IOException e) {
                throw new NotFoundException();
            }
            return file != null && file.getMimeType().equals("application/vnd.google-apps.folder");
        } else {
            return true;
        }
    }

}
