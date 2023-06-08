package org.example;

import filter.formatter.DecryptFormatter;
import pipeline.Pipeline;
import filter.formatter.*;
import filter.converter.*;


import filter.converter.JsonToJsonEmailConverter;

public class Main {
    public static void main(String[] args) {

       Pipeline pipeline = new Pipeline();


       pipeline.addFilter(new DecryptFormatter());


        try {
            String outputFilePath = pipeline.runPipeline("src/main/java/maintest/Person.json");
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