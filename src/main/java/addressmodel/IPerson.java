package addressmodel;

import java.util.List;

public interface IPerson {

    //getter Methoden
    String getFirstName();

    String getSurname();

    List<Email> getEmail();

    Address getAddress();

    String getAge();

    List<Phone> getPhone();

    //setter Methoden
    void setAddress(Address address);

    void setFirstName(String firstName);

    void setSurname(String surname);

    void setAge(String age);

    void setPhone(List<Phone> phone);
}
