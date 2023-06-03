package adressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

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

    public String getStreet() {
        return street;
    }

    public String getCity(){
        return city;
    }

    public String getPostcode() {
        return postcode;
    }
}