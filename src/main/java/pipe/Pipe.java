package pipe;

import filter.Filter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Pipe {
    private List<Filter> filters;

    public Pipe() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void execute(InputStream input, OutputStream output) throws Exception {
        InputStream currentInput = input;
        OutputStream currentOutput;

        for (int i = 0; i < filters.size(); i++) {
            Filter filter = filters.get(i);

            if (i == filters.size() - 1) {
                // Letzter Filter, also verwende den gegebenen Output-Stream
                currentOutput = output;
            } else {
                // Nicht der letzte Filter, also erstelle einen neuen ByteArrayOutputStream
                // für den temporären Output
                currentOutput = new ByteArrayOutputStream();
            }

            // ausführen der Filterfunktion (converter oder formatter)
            filter.process(currentInput, currentOutput);

            if (currentInput != input) {
                // Schließe den vorherigen temporären Input-Stream, wenn es nicht der ursprüngliche ist
                currentInput.close();
            }

            if (i < filters.size() - 1) {
                // Nicht der letzte Filter, also aktualisiere den Input-Stream
                // für den nächsten Filter
                currentInput = new ByteArrayInputStream(((ByteArrayOutputStream) currentOutput).toByteArray());
            }
        }
    }
}