package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import adressmodel.Person;
import adressmodel.Email;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToJsonEmailConverter implements Filter{

    @Override
    public void process(String inputFilePath, String outputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Person> personList;

        if (JsonUtils.isArray(inputFile)){
            personList = JsonUtils.fromJson(inputFile, new TypeReference<>() {});
        }
        else{
            Person singlePerson = JsonUtils.fromJson(inputFile, new TypeReference<Person>() {});
            personList = List.of(singlePerson); // Erstellen einer Liste mit der einzelnen Adresse
        }


        // Extrahieren der Email-Informationen aus den Adressobjekten
        List<Email> emails = new ArrayList<>();
        for (Person person : personList) {
            emails.addAll(person.getEmail());
        }

        // Schreiben der Email-Informationen in die JSON-Ausgabedatei
        File outputFile = new File(outputFilePath);
        JsonUtils.toJson(emails, outputFilePath);
    }
}
