package adressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonPropertyOrder({ "Street", "City", "Postcode"})
@JacksonXmlRootElement(localName = "Address")
public class AddressDetails {
    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    private String street;
    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    private String city;
    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    private String postcode;

    public AddressDetails(String street, String city, String postcode){
        this.street = street;
        this.city = city;
        this.postcode = postcode;
    }

    public AddressDetails(){

    }

    @JsonProperty("Street")
    @JacksonXmlProperty(localName = "Street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("City")
    @JacksonXmlProperty(localName = "City")
    public String getCity(){
        return city;
    }

    @JsonProperty("Postcode")
    @JacksonXmlProperty(localName = "Postcode")
    public String getPostcode() {
        return postcode;
    }
}