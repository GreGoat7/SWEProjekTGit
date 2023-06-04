package utils;

public class FormatUtilsTest {
    public static void main(String[] args) {
        testFileType("src/test/resources/adresse.json", "json");
        testFileType("src/test/resources/email.Json", "json");
        testFileType("src/test/resources/adresse.xml", "xml");
    }

    private static void testFileType(String filePath, String expectedFileType) {
        String detectedFileType = FormatUtils.detectFileType(filePath);
        if (detectedFileType.equals(expectedFileType)) {
            System.out.println("Test passed for file: " + filePath);
        } else {
            System.out.println("Test failed for file: " + filePath
                    + ". Expected: " + expectedFileType
                    + ", but got: " + detectedFileType);
        }
    }
}
