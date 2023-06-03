package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
import adressmodel.Address;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonToXmlConverter implements Filter{

    @Override
    public void process(String inputFilePath, String outputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Address> addresses;
        if(JsonUtils.isArray(inputFile)){
            addresses = JsonUtils.fromJson(inputFile, new TypeReference<List<Address>>() {});
        } else {
            addresses = Collections.singletonList(JsonUtils.fromJson(inputFile, Address.class));
        }

        // Schreiben der Adressinformationen in die XML-Ausgabedatei
        File outputFile = new File(outputFilePath);
        if(addresses.size() > 1){
            XmlUtils.toXml(addresses, outputFilePath);
        } else {
            XmlUtils.toXml(addresses.get(0), outputFilePath);
        }
    }
}
