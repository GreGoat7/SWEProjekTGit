package application;

import filter.Filter;
import filter.FilterFactory;
import pipeline.Pipeline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import constants.Constants;

// Die Hauptklasse des Projekts, die eine Pipeline eine Liste von Filtern aus einer Config-Datei list und in eine Pipeline
// einfügt und abarbeitet
public class Application {
    // in String args config-file übergeben
    public static void main(String[] args) {
        try {
            // Pfad zur Konfigurationsdatei, die Informationen über die zu verwendenden Filter und die zu verarbeitende Datei enthält.
            String configFilePath = "src/main/resources/config.txt";

            // Erzeugt eine Liste von Filtern basierend auf den Einstellungen in der Konfigurationsdatei.
            List<Filter> filters = FilterFactory.createFiltersFromConfig(configFilePath);

            // Erstellt eine neue Pipeline und fügt die Filter aus der Liste hinzu.
            Pipeline pipeline = new Pipeline();
            for (Filter filter : filters) {
                pipeline.addFilter(filter);
            }

            // Liest die Konfigurationsdatei und sucht nach dem Pfad der Datei, die verarbeitet werden soll.
            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            String startFilePath = null;
            while ((line = reader.readLine()) != null) {
                // Wenn die aktuelle Zeile mit "StartFile:" beginnt, wird der Pfad als der Rest der Zeile nach dem Doppelpunkt gespeichert.
                if (line.startsWith(Constants.STARTFILE)) {
                    startFilePath = line.split(":")[1].trim();
                }
            }

            // Überprüft, ob ein Pfad zur zu verarbeitenden Datei gefunden wurde.
            if (startFilePath != null) {
                // Wendet die Pipeline auf die Datei an und gibt das Ergebnis aus.
                String result = pipeline.runPipeline(startFilePath);
                System.out.println(result);
            } else {
                // Wenn kein Pfad zur Datei gefunden wurde, gibt eine Fehlermeldung aus.
                System.out.println("Kein StartFile Pfad in der Konfigurationsdatei gefunden.");
            }
        } catch (Exception e) {
            // Bei einem Fehler wird die Ausnahme ausgedruckt.
            e.printStackTrace();
        }
    }
}








