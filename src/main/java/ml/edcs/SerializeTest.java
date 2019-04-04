package ml.edcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.model.Vote;
import ml.edcs.model.Voting;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SerializeTest {
    @Test
    public void testSerialize() throws JsonProcessingException {


        Vote vote = new Vote();
      vote.setOption("ddfs");
      vote.setName("someName");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(vote));

        Voting voting = new Voting(Arrays.asList("1","2","3"));
        voting.setName("newVoting");
        System.out.println(objectMapper.writeValueAsString(voting));
    }

    @Test
    public void testDEserialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = "{\"sender\":\"someName\",\"receiveTime\":1554139360337,\"option\":\"someOpition\"}";
//        Vote vote1 = new Vote("someName", "someOpition");
//        String data =objectMapper.writeValueAsString(vote1);
        Vote vote = objectMapper.readValue(data, Vote.class);
        System.out.println(vote.getSender());
        JsonNode jsonNode = objectMapper.readTree(data);
        String color = jsonNode.get("receiveTime").asText();
        System.out.println(color);
    }
}
