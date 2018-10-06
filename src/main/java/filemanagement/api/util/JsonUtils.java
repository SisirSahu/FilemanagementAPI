package filemanagement.api.util;

import com.google.gson.Gson;
import filemanagement.api.ro.FileRO;

import java.util.List;

public class JsonUtils {
    public static String convertToJson(Object ros) {
        Gson gson = new Gson();
        return gson.toJson(ros);
    }
}
