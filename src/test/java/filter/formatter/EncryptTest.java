package filter.formatter;

//Testet die Verschlüsselung; gibt die verschlüsselte Datei aus (test.json)
public class EncryptTest {
    public static void main(String[] args) {
        EncryptFormatter encryptFormatter = new EncryptFormatter();
        try {
            String filePath = "src/test/resources/test.json";
            encryptFormatter.encryptFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
