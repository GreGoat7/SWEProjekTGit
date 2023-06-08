package filter;


public interface Filter {
    // converts/formats a inputfile and writes result to outputfile and returns it
    String process(String inputFilePath) throws Exception;
}
