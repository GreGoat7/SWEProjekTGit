package addressmodel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({ "type", "address"})
@JacksonXmlRootElement(localName = "Email")

public class Email implements IEmail {
    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    private String type;
    @JsonProperty("address")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "address")
    private List<String> emailAddress;

    // Constructor
    public Email(String type, List<String> emailAddress) {
        this.type = type;
        this.emailAddress = emailAddress;
    }

    //Default-Constructor
    public Email() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return type.equals(email.type) &&
                emailAddress.equals(email.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, emailAddress);
    }

    //getter und setter
    @Override
    public List<String> getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(List<String> emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
