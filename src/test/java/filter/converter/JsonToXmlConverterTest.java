package filter.converter;

import filter.converter.JsonToXmlConverter;
import org.junit.jupiter.api.Test;

import adressmodel.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonToXmlConverterTest {
    @Test
    public void testJsonToXmlConverter() throws IOException {
        // Pfad zur Eingabedatei und zur Ausgabedatei
        String inputFilePath = "src/test/resources/expected_emails.json";
        String outputFilePath = "src/test/resources/emails.xml";

        // Erstellen Sie ein neues JsonToXmlConverter-Objekt und verwenden Sie es, um die Dateien zu konvertieren
        JsonToXmlConverter converter = new JsonToXmlConverter();
        converter.process(inputFilePath, outputFilePath);

        // Überprüfen, ob die Ausgabedatei tatsächlich erstellt wurde
        assertTrue(Files.exists(Paths.get(outputFilePath)));

        // Hier könnten Sie weitere Tests hinzufügen, um die Genauigkeit der konvertierten Daten zu überprüfen
        // z.B. Lesen der XML-Datei und Überprüfen, ob bestimmte Elemente vorhanden sind, Überprüfen der Reihenfolge der Elemente usw.
    }
}
