package ml.edcs.dao;

import ml.edcs.model.Register;
import ml.edcs.model.Result;
import ml.edcs.model.Vote;
import ml.edcs.model.Voting;

import java.util.*;

public class Storage {
    public final static String NAME = String.valueOf(new Random().nextInt());
    private static Map<String, List<Vote>> votes = new HashMap<>();
    private static Map<String, List<Register>> registrations = new HashMap<>();

    public static void registerVoting(String voting) {
        registrations.put(voting, new ArrayList<Register>());
        votes.put(voting, new ArrayList<Vote>());
    }

    public static void registerVoting(Voting voting) {
        Storage.registerVoting(voting.getName());
    }

    public static void vote(Vote vote) {
        if (votes.containsKey(vote.getName())) {
            List<Vote> votes = Storage.votes.get(vote.getName());
            votes.add(vote);
        } else System.out.println("Unknown vote for voting:" + vote.getName());
    }

    public static void register(Register register) {
        List<Register> registerList = Storage.registrations.get(register.getName());
        registerList.add(register);
    }

    public static Result getResult(String voting) {
        Result result = new Result();
        List<Vote> votes = Storage.votes.get(voting);
        result.setVotes(votes);
        result.setName(voting);
        result.setSender(NAME);
        return result;

    }
}
