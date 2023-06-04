package utils;

import adressmodel.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlUtilsTest {
    @Test
    void testFromXmlToAddressDetails(){
        File inputFile = new File("src/test/resources/AddressDetails.xml");
        Class<AddressDetails> targetClass = AddressDetails.class;

        // Ausführung
        AddressDetails result;
        try {
            result = XmlUtils.fromXml(inputFile, targetClass);
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
    void testFromXmlToEmail() {
        // Vorbereitung
        File inputFile = new File("src/test/resources/email.xml");
        Class<Email> targetClass = Email.class;

        // Ausführung
        Email result;
        try {
            result = XmlUtils.fromXml(inputFile, targetClass);
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
    void AddressListXmlToJava() {
        File inputFile = new File("src/test/resources/adresse.xml");
        TypeReference<List<Address>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Address> result;
        try {
            result = XmlUtils.fromXml(inputFile, targetClass);
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

    // Hilfsmethode zur Validierung eines Address-Objekts
    private void validateAddress(Address address, String firstName, String surname, int age,
                                 String street, String city, String postcode,
                                 int expectedPhoneCount, int expectedEmailCount) {
        assertEquals(firstName, address.getFirstName(), "Invalid first name");
        assertEquals(surname, address.getSurname(), "Invalid surname");
        assertEquals(age, address.getAge(), "Invalid age");

        // Validate AddressDetails
        AddressDetails details = address.getAddress();
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

    @Test
    public void testToXml(){

        AddressDetails xmlAdressDetails = new AddressDetails("test", "testt", "testtt");
        String outputFilePath = "src/test/resources/xml_adress_details.xml";
        Address testAddress = new Address();
        testAddress.setAddress(xmlAdressDetails);
        try {
            XmlUtils.toXml(testAddress, outputFilePath);
        }catch (Exception e){
            fail("Exception thrown during test: " + e.toString());
            return;
        }

    }

    @Test
    public void testFromXmlBackToXml(){
        File inputFile = new File("src/test/resources/adresse.xml");
        TypeReference<List<Address>> targetClass = new TypeReference<>() {};

        // Ausführung
        List<Address> result;
        try {
            result = XmlUtils.fromXml(inputFile, targetClass);
        } catch (IOException e) {
            fail("Exception thrown during test: " + e.toString());
            return;
        }

        String outputFilePath = "src/test/resources/back_adresse.xml";
        try {
            XmlUtils.toXml(result, outputFilePath);
        }catch (Exception e){
            fail("Exception thrown during test: " + e.toString());
            return;
        }


    }

    @Test
    void testArray(){
        File testFile = new File("src/test/resources/xmladresse.xml");
        boolean result = false;
        try {
            result = XmlUtils.isArray(testFile);
        }catch (Exception e){
            fail("Fail" + e);
        }

        assertEquals(true, result);
    }

}
