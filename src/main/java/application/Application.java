package application;

import filter.Filter;
import filter.FilterFactory;
import pipeline.Pipeline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Application {
    // in String args config-file übergeben
    public static void main(String[] args) {
        try {
            String configFilePath = "src/main/resources/config.txt";
            List<Filter> filters = FilterFactory.createFiltersFromConfig(configFilePath);
            Pipeline pipeline = new Pipeline();
            for (Filter filter : filters) {
                pipeline.addFilter(filter);
            }

            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            String startFilePath = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("StartFile:")) {
                    startFilePath = line.split(":")[1].trim();
                }
            }



            if (startFilePath != null) {
                // Jetzt können Sie mit der Datei arbeiten, deren Pfad in startFilePath gespeichert ist
                String result = pipeline.runPipeline(startFilePath);
                System.out.println(result);
            } else {
                System.out.println("Kein StartFile Pfad in der Konfigurationsdatei gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
