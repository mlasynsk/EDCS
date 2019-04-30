package ml.edcs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.dao.Storage;
import ml.edcs.model.Register;
import ml.edcs.model.Result;
import ml.edcs.model.Vote;
import ml.edcs.model.Voting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class OutboundService {

    private static final String ipAddress = "230.0.0.0";
    private static final int port = 4321;
    private ObjectMapper objectMapper;

    public OutboundService() {
        objectMapper = new ObjectMapper();
    }

    public void register(Register register) {
        this.send(register);
    }

    public void result(Result result) {
        this.send(result);
    }

    public void create(Voting voting) {
        Storage.registerVoting(voting);
        Timer timer = new Timer();
        OutboundService outboundService = this;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer out it's time for results id:" + voting.getName());
                Result result = Storage.getResult(voting.getName());
                if (result != null) outboundService.result(result);
            }
        };
        timer.schedule(timerTask, voting.getResultTime());

        this.send(voting);
    }

    public void vote(Vote vote) {
        this.send(vote);
    }

    private void send(Object obj) {
        try {
            this.send(objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void send(String message) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(ipAddress);
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
