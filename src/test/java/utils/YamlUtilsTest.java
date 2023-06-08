/*package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import addressmodel.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

class YamlUtilsTest {
    @Test
    void testFromYamlToAddressDetails() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/AddressDetails.yaml");
        Class<Address> targetClass = Address.class;

        // Ausführung
        Address result;
        try {
            result = YamlUtils.toJava(inputFile, targetClass);
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
    void testFromYamlToEmail() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/Email.yaml");
        Class<Email> targetClass = Email.class;

        // Ausführung
        Email result;
        try {
            result = YamlUtils.toJava(inputFile, targetClass);
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

    @Test
    void testFromAddressDetailsListYamlToJava() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/AddressDetailsList.yaml");
        TypeReference<List<Address>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Address> result;
        try {
            result = YamlUtils.toJava(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            return;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");

        // Beispiel: Überprüfen des ersten Address-Objekts in der Liste
        Address secondDetails = result.get(1);
        assertEquals("Test",secondDetails.getCity(), "Falsch");

        // usw. für die restlichen Adressen und Felder
    }

    @Test
    void AddressListYamlToJava() {
        File inputFile = new File("src/test/resources/adresse.yaml");
        TypeReference<List<Person>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Person> result;
        try {
            result = YamlUtils.toJava(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            return;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");

        // Beispiel: Überprüfen der einzelnen Address-Objekte in der Liste
        validateAddress(result.get(0), "Fred", "Smith", 28, "Hursley Park", "Winchester", "SO21 2JN", 4, 2);
        validateAddress(result.get(1), "Dieter", "M\u00FCller", 32, "Nordbahnhofstr", "Stuttgart", "70191", 2, 1);
    }

    @Test
    void testDetermineListType() {
        // Vorbereitung
        File personFile = new File("src/test/resources/adresse.yaml");
        File emailFile = new File("src/test/resources/email.yaml");
        File phoneFile = new File("src/test/resources/PhoneList.json");
        File addressFile = new File("src/test/resources/AddressList.json");

        // Ausführung und Überprüfung
        try {
            assertEquals(
                    new TypeReference<List<Person>>() {}.getType(),
                    YamlUtils.determineListType(personFile).getType(),
                    "Expected List<Person> type"
            );

            assertEquals(
                    new TypeReference<List<Email>>() {}.getType(),
                    YamlUtils.determineListType(emailFile).getType(),
                    "Expected List<Email> type"
            );

            /*assertEquals(
                    new TypeReference<List<Phone>>() {}.getType(),
                    JsonUtils.determineListType(phoneFile).getType(),
                    "Expected List<Phone> type"
            );

            assertEquals(
                    new TypeReference<List<Address>>() {}.getType(),
                    JsonUtils.determineListType(addressFile).getType(),
                    "Expected List<Address> type"
            );
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }
    }

    // Hilfsmethode zur Validierung eines Address-Objekts
    private void validateAddress(Person address, String firstName, String surname, int age,
                                 String street, String city, String postcode,
                                 int expectedPhoneCount, int expectedEmailCount) {
        assertEquals(firstName, address.getFirstName(), "Invalid first name");
        assertEquals(surname, address.getSurname(), "Invalid surname");
        assertEquals(age, address.getAge(), "Invalid age");

        // Validate AddressDetails
        Address details = address.getAddress();
        assertNotNull(details, "AddressDetails should not be null");
        assertEquals(street, details.getStreet(), "Invalid street");
        assertEquals(city, details.getCity(), "Invalid city");
        assertEquals(postcode, details.getPostcode(), "Invalid postcode");

        // Validate Phone count
        List<Phone> phones = address.getPhone();
        assertNotNull(phones, "Phone list should not be null");
        assertEquals(expectedPhoneCount, phones.size(), "Invalid number of phones");

        // Validate Email count
        List<Email> emails = address.getEmail();
        assertNotNull(emails, "Email list should not be null");
        assertEquals(expectedEmailCount, emails.size(), "Invalid number of emails");
    }
} */
