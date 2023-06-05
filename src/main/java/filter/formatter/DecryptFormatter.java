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
import adressmodel.Address;
import adressmodel.Phone;
import adressmodel.Email;
import java.util.List;
import java.util.ArrayList;

public class DecryptFormatter {
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);

    public void decryptFile(String filePath) throws Exception {
        File file = new File(filePath);

        String fileFormat = FormatUtils.detectFileType(filePath);

        switch (fileFormat.toLowerCase()) {
            case "json":
                TypeReference<?> typeReference = JsonUtils.determineListType(new File(filePath));
                handleJsonFile(filePath, typeReference);
                break;
            case "xml":
                TypeReference<?> xmlTypeReference = XmlUtils.determineListType(new File(filePath));
                handleXmlFile(filePath, xmlTypeReference);
                break;
            default:
                throw new Exception("Unsupported file format: " + fileFormat);
        }
    }

    private void handleJsonFile(String filePath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Person>>) typeReference);
            decryptAddresses(personList);
            JsonUtils.toJson(personList, filePath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Email>>) typeReference);
            decryptEmailDetails(emailList);
            JsonUtils.toJson(emailList, filePath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = JsonUtils.fromJson(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            decryptPhoneDetails(phoneList);
            JsonUtils.toJson(phoneList, filePath);
        }
    }

    private void handleXmlFile(String filePath, TypeReference<?> typeReference) throws Exception {
        if (typeReference.getType().equals(new TypeReference<List<Person>>(){}.getType())) {
            List<Person> personList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Person>>) typeReference);
            decryptAddresses(personList);
            XmlUtils.toXml(personList, filePath);
        } else if (typeReference.getType().equals(new TypeReference<List<Email>>(){}.getType())) {
            List<Email> emailList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Email>>) typeReference);
            decryptEmailDetails(emailList);
            XmlUtils.toXml(emailList, filePath);
        } else if (typeReference.getType().equals(new TypeReference<List<Phone>>(){}.getType())) {
            List<Phone> phoneList = XmlUtils.fromXml(new File(filePath), (TypeReference<List<Phone>>) typeReference);
            decryptPhoneDetails(phoneList);
            XmlUtils.toXml(phoneList, filePath);
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
