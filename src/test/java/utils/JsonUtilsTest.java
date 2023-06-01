package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.JsonUtils;
import adressmodel.AddressDetails;
import adressmodel.Email;

import java.io.File;
import java.io.IOException;

class JsonUtilsTest {
    @Test
    void testFromJsonToAddressDetails() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/AddressDetails.json");
        Class<AddressDetails> targetClass = AddressDetails.class;

        // Ausführung
        AddressDetails result;
        try {
            result = JsonUtils.fromJson(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            result = null;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");

        // Hier werden die erwarteten Werte überprüft
        assertEquals("Hursley Park", result.getStreet(), "Street should be 'Hursley Park'");
        assertEquals("Winchester", result.getCity(), "City should be 'Winchester'");
        assertEquals("SO21 2JN", result.getPostcode(), "Postcode should be 'SO21 2JN'");
    }

    @Test
    void testFromJsonToEmail() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/Email.json");
        Class<Email> targetClass = Email.class;

        // Ausführung
        Email result;
        try {
            result = JsonUtils.fromJson(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            result = null;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");

        // Hier werden die erwarteten Werte überprüft
        assertNotNull(result, "Result should not be null");
        assertEquals("office", result.getType(), "Type should be 'office'");
        assertNotNull(result.getEmailAddress(), "Address list should not be null");
        assertEquals(2, result.getEmailAddress().size(), "Address list should have 2 emails");
        assertTrue(result.getEmailAddress().contains("fred.smith@my-work.com"), "Address list should contain 'fred.smith@my-work.com'");
        assertTrue(result.getEmailAddress().contains("fsmith@my-work.com"), "Address list should contain 'fsmith@my-work.com'");
    }


}
