package assessment;

import assessment.services.StreamService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        String INPUT_PATH = "src/main/java/assessment/data/input.json";
        String OUTPUT_PATH = "src/main/java/assessment/data/output.json";

        try {
            JsonNode input = objectMapper.readTree(new File(INPUT_PATH));

            ObjectNode output = StreamService.process(input);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(OUTPUT_PATH), output);

            System.out.println("Processing of JSON completed. Kindly check the output file.");

        } catch (IOException e) {
            System.out.println("Error reading or writing files");
            e.printStackTrace();
        }
    }
}