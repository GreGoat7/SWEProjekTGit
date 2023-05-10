package filter;
import java.io.InputStream;
import java.io.OutputStream;

public interface Filter {
    void process(InputStream input, OutputStream output);
}
