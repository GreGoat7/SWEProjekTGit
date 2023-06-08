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

    // Überschreibt die equals-Methode, um die Gleichheit von Email-Objekten zu bestimmen.

    // @param o Das Objekt, mit dem dieses Email-Objekt verglichen wird.
    // @return  Gibt 'true' zurück, wenn das gegebene Objekt das gleiche ist wie dieses Objekt,
    //          oder wenn das gegebene Objekt eine Email ist und der Typ und
    //          die E-Mail-Adresse mit denen dieses Objekts übereinstimmen.
    //          Andernfalls wird 'false' zurückgegeben.
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
