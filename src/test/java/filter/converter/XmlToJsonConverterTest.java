/*package filter.converter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlToJsonConverterTest {
    @Test
    void testXmlToJson() throws Exception {
        String inputFilePath = "src/test/resources/emails.xml";
        // Generate output file path
        String outputFilePath = inputFilePath.replace(".xml", ".json");

        // Erstellen Sie ein neues XmlToJsonConverter-Objekt und verwenden Sie es, um die Datei zu konvertieren
        XmlToJsonConverter converter = new XmlToJsonConverter();
        converter.process(inputFilePath);

        // Überprüfen, ob die Ausgabedatei tatsächlich erstellt wurde
        assertTrue(Files.exists(Paths.get(outputFilePath)));

        // Hier könnten Sie weitere Tests hinzufügen, um die Genauigkeit der konvertierten Daten zu überprüfen
        // z.B. Lesen der XML-Datei und Überprüfen, ob bestimmte Elemente vorhanden sind, Überprüfen der Reihenfolge der Elemente usw.
    }
}*/
