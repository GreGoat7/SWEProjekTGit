package utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.w3c.dom.Document;

public class XmlUtils {
    private static XmlMapper mapper;

    static {
        mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T fromXml(File xmlFile, Class<T> classType) throws IOException {
        return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), classType);
    }

    public static <T> T fromXml(File xmlFile, TypeReference<T> type) throws IOException {
        return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), type);
    }

    public static void toXml(Object obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }

    public static boolean isArray(File xmlFile) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        return doc.getDocumentElement().getElementsByTagName("*").getLength() > 1;
    }
}
