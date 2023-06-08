package addressmodel;

import java.util.List;

public interface IEmail {

    boolean equals(Object o);

    int hashCode();

    //getter und setter
    List<String> getEmailAddress();

    void setEmailAddress(List<String> emailAddress);

    String getType();

    void setType(String type);
}
