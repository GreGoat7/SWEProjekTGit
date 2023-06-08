package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import exceptions.*;

public interface IUtils {

    <T> T toJava(File inputfile, TypeReference<T> type) throws IOException, WrongFiletypeException;

    <T> void fromJava(T obj, String filePath) throws IOException;

    TypeReference<?> determineListType(File inputFile) throws IOException, NotAListException;
}
