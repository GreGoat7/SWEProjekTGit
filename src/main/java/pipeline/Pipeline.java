package pipeline;

import filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class Pipeline {
    private List<Filter> filters = new ArrayList<>();

    public Pipeline addFilter(Filter filter) {
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