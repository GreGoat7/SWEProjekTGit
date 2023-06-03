package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import adressmodel.Address;
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
        List<Address> addresses;
        if (JsonUtils.isArray(inputFile)){
            addresses = JsonUtils.fromJson(inputFile, new TypeReference<>() {});
        }
        else{
            Address singleAddress = JsonUtils.fromJson(inputFile, Address.class);
            addresses = List.of(singleAddress); // Erstellen einer Liste mit der einzelnen Adresse
        }


        // Extrahieren der Email-Informationen aus den Adressobjekten
        List<Email> emails = new ArrayList<>();
        for (Address address : addresses) {
            emails.addAll(address.getEmail());
        }

        // Schreiben der Email-Informationen in die JSON-Ausgabedatei
        File outputFile = new File(outputFilePath);
        JsonUtils.toJson(emails, outputFilePath);
    }
}
