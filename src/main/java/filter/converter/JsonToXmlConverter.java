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

public class JsonToXmlConverter implements Filter{

    @Override
    public void process(String inputFilePath, String outputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Adressobjekten
        File inputFile = new File(inputFilePath);
        List<Person> personList;
        if(JsonUtils.isArray(inputFile)){
            personList = JsonUtils.fromJson(inputFile, new TypeReference<List<Person>>() {});
        } else {
            personList = Collections.singletonList(JsonUtils.fromJson(inputFile, new TypeReference<Person>() {}));
        }

        // Schreiben der Adressinformationen in die XML-Ausgabedatei
        File outputFile = new File(outputFilePath);
        if(personList.size() > 1){
            XmlUtils.toXml(personList, outputFilePath);
        } else {
            XmlUtils.toXml(personList.get(0), outputFilePath);
        }
    }
}
