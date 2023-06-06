package filter.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonToXmlConverterTest {
    private JsonToXmlConverter converter;

    @BeforeEach
    void setUp() {
        converter = new JsonToXmlConverter();
    }

    @Test
    void testProcess() throws IOException {
        // Setzen Sie den Pfad zu Ihrer vorhandenen JSON-Datei
        String jsonFilePath = "src/test/resources/Test.json";

        // Führen Sie die Konvertierung durch
        converter.process(jsonFilePath);

        // Prüfen, ob eine XML-Datei mit dem erwarteten Namen erstellt wurde
        Path expectedXmlFilePath = Paths.get(jsonFilePath.replace(".json", ".xml"));
        assertTrue(Files.exists(expectedXmlFilePath), "Die XML-Datei wurde nicht erstellt.");
    }
}
