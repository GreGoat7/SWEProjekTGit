package filter.converter;

import addressmodel.*;
import addressmodel.Email;
import addressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import exceptions.NotAListException;
import exceptions.WrongFiletypeException;
import exceptions.WrongFormatException;
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

    //Überprüfen ob der richtige outputpath generiert wird
    @Test
    public void testProcessForCorrectOutputPath() {
        String inputFilePath = "src/test/resources/JsonToXmlResources/Person.json";
        String expectedOutputFilePath = "src\\test\\resources\\JsonToXmlResources\\Person.xml";

        assertDoesNotThrow(() -> {
            String result = converter.process(inputFilePath);
            assertEquals(expectedOutputFilePath, result);
        });
    }

    // Überpürft ob richtige Exception geworfen wird bei nicht Json File
    @Test
    public void testProcessForNoJsonInput() {
        String inputFilePath = "src/test/resources/JsonToXmlResources/Person.xml";

        assertThrows(WrongFiletypeException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    //  Überprüfen ob richtige Exception geworfen wird bei Json File mit falschen feldern
    @Test
    public void testProcessForInvalidJsonInput() {
        String inputFilePath = "src/test/resources/JsonToXmlResources/InvalidEmail.json";

        assertThrows(WrongFormatException.class, () -> {
            converter.process(inputFilePath);
        });
    }


    // Überprüfen ob richtige Exception geworfen wird bei Json die nicht existiert
    @Test
    public void testProcessForNonexistentFile() {
        String inputFilePath = "src/test/resources/doesnotexist.json";
        File aaa = new File(inputFilePath);
        assertThrows(IOException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    //Überprüfen ob richtige Exception geworfen wird bei Json File die keine Liste ist
    @Test
    public void testProcessForNoList() {
        String inputFilePath = "src/test/resources/JsonToXmlResources/Address.json";
        assertThrows(NotAListException.class, () -> {
            converter.process(inputFilePath);
        });
    }


    // Überprüfen ob richtige XML generiert wird bei Json Person file
    @Test
    void testProcessForPerson() throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
        runTest("src/test/resources/JsonToXmlResources/Person.json",
                new TypeReference<List<Person>>() {});
    }

    // Überprüfen ob richtige XML generiert wird bei Json Person encrypted file
    @Test
    void testProcessForEncryptedPerson() throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
        runTest("src/test/resources/JsonToXmlResources/personen.enc.json",
                new TypeReference<List<Person>>() {});
    }

    // Überprüfen ob richtige XML generiert wird bei Json Address file
    @Test
    void testProcessForAddress() throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
        runTest("src/test/resources/JsonToXmlResources/AddressList.json",
                new TypeReference<List<Address>>() {});
    }

    // Überprüfen ob richtige XML generiert wird bei Json Email file
    @Test
    void testProcessForEmail() throws IOException, NotAListException, WrongFiletypeException, WrongFormatException{
        runTest("src/test/resources/JsonToXmlResources/Email.json",
                new TypeReference<List<Email>>() {});
    }

    // Überprüfen ob richtige XML generiert wird bei Json Phone file
    @Test
    void testProcessForPhone() throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
        runTest("src/test/resources/JsonToXmlResources/Phone.json",
                new TypeReference<List<Phone>>() {});
    }


    //Methode die überprüft ob zwei Json und Xml datei gleich sind
    private <T> void runTest(String inputFilePath, TypeReference<T> type) throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
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
