package ml.edcs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.dao.Storage;
import ml.edcs.model.*;

import java.io.IOException;
import java.util.Date;

public class InboundService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private OutboundService outboundService = new OutboundService();

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
        System.out.println(register.toString());
        Storage.register(register);
    }

    private void result(Result result) {
        System.out.println(result);
    }

    private void create(Voting voting) {
        System.out.println(voting);
        Vote vote = new Vote();
        vote.setName(voting.getName());
        vote.setSender(Storage.NAME);
        vote.setReceiveTime(new Date());
        vote.setOption(voting.getOptions().get(0));
        try {
            Thread.sleep(3999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        outboundService.vote(vote);

    }

    private void vote(Vote vote) {
        Storage.vote(vote);
    }
}
