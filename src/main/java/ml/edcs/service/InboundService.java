package ml.edcs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.dao.Storage;
import ml.edcs.model.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InboundService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private OutboundService outboundService = new OutboundService();
    private Random random = new Random();

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
        Storage.register(register);
    }

    private void result(Result result) {
    }

    private void create(Voting voting) {
        Vote vote = new Vote();
        vote.setName(voting.getName());
        vote.setSender(Storage.NAME);
        vote.setReceiveTime(new Date());
        List<String> options = voting.getOptions();
        int index = random.nextInt() % options.size();
        vote.setOption(options.get(Math.abs(index)));
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
