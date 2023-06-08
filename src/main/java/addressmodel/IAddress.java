package addressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public interface IAddress {

    //getter Methoden
    String getStreet();

    String getCity();

    String getPostcode();

    //setter Methoden
    void setStreet(String street);

    void setCity(String city);

    void setPostcode(String postcode);
}
