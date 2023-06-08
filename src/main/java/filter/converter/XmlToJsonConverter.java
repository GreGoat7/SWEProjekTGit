package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;
import utils.XmlUtils;
import filter.Filter;
import constants.Constants;

import java.io.File;
import java.util.List;

public class XmlToJsonConverter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;


    @Override
    public String process(String inputFilePath) throws Exception {
        // Lesen der XML-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        TypeReference<?> typeReference = xmlUtils.determineListType(inputFile);
        Object result = xmlUtils.toJava(inputFile, typeReference);

        if (!(result instanceof List)) {
            throw new ClassCastException("Ergebnis ist keine Liste");
        }

        List<?> objectList = (List<?>) result;

        // Erstellen des Pfads für die Ausgabedatei durch Ersetzen von .xml durch .json
        String outputFilePath = inputFilePath.replace(".xml", ".json");

        // Schreiben der Informationen in die JSON-Ausgabedatei
        jsonUtils.fromJava(objectList, outputFilePath);

        return outputFilePath;
    }
}
