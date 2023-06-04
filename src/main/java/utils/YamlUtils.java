package utils;

import adressmodel.Address;
import adressmodel.Email;
import adressmodel.Person;
import adressmodel.Phone;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class YamlUtils {
    private static YAMLMapper mapper;

    static {
        mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T fromYaml(File yamlFile, Class<T> classType) throws IOException {
        JsonNode rootNode = mapper.readTree(yamlFile);
        return mapper.readValue(new InputStreamReader(new FileInputStream(yamlFile), StandardCharsets.UTF_8), classType);
    }

    public static <T> T fromYaml(File yamlFile, TypeReference<T> type) throws IOException {
        JsonNode rootNode = mapper.readTree(yamlFile);
        return mapper.readValue(new InputStreamReader(new FileInputStream(yamlFile), StandardCharsets.UTF_8), type);
    }

    public static void toYaml(Object obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }

    public static boolean isArray(File yamlFile) throws IOException{
        JsonNode rootNode = mapper.readTree(yamlFile);
        return rootNode.isArray();
    }

    public static TypeReference<?> determineListType(File yamlFile) throws IOException {
        JsonNode rootNode = mapper.readTree(yamlFile);
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
        throw new InvalidFormatException("Das JSON-Format entspricht nicht erwarteten Feldern", yamlFile, Object.class);
    }
}
