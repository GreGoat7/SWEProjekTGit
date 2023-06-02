package adressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {
    @JsonProperty("type")
    private String type;
    @JsonProperty("number")
    private String number;

    public Phone(String type, String number){
        this.type = type;
        this.number = number;
    }

    public Phone(){

    }



}