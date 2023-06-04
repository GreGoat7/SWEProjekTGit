package utils;

import adressmodel.Address;
import adressmodel.Email;
import adressmodel.Person;
import adressmodel.Phone;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlUtils {
    private static XmlMapper mapper;

    static {
        mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /*public static <T> T fromXml(File xmlFile, Class<T> classType) throws IOException {
        return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), classType);
    } */

    public static <T> T fromXml(File xmlFile, TypeReference<T> type) throws IOException {
        return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), type);
    }

    public static void toXml(Object obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }

    public static boolean isArray(File xmlFile) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);

            // Wird zur Ermittlung des obersten Knoten im XML-Dokument verwendet
            Element rootElement = doc.getDocumentElement();

            // Überprüft, ob der oberste Knoten "ArrayList" oder "root" ist
            return rootElement.getNodeName().equals("ArrayList") || rootElement.getNodeName().equals("root");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Behandlung der Ausnahmen, z.B. durch Protokollierung oder Ausgabe einer Fehlermeldung
            System.err.println("Fehler beim Parsen der XML-Datei: " + e.getMessage());
            return false;  // Rückgabe von false, da das Parsen fehlgeschlagen ist
        }
    }

    public static TypeReference<?> determineListType(File xmlFile) throws IOException {
        JsonNode rootNode = mapper.readTree(xmlFile);
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
            throw new InvalidFormatException("Das XML-Format entspricht nicht erwarteten Feldern", xmlFile, Object.class);
    }


}
