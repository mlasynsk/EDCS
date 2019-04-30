package ml.edcs.dao;

import ml.edcs.model.Register;
import ml.edcs.model.Result;
import ml.edcs.model.Vote;
import ml.edcs.model.Voting;
import ml.edcs.service.OutboundService;
import org.junit.Test;

import java.util.*;

public class Storage {
    public final static String NAME = String.valueOf(new Random().nextInt() % 100);
    private static Map<String, List<Vote>> votesMap = new HashMap<>();
    private static Map<String, List<Register>> registrations = new HashMap<>();
    private static List<Voting> votings = new ArrayList<>();
    private static Map<String, Result> resultsMap = new HashMap<>();
    private static OutboundService outboundService = new OutboundService();

    public static void registerVoting(Voting voting) {
        votings.add(voting);
        registrations.put(voting.getName(), new ArrayList<Register>());
        votesMap.put(voting.getName(), new ArrayList<Vote>());
    }

    public static void vote(Vote vote) {
        Voting voting = votings.stream().filter(v -> v.getName().equals(vote.getName())).findAny().orElse(null);
        if (voting == null) {
            System.out.println("Unknown vote for voting:" + vote.getName());
            return;
        }
        List<Register> registrations = Storage.registrations.get(voting.getName());
        Register register = registrations
                .stream()
                .filter(r -> r.getSender().equals(vote.getSender()))
                .findAny()
                .orElse(null);
        if (register == null) {
            System.out.println("Received vote which is not registered" + vote.getName());
            return;
        }
        Date now = new Date();
        Date startTime = voting.getStartTime();
        boolean isNotTimeYet = startTime.compareTo(now) > 0;
        List<Vote> votes = null;
        if (isNotTimeYet) {
            System.out.println("Received vote but it's not time for voting yet, name:" + vote.getName());
            return;
        } else if (votesMap.containsKey(vote.getName())) {
            votes = Storage.votesMap.get(vote.getName());
            votes.add(vote);
        }

        if (votes.size() == registrations.size()) {
            System.out.println("All registered have voted, making result");
            Result res = Storage.getResult(voting.getName());
            outboundService.result(res);
        }

    }

    public static void register(Register register) {
        Voting voting = votings.stream().filter(v -> v.getName().equals(register.getName())).findAny().orElse(null);
        if (voting == null) {
            System.out.println("Unknown vote for voting:" + register.getName());
            return;
        }

        List<Register> registerList = Storage.registrations.get(register.getName());
        if (registerList == null) {
            System.out.println("Unknown register for voting:" + register.getName());
        } else registerList.add(register);
    }

    public static Result getResult(String voting) {
        if (resultsMap.containsKey(voting)) {
            System.out.println("Resuld already sent:" + voting);
            return null;
        }
        Result result = new Result();
        List<Vote> votes = Storage.votesMap.get(voting);
        Map<String, Integer> results = new HashMap<>();
        for (Vote vote : votes) {
            String option = vote.getOption();
            if (results.containsKey(option)) {
                results.put(option, results.get(option) + 1);
            } else {
                results.put(option, 1);
            }
        }
        result.setVotes(results);
        result.setName(voting);
        result.setSender(NAME);

        resultsMap.put(result.getName(), result);

        return result;

    }

    @Test
    public void testEquals() {
        Voting v1 = new Voting();
        v1.setSender("sender");
        v1.setName("1");
        Voting v2 = new Voting();
        v2.setSender("sender2");
        v2.setName("2");
        Voting v3 = new Voting();
        v3.setSender("sender3");
        v3.setName("3");
        votings.add(v1);
        votings.add(v2);
        votings.add(v3);
        Voting v4 = new Voting();
        v4.setName("2");

        System.out.println(votings.indexOf(v4));
    }
}
