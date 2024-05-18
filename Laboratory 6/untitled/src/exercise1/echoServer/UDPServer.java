package exercise1.echoServer;

import java.io.IOException;
import java.net.*;

public abstract class UDPServer implements Runnable {
    private final int bufferSize;
    private final int port;
    private volatile boolean isShutDown = false;

    public UDPServer(int port, int bufferSize) {
        this.bufferSize = bufferSize;
        this.port = port;
    }

    public UDPServer(int port) {
        this(port, 8192);
    }

    public UDPServer() {
        this(12345, 8192);
    }

    public void shutDown() {
        this.isShutDown = true;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                if (isShutDown)
                    return;
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(incoming);
                    this.respond(socket, incoming);
                } catch (SocketTimeoutException ex) {
                    if (isShutDown)
                        return;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage() + "\n" + ex);
                }
            }
        } catch (SocketException ex) {
            System.err.println("Cannot use port: " + port + "\n" + ex);
        }
    }

    public abstract void respond(DatagramSocket socket, DatagramPacket request) throws IOException;
}