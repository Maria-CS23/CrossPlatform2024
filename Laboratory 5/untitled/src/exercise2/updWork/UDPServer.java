package exercise2.updWork;

import java.io.*;
import java.net.*;

public class UDPServer {
    private ActiveUsers userList;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    private int port;

    public UDPServer(int serverPort) {
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            System.out.println("Error: " + e.getMessage());
        }
        userList = new ActiveUsers();
    }

    public void work(int bufferSize) {
        System.out.println("Server started...");
        try {
            while (true) {
                getUserData(bufferSize);
                log(address, port);
                sendUserData();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Server ended...");
            socket.close();
        }
    }

    private void log(InetAddress address, int port) {
        System.out.println("Request from: " + address.getHostAddress() + " port: " + port);
    }

    private void clear(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    private void getUserData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();
        User usr = new User(address, port);
        if (!userList.contains(usr)) {
            userList.add(usr);
        }
        clear(buffer);
    }

    private void sendUserData() throws IOException {
        byte[] buffer;
        for (int i = 0; i < userList.size(); i++) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(userList.get(i));
            buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
        buffer = new byte[0];
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    public static void main(String[] args) {
        int serverPort = 1501;
        int bufferSize = 256;
        UDPServer server = new UDPServer(serverPort);
        server.work(bufferSize);
    }
}