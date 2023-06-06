package filter.formatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class EncryptDecryptTest {

    private EncryptFormatter encryptFormatter;
    private DecryptFormatter decryptFormatter;

    @BeforeEach
    public void setup() {
        encryptFormatter = new EncryptFormatter();
        decryptFormatter = new DecryptFormatter();
    }

    @Test
    public void testDecrypt() throws Exception {
        String filePath = "src/test/resources/test.xml";

        // Die Originaldatei speichern, bevor sie verschlüsselt wurde
        File originalFile = new File(filePath);
        byte[] originalFileBytes = Files.readAllBytes(originalFile.toPath());

        // Verschlüsselt die Datei
        encryptFormatter.process(filePath);

        // Entschlüsselt die Datei
        decryptFormatter.process(filePath);

        // Die Datei nach der Entschlüsselung speichern
        File decryptedFile = new File(filePath);
        byte[] decryptedFileBytes = Files.readAllBytes(decryptedFile.toPath());

        // Vergleichet Originaldatei und die entschlüsselte Datei
        assertArrayEquals(originalFileBytes, decryptedFileBytes);

        // Eine Nachricht ausgeben, wenn der Test bestanden hat
        System.out.println("The test passed successfully. The original file and the decrypted file are identical.");
    }
}
