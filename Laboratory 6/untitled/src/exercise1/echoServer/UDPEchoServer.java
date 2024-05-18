package exercise1.echoServer;

import java.io.IOException;
import java.net.*;

public class UDPEchoServer extends UDPServer {
    public UDPEchoServer() {
        super(DEFAULT_PORT);
    }

    public final static int DEFAULT_PORT = 7;

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException {
        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
        socket.send(reply);
    }

    public static void main(String[] args) {
        System.out.println("Start echo-server...");
        UDPEchoServer server = new UDPEchoServer();
        Thread t = new Thread(server);
        t.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.shutDown();
        System.out.println("Finish echo-server...");
    }
}