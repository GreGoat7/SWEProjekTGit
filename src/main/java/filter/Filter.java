package filter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface Filter {
    void process(String inputFilePath, String outputFilePath) throws IOException;
}
