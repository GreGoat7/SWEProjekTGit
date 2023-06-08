package filter.converter;
import com.fasterxml.jackson.core.type.TypeReference;
import constants.Constants;
import exceptions.NotAListException;
import exceptions.WrongFiletypeException;
import utils.JsonUtils;
import utils.YamlUtils;
import filter.Filter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonToYamlConverter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    YamlUtils yamlUtils = Constants.YAMLUTILS;

    @Override
    public String process(String inputFilePath) throws IOException, NotAListException, WrongFiletypeException {
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);
        TypeReference<?> typeReference = jsonUtils.determineListType(inputFile);
        Object result = jsonUtils.toJava(inputFile, typeReference);

        if (!(result instanceof List)) {
            throw new ClassCastException("Ergebnis ist keine Liste");
        }

        List<?> objectList = (List<?>) result;

        // Generieren des Ausgabedateipfades durch Ändern der Erweiterung von .json zu .xml
        Path inputPath = Paths.get(inputFilePath);
        String outputFilePath = Paths.get(inputPath.getParent().toString(), inputPath.getFileName().toString().replace(".json", ".yaml")).toString();

        // Schreiben der Informationen in die XML-Ausgabedatei
        File outputFile = new File(outputFilePath);
        yamlUtils.fromJava(objectList, outputFilePath);

        return outputFilePath;
    }
}
