package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import adressmodel.*;

import java.io.File;
import java.io.IOException;
import java.util.List;




class JsonUtilsTest {
    @Test
    void testFromJsonToAddress() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/Address.json");
        Class<Address> targetClass = Address.class;

        // Ausführung
        Address result;
        try {
            result = JsonUtils.fromJson(inputFile, new TypeReference<Address>() {});
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
            result = JsonUtils.fromJson(inputFile, new TypeReference<Email>() {});
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            result = null;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");

        // Hier werden die erwarteten Werte überprüft
        assertNotNull(result, "Result should not be null");
        assertEquals("office", result.getType(), "Type should be 'office'");
        assertNotNull(result.getEmailAddress(), "Person list should not be null");
        assertEquals(2, result.getEmailAddress().size(), "Person list should have 2 emails");
        assertTrue(result.getEmailAddress().contains("fred.smith@my-work.com"), "Person list should contain 'fred.smith@my-work.com'");
        assertTrue(result.getEmailAddress().contains("fsmith@my-work.com"), "Person list should contain 'fsmith@my-work.com'");
    }




    @Test
    void testFromAddressListJsonToJava() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/AddressList.json");
        TypeReference<List<Address>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Address> result;
        try {
            result = JsonUtils.fromJson(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            return;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");

        // Beispiel: Überprüfen des ersten Person-Objekts in der Liste
        Address secondDetails = result.get(1);
        assertEquals("Test",secondDetails.getCity(), "Falsch");


        // usw. für die restlichen Adressen und Felder
    }

    @Test
    void testPersonListJsonToJava() {
        File inputFile = new File("src/test/resources/adresse.json");
        TypeReference<List<Person>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Person> result;
        try {
            result = JsonUtils.fromJson(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            return;
        }

        // Überprüfung
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");

        // Beispiel: Überprüfen der einzelnen Person-Objekte in der Liste
        validateAddress(result.get(0), "Fred", "Smith", "28", "Hursley Park", "Winchester", "SO21 2JN", 4, 2);
        validateAddress(result.get(1), "Dieter", "M\u00FCller", "32", "Nordbahnhofstr", "Stuttgart", "70191", 2, 1);
    }

    @Test
    void testDetermineListType() {
        // Vorbereitung
        File personFile = new File("src/test/resources/adresse.json");
        File emailFile = new File("src/test/resources/converted_emails.json");
        /*File phoneFile = new File("src/test/resources/PhoneList.json");
        File addressFile = new File("src/test/resources/AddressList.json"); */

        // Ausführung und Überprüfung
        try {
            assertEquals(
                    new TypeReference<List<Person>>() {}.getType(),
                    JsonUtils.determineListType(personFile).getType(),
                    "Expected List<Person> type"
            );

            assertEquals(
                    new TypeReference<List<Email>>() {}.getType(),
                    JsonUtils.determineListType(emailFile).getType(),
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
            ); */
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
        }
    }


    // Hilfsmethode zur Validierung eines Person-Objekts
    private void validateAddress(Person person, String firstName, String surname, String age,
                                 String street, String city, String postcode,
                                 int expectedPhoneCount, int expectedEmailCount) {
        assertEquals(firstName, person.getFirstName(), "Invalid first name");
        assertEquals(surname, person.getSurname(), "Invalid surname");
        assertEquals(age, person.getAge(), "Invalid age");

        // Validate Address
        Address details = person.getAddress();
        assertNotNull(details, "Address should not be null");
        assertEquals(street, details.getStreet(), "Invalid street");
        assertEquals(city, details.getCity(), "Invalid city");
        assertEquals(postcode, details.getPostcode(), "Invalid postcode");

        // Validate Phone count
        List<Phone> phones = person.getPhone();
        assertNotNull(phones, "Phone list should not be null");
        assertEquals(expectedPhoneCount, phones.size(), "Invalid number of phones");

        // Validate Email count
        List<Email> emails = person.getEmail();
        assertNotNull(emails, "Email list should not be null");
        assertEquals(expectedEmailCount, emails.size(), "Invalid number of emails");
    }







}
