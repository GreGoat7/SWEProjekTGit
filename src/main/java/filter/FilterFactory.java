package filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import constants.Constants;

public class FilterFactory implements IFilterFactory {

    @Override
    public Filter createFilter(String filterClassName) throws Exception {
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

    @Override
    public List<Filter> createFiltersFromConfig(String configFilePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
        String line;
        List<Filter> filters = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(Constants.FILTERLIST)) {
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

