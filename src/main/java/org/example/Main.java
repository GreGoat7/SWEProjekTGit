package org.example;

import pipe.Pipe;
import filter.converter.JsonToXmlConverter;
import filter.formatter.EncryptFormatter;
import filter.converter.JsonToJsonEmailConverter;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

       Pipe pipeline = new Pipe();

       pipeline.addFilter(new JsonToXmlConverter());
       pipeline.addFilter(new EncryptFormatter());

        try {
            String outputFilePath = pipeline.process("src/main/java/maintest/Person.json");
            System.out.println("Final output file: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}