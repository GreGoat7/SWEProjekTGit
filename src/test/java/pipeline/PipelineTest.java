/*package pipeline;

import filter.formatter.DecryptFormatter;
import filter.converter.JsonToXmlConverter;
import filter.converter.JsonToJsonEmailConverter;
import filter.formatter.EncryptFormatter;
import pipeline.Pipeline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest {

    @Test
    public void testPipeline() {
        Pipeline pipeline = new Pipeline();
        pipeline.addFilter(new JsonToXmlConverter());
        pipeline.addFilter(new EncryptFormatter());
        pipeline.addFilter(new DecryptFormatter());

        try {
            String outputFilePath = pipeline.runPipeline("src/test/resources/Person.json");
            System.out.println("Final output file: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test: " + e.getMessage());
        }

        Pipeline pipeline2 = new Pipeline();
        pipeline2.addFilter(new JsonToJsonEmailConverter());
        pipeline2.addFilter(new JsonToXmlConverter());

        try {
            String outputFilePath = pipeline2.runPipeline("src/test/resources/Person.json");
            System.out.println("Final output file: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test: " + e.getMessage());
        }
    }
}
*/