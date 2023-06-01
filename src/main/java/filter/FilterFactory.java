package filter;

public class FilterFactory {
    // Erstellung der konkreten Filter-Objekte mit reflection
    public static Filter createFilter(String filterClassName) throws Exception {
        // Beispiel filterClassName = filter.converter.JsonToXmlConverter
        Class<?> filterClass = Class.forName(filterClassName);
        return (Filter) filterClass.getDeclaredConstructor().newInstance();
    }
}
