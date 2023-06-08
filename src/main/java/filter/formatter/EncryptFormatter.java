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


// Die Klasse EncryptFormatter verschlüsselt die Daten in einer Datei
public class EncryptFormatter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;
    YamlUtils yamlUtils = Constants.YAMLUTILS;

    String ALGORITHM = Constants.ALGORITHM;
    byte[] KEY = Constants.KEY;

    // Hilfsmethode zum Generieren des Ausgabepfads
    private String getOutputFilePath(String inputFilePath) {
        int lastDotIndex = inputFilePath.lastIndexOf(".");
        String baseName = inputFilePath.substring(0, lastDotIndex);
        String extension = inputFilePath.substring(lastDotIndex);
        return baseName + ".enc" + extension;
    }


    // Die Methode process verschlüsselt die Daten in der angegebenen Datei
    @Override
    public String process(String inputFilePath) throws Exception {

        String outputFilePath = getOutputFilePath(inputFilePath);

        // Erkennt das Dateiformat
        String fileFormat = FormatUtils.detectFileType(inputFilePath).toString().toLowerCase();


        switch (fileFormat.toLowerCase()) {
            case "json" -> {
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> typeReference = jsonUtils.determineListType(new File(inputFilePath));
                handleJsonFile(inputFilePath, outputFilePath, typeReference);
            }
            case "xml" -> {
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> xmlTypeReference = xmlUtils.determineListType(new File(inputFilePath));
                handleXmlFile(inputFilePath, outputFilePath, xmlTypeReference);
            }
            case "yaml" -> {
                TypeReference<?> yamlTypeReference = yamlUtils.determineListType(new File(inputFilePath));
                handleYamlFile(inputFilePath, outputFilePath, yamlTypeReference);
            }

            // Wenn das Dateiformat nicht unterstützt wird
            default -> throw new Exception("Unsupported file format: " + fileFormat);
        }

        // Gibt den Pfad zur verschlüsselten Datei zurück
        return outputFilePath;
    }

    //Die Verschlüsselungs-Hilfsmethode handleJsonFile wird aufgerufen, wenn es sich um eine JsonFile handelt
    private void handleJsonFile(String filePath, String outputFilePath, TypeReference<?> typeReference) throws Exception {

        // Wenn die Daten eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptPersonList(personList);
            jsonUtils.fromJava(personList, outputFilePath);
        }
        //Wenn die Daten eine Liste von Emails sind
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            jsonUtils.fromJava(emailList, outputFilePath);
        }
        //Wenn die Daten eine Liste von Telefonnummern sind
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            jsonUtils.fromJava(phoneList, outputFilePath);
        }
    }

    //Die Verschlüsselungs-Hilfsmethode handleXmlFile wird aufgerufen, wenn es sich um eine XmlFile handelt
    private void handleXmlFile(String filePath, String outputFilePath, TypeReference<?> typeReference) throws Exception {
        // Wenn die Daten eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = xmlUtils.toJava(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptPersonList(personList);
            xmlUtils.fromJava(personList, outputFilePath );
        }
        //Wenn die Daten eine Liste von Emails sind
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = xmlUtils.toJava(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            xmlUtils.fromJava(emailList, outputFilePath);
        }
        //Wenn die Daten eine Liste von Telefonnummern sind
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = xmlUtils.toJava(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            xmlUtils.fromJava(phoneList, outputFilePath);

        }
    }

    //Die Verschlüsselungs-Hilfsmethode handleYamlFile wird aufgerufen, wenn es sich um eine YamlFile handelt
    private void handleYamlFile(String filePath, String outputFilePath, TypeReference<?> typeReference) throws Exception {
        // Wenn die Daten eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptPersonList(personList);
            yamlUtils.fromJava(personList, outputFilePath);
        }
        //Wenn die Daten eine Liste von Emails sind
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            yamlUtils.fromJava(emailList, outputFilePath);
        }
        //Wenn die Daten eine Liste von Telefonnummern sind
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            yamlUtils.fromJava(phoneList, outputFilePath);
        }
    }


    // Die Methode encryptAddresses verschlüsselt eine Liste von Personen
    private void encryptPersonList(List<Person> personList) throws Exception {
        // Erstellt den Schlüssel für die Verschlüsselung
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        // Erstellt den Verschlüsselungsmechanismus
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Geht durch jede Person in der Liste
        for (Person person : personList) {
            // Verschlüsselt die einzelnen Daten der Person
            person.setFirstName(encryptString(person.getFirstName(), cipher));
            person.setSurname(encryptString(person.getSurname(), cipher));
            person.setAge(encryptString(person.getAge(), cipher));
            encryptAddresses(person.getAddress(), cipher);
            encryptPhones(person.getPhone());
            encryptEmails(person.getEmail());
        }
    }

    // Die Methode encryptPhoneDetails verschlüsselt eine Liste von Telefonnummern
    private void encryptPhones(List<Phone> phones) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Phone phone : phones) {
            phone.setNumber(encryptString(phone.getNumber(), cipher));
            phone.setType(encryptString(phone.getType(), cipher));
        }
    }

    // Die Methode encryptEmailDetails verschlüsselt eine Liste von Emails
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

    // Die Methode encryptAddressDetails verschlüsselt eine Adresse
    private void encryptAddresses(Address address, Cipher cipher) throws Exception {
        address.setStreet(encryptString(address.getStreet(), cipher));
        address.setCity(encryptString(address.getCity(), cipher));
        address.setPostcode(encryptString(address.getPostcode(), cipher));
    }

    // Die Methode encryptString verschlüsselt einen String
    private String encryptString(String value, Cipher cipher) throws Exception {
        // Verschlüsselt den Wert
        byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        // Kodiert den verschlüsselten Wert in Base64 und gibt ihn zurück
        return Base64.getEncoder().encodeToString(encrypted);
    }
}