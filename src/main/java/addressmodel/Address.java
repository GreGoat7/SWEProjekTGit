package addressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;

@JsonPropertyOrder({ "Street", "City", "Postcode"})
@JacksonXmlRootElement(localName = "Person")
public class Address implements IAddress {
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    private String street;
    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    private String city;
    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    private String postcode;

    // Constructor
    public Address(String street, String city, String postcode) {
        this.street = street;
        this.city = city;
        this.postcode = postcode;
    }

    //Default-Constructor
    public Address() {

    }

    //getter Methoden
    @Override
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    public String getStreet() {
        return street;
    }

    @Override
    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    public String getCity() {
        return city;
    }

    @Override
    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    public String getPostcode() {
        return postcode;
    }


    //setter Methoden
    @Override
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // Diese Methode wird überschrieben, um die Gleichheit von Address-Objekten zu bestimmen.
    // @param o Das Objekt, mit dem dieses Address-Objekt verglichen wird.
    // @return  Gibt 'true' zurück, wenn das gegebene Objekt das gleiche ist wie dieses Objekt,
    //          oder wenn das gegebene Objekt eine Adresse ist und die Straße, die Stadt und
    //          die Postleitzahl mit denen dieses Objekts übereinstimmen.
    //          Andernfalls wird 'false' zurückgegeben.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postcode, address.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postcode);
    }

}
