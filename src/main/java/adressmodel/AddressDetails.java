package adressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDetails {
    @JsonProperty("Street")
    private String street;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Postcode")
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