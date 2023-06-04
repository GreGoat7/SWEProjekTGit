package filter.converter;

import adressmodel.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import utils.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonToYamlConverterTest {

    @Test
    void testProcessForAddressDetails() throws IOException {
        runTest("src/test/resources/addressdetails.json", "src/test/resources/addressdetails.yaml",
                new TypeReference<AddressDetails>() {});
    }

    @Test
    void testProcessForAddresses() throws IOException {
        runTest("src/test/resources/adresse.json", "src/test/resources/adresse.yaml",
                new TypeReference<List<Address>>() {});
    }

    @Test
    void testProcessForEmail() throws IOException {
        runTest("src/test/resources/email.json", "src/test/resources/email.yaml",
                new TypeReference<Email>() {});
    }

    @Test
    void testProcessForAddressDetailsList() throws IOException {
        runTest("src/test/resources/addressdetailslist.json", "src/test/resources/addressdetailslist.yaml",
                new TypeReference<List<AddressDetails>>() {});
    }

    private <T> void runTest(String inputFilePath, String outputFilePath, TypeReference<T> type) throws IOException {
        // Create JsonToYamlConverter object
        JsonToYamlConverter converter = new JsonToYamlConverter();

        // Execute the process method
        converter.process(inputFilePath, outputFilePath);

        // Load the expected output data
        File outputFile = new File(outputFilePath);
        assertTrue(outputFile.exists(), "Output file should exist");

        // Check if the result is as expected
        T resultData = YamlUtils.fromYaml(outputFile, type);

        // Load the input data
        T inputData = JsonUtils.fromJson(new File(inputFilePath), type);

        // Validate the results
        assertEquals(inputData, resultData, "Converted data should match input data");
    }
}
