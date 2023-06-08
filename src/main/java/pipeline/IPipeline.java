package pipeline;

import filter.Filter;

public interface IPipeline {

    // Diese Methode fügt einen Filter zur Pipeline hinzu.

    // @param filter Der Filter, der zur Pipeline hinzugefügt werden soll.
    // @return Ein Pipeline-Objekt, das den hinzugefügten Filter enthält.
    Pipeline addFilter(Filter filter);

    // Die Methode runPipeline wendet alle Filter in der Pipeline auf eine Eingabe an.

    // @param  input   Die Eingabe, auf die die Filter angewendet werden sollen.
    // @return Ein String, der das Ergebnis der Anwendung aller Filter auf die Eingabe darstellt.
    // @throws Exception wenn während der Ausführung der Pipeline ein Fehler auftritt.
    String runPipeline(String input) throws Exception;
}
