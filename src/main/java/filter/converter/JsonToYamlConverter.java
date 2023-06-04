package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import org.yaml.snakeyaml.Yaml;
import utils.JsonUtils;
import utils.XmlUtils;
import utils.YamlUtils;
import adressmodel.Address;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonToYamlConverter implements Filter {

    @Override
    public void process(String inputFilePath, String outputFilePath) throws IOException {
        // Lesen der XML-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Address> addresses;
        if(YamlUtils.isArray(inputFile)) {
            addresses = YamlUtils.fromYaml(inputFile, new TypeReference<List<Address>>() {});
        } else {
            addresses = Collections.singletonList(YamlUtils.fromYaml(inputFile, Address.class));
        }

        // Schreiben der Adressinformationen in die JSON-Ausgabedatei
        File outputFile = new File(outputFilePath);
        if(addresses.size() > 1) {
            JsonUtils.toJson(addresses, outputFilePath);
        } else {
            JsonUtils.toJson(addresses.get(0), outputFilePath);
        }
    }
}
