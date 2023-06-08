package utils;

import addressmodel.Address;
import addressmodel.Email;
import addressmodel.Person;
import addressmodel.Phone;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.NotAListException;
import exceptions.WrongFiletypeException;
import exceptions.WrongFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class YamlUtils implements IUtils {
    private static YAMLMapper mapper;

    static {
        mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Override
    public <T> T toJava(File yamlFile, TypeReference<T> type) throws WrongFiletypeException{

        try {
            return mapper.readValue(new InputStreamReader(new FileInputStream(yamlFile), StandardCharsets.UTF_8), type);
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
    public TypeReference<?> determineListType(File yamlFile) throws IOException, NotAListException, WrongFormatException, WrongFiletypeException {
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(yamlFile);
        }
        catch (Exception e){
            throw new WrongFiletypeException("Fehler beim Umwandeln der XML-Datei: Eingangsdatei ist kein JSON-File");
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
            throw new WrongFormatException("Felder im YAML-File enstprechen nicht den erwartenden Feldern");
        }
        throw new NotAListException("Das YAML-File muss eine Liste sein, aber ist keine.");
    }
}
