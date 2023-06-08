package filter.formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.File;
import addressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import constants.Constants;
import filter.Filter;
import utils.JsonUtils;
import utils.XmlUtils;
import addressmodel.Address;
import addressmodel.Phone;
import addressmodel.Email;
import utils.YamlUtils;
import utils.FormatUtils;
import utils.IUtils;

import java.util.List;
import java.util.ArrayList;

public class DecryptFormatter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;
    YamlUtils yamlUtils = Constants.YAMLUTILS;
    FormatUtils formatUtils = Constants.FORMATUTILS;
    String ALGORITHM = Constants.ALGORITHM;
    byte[] KEY = Constants.KEY;

    // generiert des Ausgabepfads
    // @param   inputFilePath  Pfad zur Eingabedatei
    private String getOutputFilePath(String inputFilePath) {
        int lastDotIndex = inputFilePath.lastIndexOf(".");
        String baseName = inputFilePath.substring(0, lastDotIndex);
        String extension = inputFilePath.substring(lastDotIndex);
        return baseName + ".dec" + extension;
    }

    @Override
    public String process(String inputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        String outputFilePath = getOutputFilePath(inputFilePath);

        String fileFormat = formatUtils.detectFileType(inputFilePath).toString().toLowerCase();

        switch (fileFormat) {
            case "json" -> {
                TypeReference<?> typeReference = jsonUtils.determineListType(inputFile);
                handleFile(inputFilePath, outputFilePath, typeReference, jsonUtils);
            }
            case "xml" -> {
                TypeReference<?> xmlTypeReference = xmlUtils.determineListType(inputFile);
                handleFile(inputFilePath, outputFilePath, xmlTypeReference, xmlUtils);
            }
            case "yaml" -> {
                TypeReference<?> yamlTypeReference = yamlUtils.determineListType(inputFile);
                handleFile(inputFilePath, outputFilePath, yamlTypeReference, yamlUtils);
            }
            default -> throw new Exception("Unsupported file format: " + fileFormat);
        }

        return outputFilePath;
    }


    // verarbeitet eine Eingabedatei, indem sie den Inhalt der Datei liest,
    // die Daten entschlüsselt und die entschlüsselten Daten in eine Ausgabedatei schreibt.
    // @param   inputFilePath    der Pfad zur Eingabedatei
    // @param   outputFilepath   der Pfad zur Ausgabedatei
    // @param   typeReference    die Art der Daten, die in der Datei enthalten sind
    // @param   iUtils           die Hilfsklasse, die zum Lesen der Eingabedatei und Schreiben der Ausgabedatei verwendet wird
    private void handleFile(String inputFilePath, String outputFilepath, TypeReference<?> typeReference, IUtils iUtils) throws Exception {
        File inputFile = new File(inputFilePath);

        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = iUtils.toJava(inputFile, (TypeReference<List<Person>>) typeReference);
            decryptPersonList(personList);
            iUtils.fromJava(personList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = iUtils.toJava(inputFile, (TypeReference<List<Email>>) typeReference);
            decryptEmails(emailList);
            iUtils.fromJava(emailList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = iUtils.toJava(inputFile, (TypeReference<List<Phone>>) typeReference);
            decryptPhones(phoneList);
            iUtils.fromJava(phoneList, outputFilepath);
        }  else if (typeReference.getType().equals(new TypeReference<List<Address>>(){}.getType())) {
            List<Address> addressList = iUtils.toJava(inputFile, (TypeReference<List<Address>>) typeReference);
            decryptAddresses(addressList);
            iUtils.fromJava(addressList, outputFilepath);
        }
    }


    // entschlüsselt eine Liste von Personen
    // @param   personList   eine Liste von Personen (Eingabeliste)
    private void decryptPersonList(List<Person> personList) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Person person : personList) {
            person.setFirstName(decryptString(person.getFirstName(), cipher));
            person.setSurname(decryptString(person.getSurname(), cipher));
            person.setAge(decryptString(person.getAge(), cipher));
            decryptAddressesForPerson(person.getAddress(), cipher);
            decryptPhones(person.getPhone());
            decryptEmails(person.getEmail());
        }
    }

    // entschlüsselt eine Liste von Telefonnummern
    // @param   phones   eine Liste von Telefonnummern (Eingabeliste)
    private void decryptPhones(List<Phone> phones) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Phone phone : phones) {
            phone.setNumber(decryptString(phone.getNumber(), cipher));
            phone.setType(decryptString(phone.getType(), cipher));
        }
    }

    // entschlüsselt eine Liste von Emails
    // @param   emails   eine Liste von Emails (Eingabeliste)
    private void decryptEmails(List<Email> emails) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Email email : emails) {
            List<String> decryptedEmails = new ArrayList<>();
            for (String emailAddress : email.getEmailAddress()) {
                decryptedEmails.add(decryptString(emailAddress, cipher));
            }
            email.setEmailAddress(decryptedEmails);
            email.setType(decryptString(email.getType(), cipher));
        }
    }

    // entschlüsselt eine Adresse
    // @param   address   eine Adresse einer Person (Eingabeliste)
    private void decryptAddresses(List<Address> addresses) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Address address : addresses) {
            address.setStreet(decryptString(address.getStreet(), cipher));
            address.setCity(decryptString(address.getCity(), cipher));
            address.setPostcode(decryptString(address.getPostcode(), cipher));
        }
    }

    private void decryptAddressesForPerson(Address address, Cipher cipher) throws Exception {
        address.setStreet(decryptString(address.getStreet(), cipher));
        address.setCity(decryptString(address.getCity(), cipher));
        address.setPostcode(decryptString(address.getPostcode(), cipher));
    }

    // entschlüsselt einen String
    // @param   value   der zu entschlüsselnde String (Eingabeliste)
    private String decryptString(String value, Cipher cipher) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(value);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
