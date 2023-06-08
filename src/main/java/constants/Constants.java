package constants;

import utils.JsonUtils;
import utils.XmlUtils;
import utils.YamlUtils;

import java.nio.charset.StandardCharsets;

public final class Constants {
    private Constants(){}

    public static final String STARTFILE = "StartFile:";
    public static final String FILTERLIST = "Filters:";


    // Konstanten für Utils
    // erstellt neues Util Objekt
    public static final JsonUtils JSONUTILS = new JsonUtils();
    public static final XmlUtils XMLUTILS = new XmlUtils();
    public static final YamlUtils YAMLUTILS = new YamlUtils();

    // Konstanten für Encrypt/Decrypt
    // AES-Algorithmus wird für die Verschlüsselung/Entschlüsselung verwendet
    public static final String ALGORITHM = "AES";
    // Schlüssel für die AES-Verschlüsselung/Entschlüsselung
    public static final byte[] KEY = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);
}
