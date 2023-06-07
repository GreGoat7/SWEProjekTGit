package filter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface Filter {
    // converts/formats a inputfile and writes result to outputfile and returns
    String process(String inputFilePath) throws Exception;
}
