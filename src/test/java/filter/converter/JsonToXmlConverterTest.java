/* package filter.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonToXmlConverterTest {
    JsonToXmlConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new JsonToXmlConverter();
    }

    @Test
    public void testProcessForValidInput() {
        String inputFilePath = "src/test/resources/Person.json"; // Hier geben Sie den Pfad zu Ihrer Testdatei an.
        String expectedOutputFilePath = "src/test/resources/Person.xml";

        assertDoesNotThrow(() -> {
            String result = converter.process(inputFilePath);
            assertEquals(expectedOutputFilePath, result);
            // Hier könnten Sie auch überprüfen, ob die resultierende XML-Datei den erwarteten Inhalt hat.
        });
    }

    @Test
    public void testProcessForInvalidInput() {
        String inputFilePath = "path/to/your/invalid/test.json"; // Hier geben Sie den Pfad zu einer ungültigen Testdatei an, die nicht in eine Liste konvertiert werden kann.

        assertThrows(ClassCastException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    @Test
    public void testProcessForNonexistentFile() {
        String inputFilePath = "path/to/nonexistent.json"; // Ein Pfad zu einer Datei, die nicht existiert.

        /*assertThrows(IOException.class, () -> {
            converter.process(inputFilePath);
        });
    }

    @Test
    public void testProcessForPersonFile(){
        String inputFilePath = "src/test/resources/Person.json";

    }
} */
