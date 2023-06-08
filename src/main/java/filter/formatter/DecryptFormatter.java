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

import java.util.List;
import java.util.ArrayList;

public class DecryptFormatter implements Filter {
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
        return baseName + ".dec" + extension;
    }

    // Verwende die Hilfsmethode, um den Pfad der Ausgabedatei zu erzeugen und die Daten dorthin zu schreiben
    @Override
    public String process(String inputFilePath) throws Exception {
        String outputFilePath = getOutputFilePath(inputFilePath);
        File file = new File(inputFilePath);

        String fileFormat = FormatUtils.detectFileType(inputFilePath);

        switch (fileFormat.toLowerCase()) {
            case "json" -> {
                TypeReference<?> typeReference = JsonUtils.determineListType(new File(inputFilePath));
                handleJsonFile(inputFilePath, outputFilePath, typeReference);
            }
            case "xml" -> {
                TypeReference<?> xmlTypeReference = XmlUtils.determineListType(new File(inputFilePath));
                handleXmlFile(inputFilePath, outputFilePath, xmlTypeReference);
            }
            case "yaml" -> {
                TypeReference<?> yamlTypeReference = YamlUtils.determineListType(new File(inputFilePath));
                handleYamlFile(inputFilePath, outputFilePath, yamlTypeReference);
            }
            default -> throw new Exception("Unsupported file format: " + fileFormat);
        }

        return outputFilePath;
    }

    private void handleJsonFile(String filePath, String outputFilepath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Person>>) typeReference);
            decryptAddresses(personList);
            jsonUtils.fromJava(personList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Email>>) typeReference);
            decryptEmailDetails(emailList);
            jsonUtils.fromJava(emailList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = jsonUtils.toJava(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            decryptPhoneDetails(phoneList);
            jsonUtils.fromJava(phoneList, outputFilepath);
        }
    }

    private void handleXmlFile(String filePath, String outputFilepath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Person>>) typeReference);
            decryptAddresses(personList);
            xmlUtils.fromJava(personList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Email>>) typeReference);
            decryptEmailDetails(emailList);
            xmlUtils.fromJava(emailList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            decryptPhoneDetails(phoneList);
            xmlUtils.fromJava(phoneList, outputFilepath);
        }
    }

    private void handleYamlFile(String filePath, String outputFilepath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Person>>) typeReference);
            decryptAddresses(personList);
            yamlUtils.fromJava(personList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Email>>) typeReference);
            decryptEmailDetails(emailList);
            yamlUtils.fromJava(emailList, outputFilepath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = yamlUtils.toJava(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            decryptPhoneDetails(phoneList);
            yamlUtils.fromJava(phoneList, outputFilepath);
        }
    }

    private void decryptAddresses(List<Person> personList) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Person person : personList) {
            person.setFirstName(decryptString(person.getFirstName(), cipher));
            person.setSurname(decryptString(person.getSurname(), cipher));
            person.setAge(decryptString(person.getAge(), cipher));
            decryptAddressDetails(person.getAddress(), cipher);
            decryptPhoneDetails(person.getPhone());
            decryptEmailDetails(person.getEmail());
        }
    }

    private void decryptPhoneDetails(List<Phone> phones) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        for (Phone phone : phones) {
            phone.setNumber(decryptString(phone.getNumber(), cipher));
            phone.setType(decryptString(phone.getType(), cipher));
        }
    }

    private void decryptEmailDetails(List<Email> emails) throws Exception {
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

    private void decryptAddressDetails(Address address, Cipher cipher) throws Exception {
        address.setStreet(decryptString(address.getStreet(), cipher));
        address.setCity(decryptString(address.getCity(), cipher));
        address.setPostcode(decryptString(address.getPostcode(), cipher));
    }

    private String decryptString(String value, Cipher cipher) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(value);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
