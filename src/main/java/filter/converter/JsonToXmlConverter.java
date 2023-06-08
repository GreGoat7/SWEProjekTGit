package filter.converter;

import adressmodel.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JsonToXmlConverter implements Filter {

    @Override
    public String process(String inputFilePath) throws IOException {
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        TypeReference<?> typeReference = JsonUtils.determineListType(inputFile);
        Object result = JsonUtils.fromJson(inputFile, typeReference);

        if (!(result instanceof List)) {
            throw new ClassCastException("Ergebnis ist keine Liste");
        }

        List<?> objectList = (List<?>) result;

        // Generieren des Ausgabedateipfades durch Ändern der Erweiterung von .json zu .xml
        Path inputPath = Paths.get(inputFilePath);
        String outputFilePath = Paths.get(inputPath.getParent().toString(), inputPath.getFileName().toString().replace(".json", ".xml")).toString();

        // Schreiben der Informationen in die XML-Ausgabedatei
        XmlUtils.toXml(objectList, outputFilePath);

        return outputFilePath;
    }

}
