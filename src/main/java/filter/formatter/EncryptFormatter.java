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

public class EncryptFormatter {
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);

    public void encryptFile(String filePath) throws Exception {
        File file = new File(filePath);

        // Determine the file format
        String fileFormat = FormatUtils.detectFileType(filePath);

        switch (fileFormat.toLowerCase()) {
            case "json":
                List<Person> personList = JsonUtils.fromJson(file, new TypeReference<List<Person>>(){});
                encryptAddresses(personList);
                JsonUtils.toJson(personList, filePath);
                break;
            case "xml":
                personList = XmlUtils.fromXml(file, new TypeReference<List<Person>>(){});
                encryptAddresses(personList);
                XmlUtils.toXml(personList, filePath);
                break;
            //yaml fehlt noch
            default:
                throw new Exception("Unsupported file format: " + fileFormat);
        }
    }

    private void encryptAddresses(List<Person> personList) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Person person : personList) {
            person.setFirstName(encryptString(person.getFirstName(), cipher));
            person.setSurname(encryptString(person.getSurname(), cipher));
            person.setAge(encryptString(person.getAge(), cipher));
            encryptAddressDetails(person.getAddress(), cipher);
            encryptPhoneDetails(person.getPhone(), cipher);
            encryptEmailDetails(person.getEmail(), cipher);
        }
    }

    private void encryptPhoneDetails(List<Phone> phones, Cipher cipher) throws Exception {
        for (Phone phone : phones) {
            phone.setNumber(encryptString(phone.getNumber(), cipher));
            phone.setType(encryptString(phone.getType(), cipher));
        }
    }

    private void encryptEmailDetails(List<Email> emails, Cipher cipher) throws Exception {
        for (Email email : emails) {
            List<String> encryptedEmails = new ArrayList<>();
            for (String emailAddress : email.getEmailAddress()) {
                encryptedEmails.add(encryptString(emailAddress, cipher));
            }
            email.setEmailAddress(encryptedEmails);
            email.setType(encryptString(email.getType(), cipher));
        }
    }

    private void encryptAddressDetails(Address address, Cipher cipher) throws Exception {
        address.setStreet(encryptString(address.getStreet(), cipher));
        address.setCity(encryptString(address.getCity(), cipher));
        address.setPostcode(encryptString(address.getPostcode(), cipher));
    }



    private String encryptString(String value, Cipher cipher) throws Exception {
        byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
