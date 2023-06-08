package addressmodel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({ "FirstName", "Surname", "Age", "Person", "Phone", "Email" })
@JacksonXmlRootElement(localName = "root")
public class Person {
    @JsonProperty("FirstName")
    @JacksonXmlProperty(localName = "FirstName")
    private String firstName;
    @JsonProperty("Surname")
    @JacksonXmlProperty(localName = "Surname")
    private String surname;

    @JsonProperty("Age")
    @JacksonXmlProperty(localName = "Age")
    private String age;
    @JsonProperty("Address")
    @JacksonXmlProperty(localName = "Address")
    private Address address;
    @JsonProperty("Phone")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Phone")
    private List<Phone> phone;
    @JsonProperty("Email")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Email")
    private List<Email> email;

    // Constructor
    public Person(String FirstName, String Surname, String Age, Address Address, List<Phone> Phone, List<Email> Email){
        this.firstName = FirstName;
        this.surname = Surname;
        this.age = Age;
        this.address = Address;
        this.phone = Phone;
        this.email = Email;
    }

    public Person(){

    }

    //getter Methoden
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public List<Email> getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    //setter Methoden
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(age, person.age) &&
                Objects.equals(address, person.address) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surname, age, address, phone, email);
    }

}