package filter.converter;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.YamlUtils;
import adressmodel.Address;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonToYamlConverter implements Filter {

    @Override
    public String process(String inputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Address> addresses;

        if(JsonUtils.isArray(inputFile)) {
            addresses = JsonUtils.fromJson(inputFile, new TypeReference<List<Address>>() {});
        } else {
            Address singleAddress = JsonUtils.fromJson(inputFile, new TypeReference<Address>() {});
            addresses = Collections.singletonList(singleAddress);
        }

        // Erstellen des Pfads für die Ausgabedatei durch Ersetzen von .json durch .yaml
        String outputFilePath = inputFilePath.replace(".json", ".yaml");

        // Schreiben der Adressinformationen in die YAML-Ausgabedatei
        if(addresses.size() > 1) {
            YamlUtils.toYaml(addresses, outputFilePath);
        } else {
            YamlUtils.toYaml(addresses.get(0), outputFilePath);
        }

        return outputFilePath;
    }
}
