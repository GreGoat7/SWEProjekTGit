package pipeline;

import filter.Filter;

public interface IPipeline {
    // Diese Methode fügt einen Filter zur Pipeline hinzu
    // Die Methode gibt die Pipeline selbst zurück, um das Hinzufügen von mehreren Filtern in einer Zeile zu ermöglichen.
    Pipeline addFilter(Filter filter);

    String runPipeline(String input) throws Exception;
}
