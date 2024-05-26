package launcher;

import engine.ComputeEngine;
import client.ComputePi;

public class RMILauncher {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try {
                System.out.println("Starting ComputeEngine...");
                ComputeEngine server = new ComputeEngine();
                server.startServer();
            } catch (Exception e) {
                System.err.println("ComputeEngine failed to start:");
                e.printStackTrace();
            }
        });
        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Starting ComputePi...");
            ComputePi client = new ComputePi("localhost", "100");
            client.execute();
        } catch (Exception e) {
            System.err.println("ComputePi failed to execute:");
            e.printStackTrace();
        }
    }
}