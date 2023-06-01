package utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T fromJson(File jsonFile, Class<T> classType) throws IOException {
        return mapper.readValue(jsonFile, classType);
    }

    public static void toJson(Object obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }
}
