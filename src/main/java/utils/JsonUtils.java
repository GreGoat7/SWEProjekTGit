package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import adressmodel.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        try {
            return mapper.readValue(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8), typeReference);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Fehler beim Umwandeln der Json-Datei: Eingangsdatei ist kein Json-File");
        }
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

    public static TypeReference<?> determineListType(File jsonFile) throws IOException {
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonFile);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Fehler beim Umwandeln der Json-Datei: Eingangsdatei ist kein Json-File");
        }

        if (rootNode.isArray() && rootNode.size() > 0) {
            JsonNode firstElement = rootNode.get(0);
            // Prüfen auf das Vorhandensein bestimmter Felder in den JSON-Elementen
            if (firstElement.has("FirstName")) {
                return new TypeReference<List<Person>>() {};
            } else if (firstElement.has("type") && firstElement.has("address")) {
                return new TypeReference<List<Email>>() {};
            }
            else if(firstElement.has("type") && firstElement.has("number")){
                return new TypeReference<List<Phone>>() {};
            }
            else if(firstElement.has("Street")){
                return new TypeReference<List<Address>>() {};
            }
        }
        throw new InvalidFormatException("Das JSON-Format entspricht nicht erwarteten Feldern", jsonFile, Object.class);
    }
}
