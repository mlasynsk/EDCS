package ml.edcs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.edcs.model.Register;
import ml.edcs.model.Result;
import ml.edcs.model.Vote;
import ml.edcs.model.Voting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class OutboundService {

    private static final String ipAddress = "230.0.0.0";
    private static final int port = 4321;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void register(Register register) {
        this.send(register);
    }

    public void result(Result result) {
        this.send(result);
    }

    public void create(Voting voting) {
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
