package filter.converter;

import adressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
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
        List<Person> personList;

        TypeReference<List<Person>> targetClass = new TypeReference<>() {};


        if(XmlUtils.isArray(inputFile)) {
            personList = XmlUtils.fromXml(inputFile, new TypeReference<List<Person>>() {});
        } else {
            personList = Collections.singletonList(XmlUtils.fromXml(inputFile, new TypeReference<Person>() {
            }));
        }

        // Schreiben der Adressinformationen in die JSON-Ausgabedatei
        if(personList.size() > 1) {
            JsonUtils.toJson(personList, outputFilePath);
        } else {
            JsonUtils.toJson(personList.get(0), outputFilePath);
        }
    }
}
