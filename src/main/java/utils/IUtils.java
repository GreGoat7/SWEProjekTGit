package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;

public interface IUtils {

    <T> T toJava(File inputfile, TypeReference<T> type) throws IOException;

    void fromJava(Object obj, String filePath) throws IOException;

    TypeReference<?> determineListType(File inputFile) throws IOException;
}
