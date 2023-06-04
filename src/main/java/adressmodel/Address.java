package adressmodel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonPropertyOrder({ "FirstName", "Surname", "Age", "Address", "Phone", "Email" })
@JacksonXmlRootElement(localName = "root")
public class Address {
    @JsonProperty("FirstName")
    @JacksonXmlProperty(localName = "FirstName")
    private String firstName;
    @JsonProperty("Surname")
    @JacksonXmlProperty(localName = "Surname")
    private String surname;

    @JsonProperty("Age")
    @JacksonXmlProperty(localName = "Age")
    private int age;
    @JsonProperty("Address")
    @JacksonXmlProperty(localName = "Address")
    private AddressDetails address;
    @JsonProperty("Phone")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Phone")
    private List<Phone> phone;
    @JsonProperty("Email")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Email")
    private List<Email> email;

    public Address(String FirstName, String Surname, int Age, AddressDetails Address, List<Phone> Phone, List<Email> Email){
        this.firstName = FirstName;
        this.surname = Surname;
        this.age = Age;
        this.address = Address;
        this.phone = Phone;
        this.email = Email;
    }

    public Address(){

    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public List<Email> getEmail() {
        return email;
    }

    public AddressDetails getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setAddress(AddressDetails address) {
        this.address = address;
    }
}