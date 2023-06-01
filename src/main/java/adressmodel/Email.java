package adressmodel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;

public class Email {
    @JsonProperty("type")
    private String type;
    @JsonProperty("address")
    private List<String> emailAddress;

    public Email(String type, List<String> emailAddress){
        this.type = type;
        this.emailAddress = emailAddress;
    }

    public Email(){

    }

    public List<String> getEmailAddress() {
        return emailAddress;
    }

    public String getType() {
        return type;
    }
}