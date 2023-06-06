/*package filter.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import adressmodel.Person;
import adressmodel.Email;
import adressmodel.Address;
import adressmodel.Phone;
import


import java.util.Collections;
import java.util.List;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonToXmlConverterTest {
    private JsonToXmlConverter converter;

    @BeforeEach
    void setUp() {
        converter = new JsonToXmlConverter();
    }

    @Test
    void testProcess() throws IOException {
        // Setzen Sie den Pfad zu Ihrer vorhandenen JSON-Datei
        String jsonFilePath = "src/test/resources/Test.json";

        // Führen Sie die Konvertierung durch
        converter.process(jsonFilePath);

        // Prüfen, ob eine XML-Datei mit dem erwarteten Namen erstellt wurde
        Path expectedXmlFilePath = Paths.get(jsonFilePath.replace(".json", ".xml"));
        assertTrue(Files.exists(expectedXmlFilePath), "Die XML-Datei wurde nicht erstellt.");
    }
    @Test
    public void testProcess_ValidJsonPerson() throws IOException {
        // Einrichten von Mock-Daten
        Person person = new Person("John Doe", "1234", new Address("Street", "City", "12345"), new Email("john.doe@example.com"), new Phone("1234567890"));
        List<Person> persons = Collections.singletonList(person);
        File testFile = new File("person.json");

        // Mocken der Methoden
        wait(jsonUtils.determineListType(testFile)).thenReturn(new TypeReference<List<Person>>() {});
        wait(jsonUtils.fromJson(testFile, new TypeReference<List<Person>>() {})).thenReturn(persons);
        doNothing().wait(xmlUtils).toXml(persons, "person.xml");

        // Aufrufen der Methode
        String result = converter.process("person.json");

        // Überprüfen der Ergebnisse
        assertEquals("person.xml", result);
        verify(jsonUtils).fromJson(testFile, new TypeReference<List<Person>>() {});
        verify(xmlUtils).toXml(persons, "person.xml");
    }

    @Test
    public void testProcess_ValidJsonAddress() throws IOException {
        // Einrichten von Mock-Daten
        Address address = new Address("Street", "City", "12345");
        List<Address> addresses = Collections.singletonList(address);
        File testFile = new File("address.json");

        // Mocken der Methoden
        when(jsonUtils.determineListType(testFile)).thenReturn(new TypeReference<List<Address>>() {});
        when(jsonUtils.fromJson(testFile, new TypeReference<List<Address>>() {})).thenReturn(addresses);
        doNothing().when(xmlUtils).toXml(addresses, "address.xml");

        // Aufrufen der Methode
        String result = converter.process("address.json");

        // Überprüfen der Ergebnisse
        assertEquals("address.xml", result);
        verify(jsonUtils).fromJson(testFile, new TypeReference<List<Address>>() {});
        verify(xmlUtils).toXml(addresses, "address.xml");
    }

    @Test
    public void testProcess_InvalidJsonFile() throws IOException {
        // Einrichten von Mock-Daten
        File testFile = new File("invalid.json");

        // Mocken der Methoden
        when(jsonUtils.determineListType(testFile)).thenThrow(new IOException());

        // Überprüfen, ob eine IOException ausgelöst wird
        assertThrows(IOException.class, () -> converter.process("invalid.json"));

        // Überprüfen der Ergebnisse
        verify(jsonUtils).determineListType(testFile);
        verify(jsonUtils, never()).fromJson(any(File.class), any(TypeReference.class));
        verify(xmlUtils, never()).toXml(anyList(), anyString());
    }

}
*/