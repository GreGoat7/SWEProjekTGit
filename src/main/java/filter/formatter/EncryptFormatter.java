package filter.formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
import adressmodel.Address;
import adressmodel.AddressDetails;
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
        String fileFormat = determineFileFormat(filePath);

        switch (fileFormat.toLowerCase()) {
            case "json":
                List<Address> addresses = JsonUtils.fromJson(file, new TypeReference<List<Address>>(){});
                encryptAddresses(addresses);
                JsonUtils.toJson(addresses, filePath);
                break;
            case "xml":
                addresses = XmlUtils.fromXml(file, new TypeReference<List<Address>>(){});
                encryptAddresses(addresses);
                XmlUtils.toXml(addresses, filePath);
                break;
            //yaml fehlt noch
            default:
                throw new Exception("Unsupported file format: " + fileFormat);
        }
    }

    private void encryptAddresses(List<Address> addresses) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        for (Address address : addresses) {
            address.setFirstName(encryptString(address.getFirstName(), cipher));
            address.setSurname(encryptString(address.getSurname(), cipher));
            //address.setAge(encryptString(Integer.toString(address.getAge()), cipher));
            encryptAddressDetails(address.getAddress(), cipher);
            encryptPhoneDetails(address.getPhone(), cipher);
            encryptEmailDetails(address.getEmail(), cipher);
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

    private void encryptAddressDetails(AddressDetails addressDetails, Cipher cipher) throws Exception {
        addressDetails.setStreet(encryptString(addressDetails.getStreet(), cipher));
        addressDetails.setCity(encryptString(addressDetails.getCity(), cipher));
        addressDetails.setPostcode(encryptString(addressDetails.getPostcode(), cipher));
    }



    private String encryptString(String value, Cipher cipher) throws Exception {
        byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private String determineFileFormat(String filePath) {
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            return filePath.substring(i + 1);
        } else {
            return "";
        }
    }
}
