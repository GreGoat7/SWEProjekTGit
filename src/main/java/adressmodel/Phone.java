package adressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Phone")
public class Phone {
    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    private String type;
    @JsonProperty("number")
    @JacksonXmlProperty(localName = "number")
    private String number;

    public Phone(String type, String number){
        this.type = type;
        this.number = number;
    }

    public Phone(){

    }



}