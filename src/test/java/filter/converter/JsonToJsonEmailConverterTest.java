package filter.converter;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonToJsonEmailConverterTest {

    @Test
    void testConvert() {
        // Vorbereitung
        String inputFilePath = "src/test/resources/adresse.json";
        JsonToJsonEmailConverter converter = new JsonToJsonEmailConverter();

        // Ausführung und Überprüfung
        try {
            String outputFilePath = converter.process(inputFilePath);
            // checkResult(outputFilePath, expectedFilePath);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }
    }

    /*private void checkResult(String outputFilePath, String expectedFilePath) throws IOException
        File outputFile = new File(outputFilePath);
        List<Email> actual = JsonUtils.fromJson(outputFile, new TypeReference<>() {});

        File expectedFile = new File(expectedFilePath);
        List<Email> expected = JsonUtils.fromJson(expectedFile, new TypeReference<>() {});

        assertEquals(expected, actual, "Converted emails should match the expected result");
    } */
}
