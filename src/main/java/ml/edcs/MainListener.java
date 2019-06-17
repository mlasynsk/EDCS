package ml.edcs;

import ml.edcs.dao.Storage;
import ml.edcs.model.Voting;
import ml.edcs.service.InboundService;
import ml.edcs.service.OutboundService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Random;

public class MainListener {

    public static String HOST;
    public static final String IP_ADDRESS = "230.0.0.0";
    public static final int PORT = 4321;

    public static void main(String[] args) {
        HOST = System.getProperty("ip");
        System.out.println("--------- ME = " + Storage.NAME);
        MainListener mainListener = new MainListener();
        Runnable runnable = () -> {
            try {
                mainListener.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


        OutboundService outboundService = new OutboundService();
        Voting voting = new Voting();
        voting.setName(String.valueOf(new Random().nextInt() % 20));
        voting.setSender(Storage.NAME);
        voting.setOptions(Arrays.asList("apple", "banana", "coffee"));
        voting.setDefaultTime();
        outboundService.create(voting);

    }

    private void listen() throws IOException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(PORT);
        socket.setTimeToLive(59);
        InetAddress group = InetAddress.getByName(IP_ADDRESS);
        socket.setInterface(InetAddress.getByName(HOST));
        socket.joinGroup(group);
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());

            InboundService inboundService = new InboundService();
            inboundService.serve(msg);
            System.out.println("------>" + msg);
            if ("OK".equals(msg)) {
                System.out.println("No more message. Exiting : " + msg);
                break;
            }
        }
    }
}
