package filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FilterFactory {


    /* erstelle einen Filter mithilfe von Reflection, indem geschaut wird ob eine Klasse mit dem Namen "filterClassName"
    im package filter existiert */

    public static Filter createFilter(String filterClassName) throws Exception {
        try {
            Class<?> filterClass = Class.forName(filterClassName);
            if (Filter.class.isAssignableFrom(filterClass)) {
                return (Filter) filterClass.getDeclaredConstructor().newInstance();
            } else {
                throw new Exception("Die Klasse " + filterClassName + " implementiert nicht das Filter-Interface");
            }
        } catch (ClassNotFoundException e) {
            throw new Exception("Der Filter " + filterClassName + " wurde nicht gefunden");
        }
    }

    /* list ein configfile und erstellt dann mithilfe der Methode "createFilter" mit Reflection die aufgeführten Filter */
    public static List<Filter> createFiltersFromConfig(String configFilePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
        String line;
        List<Filter> filters = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Filters:")) {
                String[] classNames = line.split(":")[1].trim().split(",");
                for (String className : classNames) {
                    Filter filter = createFilter(className.trim());
                    filters.add(filter);
                }
            }
        }

        reader.close();
        return filters;
    }
}

