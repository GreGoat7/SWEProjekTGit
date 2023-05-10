package utils;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonUtils {
    public static <T> T fromJson(InputStream inputStream, Class<T> classType){
        // gibt ein Objekt der KLasse "classType" zurück
    }

    public static <T> void toJson(T object, OutputStream outputStream) {
        // Wandelt ein Objekt in ein Outputstream im Json-Format um
    }

    // eventuell weitere Utils


}
