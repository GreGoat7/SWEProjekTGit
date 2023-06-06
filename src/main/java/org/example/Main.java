package org.example;

import filter.formatter.DecryptFormatter;
import pipeline.Pipeline;
import filter.formatter.*;
import filter.converter.*;


import filter.converter.JsonToJsonEmailConverter;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

       Pipe pipeline = new Pipe();


       pipeline.addFilter(new DecryptFormatter());


        try {
            String outputFilePath = pipeline.process("src/main/java/maintest/Person.json");
            System.out.println("Final output file: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        Pipeline pipeline2 = new Pipeline();
        pipeline2.addFilter(new JsonToJsonEmailConverter());
        pipeline2.addFilter(new JsonToXmlConverter());

        try {
            String outputFilePath = pipeline2.process("src/main/java/maintest/Person.json");
            System.out.println("Final output file: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}