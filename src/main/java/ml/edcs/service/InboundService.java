package ml.edcs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.model.*;

import java.io.IOException;

public class InboundService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public synchronized void serve(String json) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
            String typeString = jsonNode.get("type").asText();
            Type type = Type.valueOf(typeString);
            switch (type) {
                case VOTE:
                    this.vote(objectMapper.readValue(json, Vote.class));
                    break;
                case CREATE:
                    this.create(objectMapper.readValue(json, Voting.class));
                    break;
                case RESULT:
                    this.result(objectMapper.readValue(json, Result.class));
                    break;
                case REGISTER:
                    this.register(objectMapper.readValue(json, Register.class));
                    break;
                default:
                    System.out.println("Error, unknown message type: " + typeString);
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void register(Register register) {

    }

    private void result(Result result) {

    }

    private void create(Voting voting) {

    }

    private void vote(Vote vote) {

    }
}
