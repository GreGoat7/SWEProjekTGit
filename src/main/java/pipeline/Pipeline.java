package pipeline;

import filter.Filter;

import java.util.ArrayList;
import java.util.List;

// Diese Klasse repräsentiert eine Pipeline, die eine Liste von Filtern enthält.
// Jeder Filter ist eine Aktion, die auf einen Eingabestring angewendet wird und einen Ausgabestring zurückgibt.
// Die Pipeline führt alle Filter in der Reihenfolge aus, in der sie hinzugefügt wurden.
public class Pipeline implements IPipeline {
    private List<Filter> filters = new ArrayList<>();


    // Diese Methode fügt einen Filter zur Pipeline hinzu
    // Die Methode gibt die Pipeline selbst zurück, um das Hinzufügen von mehreren Filtern in einer Zeile zu ermöglichen.
    @Override
    public Pipeline addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public String runPipeline(String input) throws Exception {
        String output = input;
        for (Filter filter : filters) {
            output = filter.process(output);
        }
        // Gibt den Ausgabestring des letzten Filters in der Pipeline zurück.
        return output;
    }
}