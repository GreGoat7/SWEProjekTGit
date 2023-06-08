package filter;

import java.util.List;

public interface IFilterFactory {

    // Diese Methode erstellt ein Filter-Objekt anhand des vollständig qualifizierten Klassennamens.
    // @param   filterClassName   der vollständig qualifizierte Name der Filter-Klasse
    // @throws  Exception   wird geworfen, wenn die Filter-Klasse nicht gefunden wird oder keine Instanz erstellt werden kann
    // @return  Ein Filter-Objekt, das von der angegebenen Klasse erstellt wurde
    Filter createFilter(String filterClassName) throws Exception;


    // Diese Methode erstellt eine Liste von Filter-Objekten basierend auf einer Konfigurationsdatei.
    // Die Konfigurationsdatei enthält eine Liste von vollständig qualifizierten Klassennamen,
    // von denen jedes ein Filter-Objekt repräsentiert.

    // @param   configFilePath   der Pfad zur Konfigurationsdatei
    // @throws  Exception   wird geworfen, wenn die Konfigurationsdatei nicht gelesen werden kann
    //                      oder wenn ein Filter-Objekt nicht erstellt werden kann
    // @return  Eine Liste von Filter-Objekten, die basierend auf der Konfigurationsdatei erstellt wurden
    List<Filter> createFiltersFromConfig(String configFilePath) throws Exception;
}
