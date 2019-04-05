package ml.edcs;

import ml.edcs.model.Voting;
import ml.edcs.service.OutboundService;

import java.util.Arrays;

public class MainSender {


    public static void main(String[] args) {

        OutboundService outboundService = new OutboundService();
        Voting voting = new Voting();
        voting.setName("someName");
        voting.setSender("me");
        voting.setOptions(Arrays.asList("1", "2", "3"));
        outboundService.create(voting);
    }


}
