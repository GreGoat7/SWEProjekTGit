package filter;

import java.util.List;

public interface IFilterFactory {
    Filter createFilter(String filterClassName) throws Exception;

    /* list ein configfile und erstellt dann mithilfe der Methode "createFilter" mit Reflection die aufgeführten Filter */
    List<Filter> createFiltersFromConfig(String configFilePath) throws Exception;
}
