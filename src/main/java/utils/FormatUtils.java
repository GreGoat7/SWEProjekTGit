package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FormatUtils {
    public enum FileType{
        JSON,XML,YAML,UNKNOWN
    }
    
    
    // Diese Methode erkennt das Dateiformat anhand der Endung .json, .xml, .yaml
    public static FileType detectFileType(String pathToFile) {
        String extension = "";

        int i = pathToFile.lastIndexOf('.');
        if (i >= 0) {
            extension = pathToFile.substring(i+1);
        }

        // durch equalsIgnoreCase wird nicht auf Groß- und Kleinschreibung geachtet
        if(extension.equalsIgnoreCase("json")) {
            return FileType.JSON;
        } else if(extension.equalsIgnoreCase("xml")) {
            return FileType.XML;
        } else if(extension.equalsIgnoreCase("yaml") || extension.equalsIgnoreCase("yml")) {
            return FileType.YAML;
        } else {
            return FileType.UNKNOWN;
        }
    }
}
