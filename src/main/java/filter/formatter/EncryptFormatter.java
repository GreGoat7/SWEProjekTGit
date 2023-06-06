package filter.formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.File;
import filter.Filter;
import adressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.FormatUtils;
import utils.JsonUtils;
import utils.XmlUtils;
import adressmodel.Address;
import adressmodel.Phone;
import adressmodel.Email;
import java.util.List;
import java.util.ArrayList;

//soll neue encrypted datei erstellt werden oder alte datei ersetzt durcvh neue encrypted datei

// Die Klasse EncryptFormatter verschlüsselt die Daten in einer Datei
public class EncryptFormatter implements Filter {
    // AES-Algorithmus wird für die Verschlüsselung verwendet
    private static final String ALGORITHM = "AES";
    // Schlüssel für die AES-Verschlüsselung
    private static final byte[] KEY = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);

    // Die Methode encryptFile verschlüsselt die Daten in der angegebenen Datei
    @Override
    public String process(String filePath) throws Exception {
        File file = new File(filePath);

        // Erkennt das Dateiformat
        String fileFormat = FormatUtils.detectFileType(filePath);


        String outputFilePath = null;

        switch (fileFormat.toLowerCase()) {
            // Wenn das Dateiformat JSON ist
            case "json" -> {
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> typeReference = JsonUtils.determineListType(new File(filePath));
                handleJsonFile(filePath, typeReference);
                //speichert den outputFilePath
                outputFilePath = filePath.replace(".json", ".enc.json");
            }
            case "xml" -> {
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> xmlTypeReference = XmlUtils.determineListType(new File(filePath));
                handleXmlFile(filePath, xmlTypeReference);
                outputFilePath = filePath.replace(".xml", ".enc.xml");
            }
            // Wenn das Dateiformat nicht unterstützt wird
            default -> throw new Exception("Unsupported file format: " + fileFormat);
        }

        // Gibt den Pfad zur verschlüsselten Datei zurück
        return outputFilePath;
    }

    //Die Verschlüsselungs-Hilfsmethode handleJsonFile wird aufgerufen, wenn es sich um eine JsonFile handelt
    private void handleJsonFile(String filePath, TypeReference<?> typeReference) throws Exception {

        // Wenn die Daten eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptPersonList(personList);
            JsonUtils.toJson(personList, filePath.replace(".json", ".enc.json"));
        }
        //Wenn die Daten eine Liste von Emails sind
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            JsonUtils.toJson(emailList, filePath.replace(".json", ".enc.json"));
        }
        //Wenn die Daten eine Liste von Telefonnummern sind
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            JsonUtils.toJson(phoneList, filePath.replace(".json", ".enc.json"));
        }
    }

    //Die Verschlüsselungs-Hilfsmethode handleXmlFile wird aufgerufen, wenn es sich um eine XmlFile handelt
    private void handleXmlFile(String filePath, TypeReference<?> typeReference) throws Exception {
        // Wenn die Daten eine Liste von Personen sind
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptPersonList(personList);
            XmlUtils.toXml(personList, filePath.replace(".xml", ".enc.xml"));
        }
        //Wenn die Daten eine Liste von Emails sind
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmails(emailList);
            XmlUtils.toXml(emailList, filePath.replace(".xml", ".enc.xml"));
        }
        //Wenn die Daten eine Liste von Telefonnummern sind
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhones(phoneList);
            XmlUtils.toXml(phoneList, filePath.replace(".xml", ".enc.xml"));

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
