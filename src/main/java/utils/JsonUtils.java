package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /*public static <T> T fromJson(File jsonFile, Class<T> classType) throws IOException {
        JsonNode rootNode = mapper.readTree(jsonFile);
        return mapper.readValue(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8), classType);
    } */

    public static <T> T fromJson(File jsonFile, TypeReference<T> typeReference) throws IOException {
        //JsonNode rootNode = mapper.readTree(jsonFile);
        return mapper.readValue(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8), typeReference);
    }

    public static void toJson(Object obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }

    public static boolean isArray(File jsonFile) throws IOException{
        JsonNode rootNode = mapper.readTree(jsonFile);
        if(rootNode.isArray()){
            return true;
        }
        else{
            return false;
        }
    }
}
