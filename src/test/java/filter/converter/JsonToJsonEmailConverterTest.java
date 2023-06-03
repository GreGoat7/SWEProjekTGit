package filter.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import adressmodel.Email;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonToJsonEmailConverterTest {

    @Test
    void testConvert() {
        // Vorbereitung
        String inputFilePath = "src/test/resources/adresse.json";
        String outputFilePath = "src/test/resources/converted_emails.json";
        String expectedFilePath = "src/test/resources/expected_emails.json";
        String inputFilePath2 = "src/test/resources/single_adresse.json";
        String outputFilePath2 = "src/test/resources/single_converted_emails.json";
        String expectedFilePath2 = "src/test/resources/expected_single_emails.json";
        JsonToJsonEmailConverter converter = new JsonToJsonEmailConverter();

        // Ausführung
        try {
            converter.process(inputFilePath, outputFilePath);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }

        try {
            converter.process(inputFilePath2, outputFilePath2);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }

        // Überprüfung
        try {
            File outputFile = new File(outputFilePath);
            List<Email> actual = JsonUtils.fromJson(outputFile, new TypeReference<>() {});

            File expectedFile = new File(expectedFilePath);
            List<Email> expected = JsonUtils.fromJson(expectedFile, new TypeReference<>() {});

            assertEquals(expected, actual, "Converted emails should match the expected result");
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }

        try {
            File outputFile2 = new File(outputFilePath2);
            List<Email> actual = JsonUtils.fromJson(outputFile2, new TypeReference<>() {});

            File expectedFile2 = new File(expectedFilePath2);
            List<Email> expected = JsonUtils.fromJson(expectedFile2, new TypeReference<>() {});

            assertEquals(expected, actual, "Converted emails should match the expected result");
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }


    }
}
