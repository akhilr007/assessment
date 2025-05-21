package assessment.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class StreamService {

    private static final Set<String> usedTop3Streamers = new HashSet<>();

    public StreamService() {

    }

    public static ObjectNode process(JsonNode input) {

        Set<String> usedTop3Streamers = new HashSet<>();

        ObjectNode output = JsonNodeFactory.instance.objectNode();

        for (JsonNode section : input) {

            String sectionName = getSectionName(section);

            List<String> streamList = getStreamerIDs(section);

            List<String> cleanedList = removeDuplicatesInTop3OfEachSection(streamList);

            output.putPOJO(sectionName, cleanedList);
        }

        return output;
    }

    private static String getSectionName(JsonNode section) {

        String sectionName = "sectionID";
        if (section.has(sectionName)) {
            return section.get(sectionName).asText();
        }

        return "EMPTY_SECTION";
    }

    private static List<String> getStreamerIDs(JsonNode section) {

        List<String> ids = new ArrayList<>();

        for (JsonNode streamer : section.get("sectionData")) {
            ids.add(streamer.get("streamerID").asText());
        }

        return ids;
    }

    private static List<String> removeDuplicatesInTop3OfEachSection(List<String> streamList) {

        Set<String> seen = new LinkedHashSet<>();
        List<String> uniqueTop3Streams = new ArrayList<>();

        int index = 0;
        while (uniqueTop3Streams.size() < 3 && index < streamList.size()) {
            String currentStream = streamList.get(index++);
            if (!seen.contains(currentStream)) {
                uniqueTop3Streams.add(currentStream);
                seen.add(currentStream);
            }
        }

        /*
            Potential failure solution:

            while (uniqueTop3Streams.size() < 3) {
                uniqueTop3Streams.add("PLACEHOLDER_STREAM_ID"); // Or a specific UUID
            }

         */

        List<String> result = new ArrayList<>();
        result.addAll(uniqueTop3Streams);
        for (int j = index; j < streamList.size(); j++) {
            result.add(streamList.get(j));
        }

        return result;
    }
}