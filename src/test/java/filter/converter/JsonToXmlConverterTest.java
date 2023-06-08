package filter.converter;

import addressmodel.*;
import addressmodel.Email;
import addressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import utils.XmlUtils;
import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonToXmlConverterTest {
    JsonToXmlConverter converter;

    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;

    @BeforeEach
    public void setUp() {
        converter = new JsonToXmlConverter();
    }

    @Test
    public void testProcessForValidInput() {
        String inputFilePath = "src/test/resources/JsonToYamlResources/Person.json"; // Hier geben Sie den Pfad zu Ihrer Testdatei an.
        String expectedOutputFilePath = "src\\test\\resources\\JsonToYamlResources\\Person.xml";

        assertDoesNotThrow(() -> {
            String result = converter.process(inputFilePath);
            assertEquals(expectedOutputFilePath, result);
            // Hier könnten Sie auch überprüfen, ob die resultierende XML-Datei den erwarteten Inhalt hat.
        });
    }

    @Test
    public void testProcessForNoJsonInput() {
        String inputFilePath = "src/test/resources/JsonToYamlResources/AddressList.xml"; // Hier geben Sie den Pfad zu einer ungültigen Testdatei an, die nicht in eine Liste konvertiert werden kann.

        assertThrows(IllegalArgumentException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    @Test
    public void testProcessForInvalidJsonInput() {
        String inputFilePath = "src/test/resources/JsonToYamlResources/AddressList.xml"; // Hier geben Sie den Pfad zu einer ungültigen Testdatei an, die nicht in eine Liste konvertiert werden kann.

        assertThrows(IllegalArgumentException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    @Test
    public void testProcessForNonexistentFile() {
        String inputFilePath = "path/to/nonexistent.json"; // Ein Pfad zu einer Datei, die nicht existiert.

        assertThrows(IOException.class, () -> {
            converter.process(inputFilePath);
        });
    }



    @Test
    void testProcessForPerson() throws IOException {
        runTest("src/test/resources/JsonToYamlResources/Person.json",
                new TypeReference<List<Person>>() {});
    }

    @Test
    void testProcessForEncryptedPerson() throws IOException {
        runTest("src/test/resources/JsonToYamlResources/personen.enc.json",
                new TypeReference<List<Person>>() {});
    }

    @Test
    void testProcessForAddress() throws IOException {
        runTest("src/test/resources/JsonToYamlResources/AddressList.json",
                new TypeReference<List<Address>>() {});
    }

    @Test
    void testProcessForEmail() throws IOException {
        runTest("src/test/resources/JsonToYamlResources/changedemail.json",
                new TypeReference<List<Email>>() {});
    }

    @Test
    void testProcessForPhone() throws IOException {
        runTest("src/test/resources/JsonToYamlResources/Phone.json",
                new TypeReference<List<Phone>>() {});
    }



    private <T> void runTest(String inputFilePath, TypeReference<T> type) throws IOException {
        // Create JsonToYamlConverter object
        JsonToXmlConverter converter = new JsonToXmlConverter();

        // Calculate output file path
        String outputFilePath = inputFilePath.replace(".json", ".xml");

        // Execute the process method
        converter.process(inputFilePath);

        // Load the expected output data
        File outputFile = new File(outputFilePath);
        assertTrue(outputFile.exists(), "Output file should exist");

        // Check if the result is as expected
        T resultData = xmlUtils.toJava(outputFile, type);

        // Load the input data
        T inputData = jsonUtils.toJava(new File(inputFilePath), type);

        // Validate the results
        assertEquals(inputData, resultData, "Converted data should match input data");
    }
}
