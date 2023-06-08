package filter.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import exceptions.NotAListException;
import exceptions.WrongFiletypeException;
import exceptions.WrongFormatException;
import utils.JsonUtils;
import utils.XmlUtils;
import filter.Filter;
import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonToXmlConverter implements Filter {
    JsonUtils jsonUtils = Constants.JSONUTILS;
    XmlUtils xmlUtils = Constants.XMLUTILS;

    @Override
    public String process(String inputFilePath) throws IOException, NotAListException, WrongFiletypeException, WrongFormatException {
        // Lesen der JSON-Eingabedatei in eine Liste von Objekten
        File inputFile = new File(inputFilePath);

        if (!inputFile.exists()) {
            throw new IOException("Input file does not exist");
        }

        TypeReference<?> typeReference = jsonUtils.determineListType(inputFile);
        Object result = jsonUtils.toJava(inputFile, typeReference);



        List<?> objectList = (List<?>) result;

        // Generieren des Ausgabedateipfades durch Ändern der Erweiterung von .json zu .xml
        Path inputPath = Paths.get(inputFilePath);
        String outputFilePath = Paths.get(inputPath.getParent().toString(), inputPath.getFileName().toString().replace(".json", ".xml")).toString();

        // Schreiben der Informationen in die XML-Ausgabedatei
        xmlUtils.fromJava(objectList, outputFilePath);

        return outputFilePath;
    }

}
