package utils;

//Eine KLasse implemntiert dieses Interface, wenn sie abhängig vom Dateiformat unterschiedlich funktioniert
public interface IFormatUtils {

    // erkennt das Dateiformat anhand der Endung .json, .xml, .yaml
    // @param   pathToFile   Pfad zur Eingabedatei
    FormatUtils.FileType detectFileType(String pathToFile);
}
