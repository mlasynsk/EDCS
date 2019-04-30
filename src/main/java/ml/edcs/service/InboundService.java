package ml.edcs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.dao.Storage;
import ml.edcs.model.*;

import java.io.IOException;
import java.util.*;

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
                case OPEN_VOTING:
                    this.registerForNewVoting(objectMapper.readValue(json, Voting.class));
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

    private void registerForNewVoting(Voting voting) {
        Register register = new Register();
        register.setSender(Storage.NAME);
        register.setName(voting.getName());
        System.out.println("Registering for:" + voting.getName());
        outboundService.register(register);


        Vote vote = new Vote();
        vote.setName(voting.getName());
        vote.setSender(Storage.NAME);
        vote.setReceiveTime(new Date());
        List<String> options = voting.getOptions();
        int index = random.nextInt() % options.size();
        vote.setOption(options.get(Math.abs(index)));

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer out voting time is come id:" + voting.getName());
                outboundService.vote(vote);
            }
        };
        timer.schedule(timerTask, voting.getStartTime());


    }

    private void vote(Vote vote) {
        Storage.vote(vote);
    }
}
