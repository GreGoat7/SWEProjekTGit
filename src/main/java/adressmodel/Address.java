package adressmodel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Address {
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("Surname")
    private String surname;
    @JsonProperty("Age")
    private int age;
    @JsonProperty("Address")
    private AddressDetails address;
    @JsonProperty("Phone")
    private List<Phone> phones;
    @JsonProperty("Email")
    private List<Email> emails;

    public Address(String FirstName, String Surname, int Age, AddressDetails Address, List<Phone> Phone, List<Email> Email){
        this.firstName = FirstName;
        this.surname = Surname;
        this.age = Age;
        this.address = Address;
        this.phones = Phone;
        this.emails = Email;
    }

    public Address(){

    }

}