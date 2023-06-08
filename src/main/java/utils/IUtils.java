package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import exceptions.*;
public interface IUtils {

    // Konvertiert eine Datei zu einem Java-Objekt des angegebenen Typs.

    // @param inputfile Die Eingabedatei, die konvertiert werden soll.
    // @param type Der TypeReference des Typs, zu dem die Datei konvertiert werden soll.
    // @return Gibt ein Java-Objekt des angegebenen Typs zurück, das die Daten aus der Eingabedatei darstellt.
    // @throws IOException Wird geworfen, wenn ein Ein-/Ausgabefehler auftritt.
    // @throws WrongFiletypeException Wird geworfen, wenn die Datei nicht in den angegebenen Typ konvertiert werden kann.
    <T> T toJava(File inputfile, TypeReference<T> type) throws IOException, WrongFiletypeException;

    // Konvertiert ein Java-Objekt in eine Datei.

    // @param obj Das Java-Objekt, das konvertiert werden soll.
    // @param filePath Der Pfad, an dem die Ausgabedatei erstellt werden soll.
    // @throws IOException Wird geworfen, wenn ein Ein-/Ausgabefehler auftritt.
    <T> void fromJava(T obj, String filePath) throws IOException;

    // Bestimmt den Typ der Liste, die in der Datei repräsentiert wird.

    // @param inputFile Die Datei, deren Listentyp bestimmt werden soll.
    // @return Gibt einen TypeReference des Typs der Liste zurück, die in der Datei repräsentiert wird.
    // @throws IOException Wird geworfen, wenn ein Ein-/Ausgabefehler auftritt.
    // @throws NotAListException Wird geworfen, wenn die Datei keine Liste repräsentiert.
    TypeReference<?> determineListType(File inputFile) throws IOException, NotAListException, WrongFiletypeException, WrongFormatException;
}
