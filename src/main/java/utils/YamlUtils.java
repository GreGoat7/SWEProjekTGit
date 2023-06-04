package utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
}
