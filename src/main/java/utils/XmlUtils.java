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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

    public static TypeReference<?> determineListType(File xmlFile) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList itemList = doc.getElementsByTagName("item");
        if (itemList.getLength() > 0) {
            TypeReference<?> itemType = determineTypeFromNodeList(itemList);
            if (itemType != null) {
                return itemType;
            }
        }

        NodeList rowList = doc.getElementsByTagName("row");
        if (rowList.getLength() > 0) {
            TypeReference<?> rowType = determineTypeFromNodeList(rowList);
            if (rowType != null) {
                return rowType;
            }
        }

        throw new InvalidFormatException("Das XML-Format entspricht nicht erwarteten Feldern", xmlFile, Object.class);
    }

    private static TypeReference<?> determineTypeFromNodeList(NodeList nodeList) {
        Node firstNode = nodeList.item(0);
        if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
            Element firstElement = (Element) firstNode;
            if (firstElement.getElementsByTagName("FirstName").getLength() > 0) {
                return new TypeReference<List<Person>>() {
                };
            } else if (firstElement.getElementsByTagName("type").getLength() > 0 &&
                    firstElement.getElementsByTagName("number").getLength() > 0) {
                return new TypeReference<List<Phone>>() {
                };
            } else if (firstElement.getElementsByTagName("Street").getLength() > 0) {
                return new TypeReference<List<Address>>() {
                };
            } else if (firstElement.getElementsByTagName("type").getLength() > 0 &&
                    firstElement.getElementsByTagName("address").getLength() > 0) {
                return new TypeReference<List<Email>>() {
                };
            }
        }

        return null;
    }





}
