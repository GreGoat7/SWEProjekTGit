package filter.converter;

import adressmodel.Email;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
import adressmodel.Address;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class XmlToJsonConverter implements Filter {

    @Override
    public void process(String inputFilePath, String outputFilePath) throws IOException {
        // Lesen der XML-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Address> addresses;

        TypeReference<List<Address>> targetClass = new TypeReference<>() {};


        if(XmlUtils.isArray(inputFile)) {
            addresses = XmlUtils.fromXml(inputFile, new TypeReference<List<Address>>() {});
        } else {
            addresses = Collections.singletonList(XmlUtils.fromXml(inputFile, Address.class));
        }

        // Schreiben der Adressinformationen in die JSON-Ausgabedatei
        if(addresses.size() > 1) {
            JsonUtils.toJson(addresses, outputFilePath);
        } else {
            JsonUtils.toJson(addresses.get(0), outputFilePath);
        }
    }
}
