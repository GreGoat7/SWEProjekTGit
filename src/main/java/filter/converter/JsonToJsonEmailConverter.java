package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import addressmodel.Person;
import addressmodel.Email;
import filter.Filter;
import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonToJsonEmailConverter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    @Override
    public String process(String inputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        System.out.println(inputFilePath);
        TypeReference<?> typeReference = jsonUtils.determineListType(inputFile);

        // Überprüfen, ob es sich um eine Liste von Personen handelt
        if (!typeReference.getType().equals(new TypeReference<List<Person>>() {}.getType())) {
            throw new IllegalArgumentException("Fehler beim Verusch Emails zu extrahieren: Ausgangsliste ist keine Personenliste");
        }

        Object result = jsonUtils.toJava(inputFile, typeReference);
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
        jsonUtils.fromJava(emails, outputFilePath);

        return outputFilePath;
    }
}

