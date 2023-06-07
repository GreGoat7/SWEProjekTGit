package filter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* Das Interface "Filter" wird von den konkreten Konverter- und Formatmodulen implementiert, sodass sicher gestellt wird,
dass bei der Abarbeitung der Filter in der "Pipeline"-Klasse alle benutzten Filter das erwartete Interface einhalten.
 */
public interface Filter {
    /* Die process-Methode verarbeitet ein File(Pfad zum File = Eingabe inputFilePath) und schreibt das Ergebnis in
    eine neue Datei mit einem eigenen Pfad. Dieser Pfad ist die Rückgabe der Methode */
    /* Zum Hinzufügen von weiteren Filtern, die die Adresslisten oder Teile davon verarbeiten, muss eine Klasse, die das
    "Filter"-interface implementiert in das package: filter.external eingefügt werden. */
    String process(String inputFilePath) throws Exception;
}
