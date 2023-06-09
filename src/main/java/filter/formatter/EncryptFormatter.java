package filter.formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.File;

import constants.Constants;
import filter.Filter;
import addressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.*;
import addressmodel.Address;
import addressmodel.Phone;
import addressmodel.Email;
import java.util.List;
import java.util.ArrayList;


// Diese klasse verschlüsselt die Daten einer Eingabedatei
public class EncryptFormatter implements Filter {
    // Zuweisung der Konstanten
    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;
    YamlUtils yamlUtils = Constants.YAMLUTILS;
    FormatUtils formatUtils = Constants.FORMATUTILS;
    String ALGORITHM = Constants.ALGORITHM;
    byte[] KEY = Constants.KEY;

    // generiert den Ausgabepfad und gibt ihn zurück
    // @param   inputFilePath   Pfad zur Eingabedatei
    private String getOutputFilePath(String inputFilePath) {
        int lastDotIndex = inputFilePath.lastIndexOf(".");
        String baseName = inputFilePath.substring(0, lastDotIndex);
        String extension = inputFilePath.substring(lastDotIndex);
        return baseName + ".enc" + extension;
    }


    @Override
    public String process(String inputFilePath) throws Exception {

        String outputFilePath = getOutputFilePath(inputFilePath);

        // Erkennt das Dateiformat
        String fileFormat = formatUtils.detectFileType(inputFilePath).toString().toLowerCase();


        switch (fileFormat) {
            case "json" -> {
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> typeReference = jsonUtils.determineListType(new File(inputFilePath));
                handleFile(inputFilePath, outputFilePath, typeReference, jsonUtils);
            }
            case "xml" -> {
                TypeReference<?> xmlTypeReference = xmlUtils.determineListType(new File(inputFilePath));
                handleFile(inputFilePath, outputFilePath, xmlTypeReference, xmlUtils);
            }
            case "yaml" -> {
                TypeReference<?> yamlTypeReference = yamlUtils.determineListType(new File(inputFilePath));
                handleFile(inputFilePath, outputFilePath, yamlTypeReference, yamlUtils);
            }

            // Wenn das Dateiformat nicht unterstützt wird
            default -> throw new Exception("Unsupported file format: " + fileFormat);
        }

        // Gibt den Pfad zur verschlüsselten Datei zurück
        return outputFilePath;
    }

    // verarbeitet eine Eingabedatei, indem sie den Inhalt der Datei liest,
    // die Daten verschlüsselt und die verschlüsselten Daten in eine Ausgabedatei schreibt.
    // @param   inputFilePath    der Pfad zur Eingabedatei
    // @param   outputFilepath   der Pfad zur Ausgabedatei
    // @param   typeReference    die Art der Daten, die in der Datei enthalten sind
    // @param   iUtils           die Hilfsklasse, die zum Lesen der Eingabedatei und Schreiben der Ausgabedatei verwendet wird
    private void handleFile(String inputFilePath, String outputFilepath, TypeReference<?> typeReference, IUtils iUtils) throws Exception {
        // Erstellt eine Datei aus dem Eingabepfad
        File inputFile = new File(inputFilePath);

        // Überprüft, ob die Daten in der Datei eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            // Wandelt den Inhalt der Datei in eine Liste von Personen um
            List<Person> personList = iUtils.toJava(inputFile, (TypeReference<List<Person>>) typeReference);
            // Verschlüsselt die Daten in der Liste
            encryptPersonList(personList);
            // Schreibt die verschlüsselten Daten in die Ausgabedatei
            iUtils.fromJava(personList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = iUtils.toJava(inputFile, (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            iUtils.fromJava(emailList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = iUtils.toJava(inputFile, (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            iUtils.fromJava(phoneList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Address>>(){}.getType())) {
            List<Address> addressList = iUtils.toJava(inputFile, (TypeReference<List<Address>>) typeReference);
            encryptAddresses(addressList);
            iUtils.fromJava(addressList, outputFilepath);
        }
    }


    // verschlüsselt eine Liste von Personen
    // @param   personList   eine Liste von Personen (Eingabeliste)
    private void encryptPersonList(List<Person> personList) throws Exception {
        // Erstellt den Schlüssel für die Verschlüsselung
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        // Erstellt den Verschlüsselungsmechanismus
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);


        for (Person person : personList) {
            // Verschlüsselt die einzelnen Daten der Person
            person.setFirstName(encryptString(person.getFirstName(), cipher));
            person.setSurname(encryptString(person.getSurname(), cipher));
            person.setAge(encryptString(person.getAge(), cipher));
            encryptAddressesForPerson(person.getAddress(), cipher);
            encryptPhones(person.getPhone());
            encryptEmails(person.getEmail());
        }
    }

    private void encryptAddressesForPerson(Address address, Cipher cipher) throws Exception {
        address.setStreet(encryptString(address.getStreet(), cipher));
        address.setCity(encryptString(address.getCity(), cipher));
        address.setPostcode(encryptString(address.getPostcode(), cipher));
    }

    // verschlüsselt eine Liste von Telefonnummern
    // @param   phones   eine Liste von Telefonnummern (Eingabeliste)
    private void encryptPhones(List<Phone> phones) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Phone phone : phones) {
            phone.setNumber(encryptString(phone.getNumber(), cipher));
            phone.setType(encryptString(phone.getType(), cipher));
        }
    }

    // verschlüsselt eine Liste von Emails
    // @param   emails   eine Liste von Emails (Eingabeliste)
    private void encryptEmails(List<Email> emails) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Email email : emails) {
            List<String> encryptedEmails = new ArrayList<>();
            for (String emailAddress : email.getEmailAddress()) {
                encryptedEmails.add(encryptString(emailAddress, cipher));
            }
            email.setEmailAddress(encryptedEmails);
            email.setType(encryptString(email.getType(), cipher));
        }
    }

    // verschlüsselt eine Adresse
    // @param   address   eine Adresse einer Person (Eingabeliste)
    private void encryptAddresses(List<Address> addresses) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Address address : addresses) {
            address.setStreet(encryptString(address.getStreet(), cipher));
            address.setCity(encryptString(address.getCity(), cipher));
            address.setPostcode(encryptString(address.getPostcode(), cipher));
        }
    }

    // verschlüsselt einen String
    // @param   value   der zu verschlüsselnde String (Eingabeliste)
    private String encryptString(String value, Cipher cipher) throws Exception {
        // Verschlüsselt den Wert
        byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        // Kodiert den verschlüsselten Wert in Base64 und gibt ihn zurück
        return Base64.getEncoder().encodeToString(encrypted);
    }
}