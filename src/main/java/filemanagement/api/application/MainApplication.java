package filemanagement.api.application;

import filemanagement.api.exceptions.FolderNotFoundExceptionMapper;
import filemanagement.api.exceptions.GenericException;
import filemanagement.api.exceptions.InternalServerException;
import filemanagement.api.resources.FileManagementResource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class MainApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(FileManagementResource.class);
        classes.add(FolderNotFoundExceptionMapper.class);
        classes.add(InternalServerException.class);
        classes.add(GenericException.class);
        return classes;
    }
}
