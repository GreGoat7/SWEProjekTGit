package addressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonPropertyOrder({ "Street", "City", "Postcode"})
@JacksonXmlRootElement(localName = "Person")
public class Address {
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

    public Address() {

    }

    //getter Methoden
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    public String getCity() {
        return city;
    }

    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    public String getPostcode() {
        return postcode;
    }


    //setter Methoden
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
