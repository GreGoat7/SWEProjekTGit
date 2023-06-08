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
public class Person implements IPerson {
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
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public List<Email> getEmail() {
        return email;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public List<Phone> getPhone() {
        return phone;
    }

    //setter Methoden
    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    // Überschreibt die equals-Methode, um die Gleichheit von Person-Objekten zu bestimmen.

    // @param o Das Objekt, mit dem dieses Person-Objekt verglichen wird.
    // @return  Gibt 'true' zurück, wenn das gegebene Objekt das gleiche ist wie dieses Objekt,
    //          oder wenn das gegebene Objekt eine Person ist und der Vorname,
    //          Nachname, Alter, Adresse, Telefon und E-Mail mit denen dieses Objekts übereinstimmen.
    //          Andernfalls wird 'false' zurückgegeben.
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