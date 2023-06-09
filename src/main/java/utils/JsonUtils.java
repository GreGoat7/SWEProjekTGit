package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import addressmodel.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import exceptions.*;

public class JsonUtils implements IUtils{
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

    @Override
    public <T> T toJava(File jsonFile, TypeReference<T> typeReference) throws WrongFiletypeException {
        //JsonNode rootNode = mapper.readTree(jsonFile);
        try {
            return mapper.readValue(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8), typeReference);
        }
        catch (Exception e){
            throw new WrongFiletypeException("Fehler beim Umwandeln der Json-Datei: Eingangsdatei ist kein Json-File");
        }
    }

    @Override
    public <T> void fromJava(T obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }


    @Override
    public TypeReference<?> determineListType(File jsonFile) throws WrongFiletypeException, NotAListException, WrongFormatException {
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonFile);
        }
        catch (Exception e){
            throw new WrongFiletypeException("Fehler beim Umwandeln der JSON-Datei: Eingangsdatei ist kein JSON-File");
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
            throw new WrongFormatException("Felder im JSON-File enstprechen nicht den erwartenden Feldern");
        }
        throw new NotAListException("Das JSON-File muss eine Liste sein, aber ist keine.");
    }
}
