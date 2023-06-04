package filter.formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.File;

import adressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.FormatUtils;
import utils.JsonUtils;
import utils.XmlUtils;
import adressmodel.AddressList;
import adressmodel.Address;
import adressmodel.Phone;
import adressmodel.Email;
import java.util.List;
import java.util.ArrayList;

// Die Klasse EncryptFormatter verschlüsselt die Daten in einer Datei
public class EncryptFormatter {
    // AES-Algorithmus wird für die Verschlüsselung verwendet
    private static final String ALGORITHM = "AES";
    // Schlüssel für die AES-Verschlüsselung
    private static final byte[] KEY = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);

    // Die Methode encryptFile verschlüsselt die Daten in der angegebenen Datei
    public void encryptFile(String filePath) throws Exception {
        File file = new File(filePath);

        // Erkennt das Dateiformat
        String fileFormat = FormatUtils.detectFileType(filePath);

        // Führt verschiedene Aktionen basierend auf dem Dateiformat aus
        switch (fileFormat.toLowerCase()) {
            // Wenn das Dateiformat JSON ist
            case "json":
                // Bestimmt den Typ der Daten in der Datei
                TypeReference<?> typeReference = JsonUtils.determineListType(new File(filePath));
                handleJsonFile(filePath, typeReference);
                break;
            /*case "xml":
                personList = XmlUtils.fromXml(file, new TypeReference<List<Person>>(){});
                encryptAddresses(personList);
                XmlUtils.toXml(personList, filePath);
                break;*/
            // Wenn das Dateiformat nicht unterstützt wird
            default:
                throw new Exception("Unsupported file format: " + fileFormat);
        }
    }

    private void handleJsonFile(String filePath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Person>>) typeReference);
            encryptAddresses(personList);
            JsonUtils.toJson(personList, filePath);
        }
        else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Email>>) typeReference);
            encryptEmailDetails(emailList);
            JsonUtils.toJson(emailList, filePath);
        }
        else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            encryptPhoneDetails(phoneList);
            JsonUtils.toJson(phoneList, filePath);
        }
    }


    // Die Methode encryptAddresses verschlüsselt eine Liste von Personen
    private void encryptAddresses(List<Person> personList) throws Exception {
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
            encryptAddressDetails(person.getAddress(), cipher);
            encryptPhoneDetails(person.getPhone());
            encryptEmailDetails(person.getEmail());
        }
    }

    // Die Methode encryptPhoneDetails verschlüsselt eine Liste von Telefonnummern
    private void encryptPhoneDetails(List<Phone> phones) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Phone phone : phones) {
            phone.setNumber(encryptString(phone.getNumber(), cipher));
            phone.setType(encryptString(phone.getType(), cipher));
        }
    }

    // Die Methode encryptEmailDetails verschlüsselt eine Liste von Emails
    private void encryptEmailDetails(List<Email> emails) throws Exception {
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
    private void encryptAddressDetails(Address address, Cipher cipher) throws Exception {
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
