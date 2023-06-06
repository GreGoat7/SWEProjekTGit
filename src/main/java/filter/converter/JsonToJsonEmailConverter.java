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
    public String process(String inputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        TypeReference<?> typeReference = JsonUtils.determineListType(inputFile);

        // Überprüfen, ob es sich um eine Liste von Personen handelt
        if (!typeReference.getType().equals(new TypeReference<List<Person>>() {}.getType())) {
            throw new IllegalArgumentException("Eingabedatei muss eine Liste von Personen enthalten");
        }

        Object result = JsonUtils.fromJson(inputFile, typeReference);
        List<Person> personList = (List<Person>) result;

        // Extrahieren der Email-Informationen aus den Adressobjekten
        List<Email> emails = new ArrayList<>();
        for (Person person : personList) {
            emails.addAll(person.getEmail());
        }

        // Aus dem ursprünglichen Pfad den Dateinamen extrahieren (ohne Erweiterung)
        String baseName = new File(inputFilePath).getName().replaceFirst("[.][^.]+$", "");

        // Den neuen Pfad erstellen
        String outputFilePath = new File(inputFile.getParent(), baseName + "_extracted_emails.json").getPath();

        // Schreiben der Email-Informationen in die JSON-Ausgabedatei
        File outputFile = new File(outputFilePath);
        JsonUtils.toJson(emails, outputFilePath);

        return outputFilePath;
    }
}

