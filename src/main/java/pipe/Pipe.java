package pipe;

import filter.Filter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Pipe {
    private List<Filter> filters = new ArrayList<>();

    public Pipe addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public String process(String input) throws Exception {
        String output = input;
        for (Filter filter : filters) {
            output = filter.process(output);
        }
        return output;
    }
}