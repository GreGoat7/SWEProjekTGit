package utils;

import addressmodel.Address;
import addressmodel.Email;
import addressmodel.Person;
import addressmodel.Phone;
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
import java.util.List;

import exceptions.NotAListException;
import exceptions.WrongFiletypeException;
import exceptions.WrongFormatException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils implements IUtils{
    private static XmlMapper mapper;

    static {
        mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /*public static <T> T fromXml(File xmlFile, Class<T> classType) throws IOException {
        return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), classType);
    } */

    @Override
    public <T> T toJava(File xmlFile, TypeReference<T> type) throws WrongFiletypeException {
        try {
            return mapper.readValue(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8), type);
        }
        catch (Exception e){
            throw new WrongFiletypeException("Fehler beim Umwandeln der Xml-Datei: Eingangsdatei ist kein Xml-File");
        }

    }

    @Override
    public <T> void fromJava(T obj, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), obj);
    }

    @Override
    public TypeReference<?> determineListType(File xmlFile) throws NotAListException, WrongFiletypeException, WrongFormatException {
        Document doc = null;
        try{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        }catch (Exception e){
            throw new WrongFiletypeException("Fehler beim Umwandeln der XML-Datei: Eingangsdatei ist kein XML-File");
        }


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
            throw new WrongFormatException("Felder im XML-File enstprechen nicht den erwartenden Feldern");
        }

        throw new NotAListException("Das XML-File muss eine Liste sein, aber ist keine.");
    }

    // Diese Methode bestimmt den Typ des Inhalts in der gegebenen NodeList.

    // @param nodeList Die NodeList, deren Inhaltstyp bestimmt werden soll.
    // @return Ein TypeReference-Objekt, das den Typ des Inhalts der NodeList darstellt.
    // Gibt null zurück, wenn der Inhaltstyp nicht bestimmt werden kann.
    private static TypeReference<?> determineTypeFromNodeList(NodeList nodeList) {

        // Holt das erste Node aus der NodeList.
        Node firstNode = nodeList.item(0);
        // Prüft, ob das erste Node ein Element-Node ist.
        if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
            // Konvertiert das Node zu einem Element.
            Element firstElement = (Element) firstNode;

            // Prüft, ob das Element "FirstName"-Tags enthält.
            // Wenn ja, wird angenommen, dass die NodeList Personen darstellt.
            if (firstElement.getElementsByTagName("FirstName").getLength() > 0) {
                return new TypeReference<List<Person>>() {};
            }
            // Prüft, ob das Element "type"- und "number"-Tags enthält.
            // Wenn ja, wird angenommen, dass die NodeList Telefonnummern darstellt.
            else if (firstElement.getElementsByTagName("type").getLength() > 0 &&
                    firstElement.getElementsByTagName("number").getLength() > 0) {
                return new TypeReference<List<Phone>>() {};
            }
            // Prüft, ob das Element "Street"-Tags enthält.
            // Wenn ja, wird angenommen, dass die NodeList Adressen darstellt.
            else if (firstElement.getElementsByTagName("Street").getLength() > 0) {
                return new TypeReference<List<Address>>() {};
            }
            // Prüft, ob das Element "type"- und "address"-Tags enthält.
            // Wenn ja, wird angenommen, dass die NodeList E-Mails darstellt.
            else if (firstElement.getElementsByTagName("type").getLength() > 0 &&
                    firstElement.getElementsByTagName("address").getLength() > 0) {
                return new TypeReference<List<Email>>() {};
            }
        }

        // Wenn keiner der obigen Fälle zutrifft, kann der Typ der NodeList nicht bestimmt werden und null wird zurückgegeben.
        return null;
    }




}
