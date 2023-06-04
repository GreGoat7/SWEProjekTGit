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
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        TypeReference<?> typeReference = JsonUtils.determineListType(inputFile);
        Object result = JsonUtils.fromJson(inputFile, typeReference);

        if (!(result instanceof List)) {
            throw new ClassCastException("Ergebnis ist keine Liste");
        }

        List<?> objectList = (List<?>) result;

        // Schreiben der Informationen in die XML-Ausgabedatei
        File outputFile = new File(outputFilePath);
        XmlUtils.toXml(objectList, outputFilePath);
    }

}
