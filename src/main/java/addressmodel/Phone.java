package addressmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;

@JacksonXmlRootElement(localName = "Phone")
public class Phone implements IPhone {
    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    private String type;

    @JsonProperty("number")
    @JacksonXmlProperty(localName = "number")
    private String number;

    // Constructor
    public Phone(String type, String number){
        this.type = type;
        this.number = number;
    }

    public Phone(){

    }

    // Getter und Setter Methoden
    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    // Überschreibt die equals-Methode, um die Gleichheit von Phone-Objekten zu bestimmen.

    // @param o Das Objekt, mit dem dieses Phone-Objekt verglichen wird.
    // @return  Gibt 'true' zurück, wenn das gegebene Objekt das gleiche ist wie dieses Objekt,
    //          oder wenn das gegebene Objekt ein Phone ist und der Typ und
    //          die Nummer mit denen dieses Objekts übereinstimmen.
    //          Andernfalls wird 'false' zurückgegeben.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(type, phone.type) &&
                Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }

}
