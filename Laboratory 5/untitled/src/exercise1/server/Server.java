package exercise1.server;

import exercise1.interfaces.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {
    private JTextField portField;
    private JTextArea logTextArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton closeButton;
    private ServerSocket serverSocket;
    private boolean running = false;

    public Server() {
        super("TCP Server");
        setLayout(new BorderLayout());

        JPanel portPanel = new JPanel(new FlowLayout());
        portField = new JTextField("1025", 10);
        portPanel.add(new JLabel("Working Port:"));
        portPanel.add(portField);
        add(portPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        closeButton = new JButton("Exit Server");
        stopButton.setEnabled(false);

        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());
        closeButton.addActionListener(e -> closeServer());

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        add(new JScrollPane(logTextArea), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void log(String message) {
        logTextArea.append(message + "\n");
    }

    private void startServer() {
        try {
            int port = Integer.parseInt(portField.getText());
            serverSocket = new ServerSocket(port);
            log("Server started on port " + port + ", waiting for connections...");
            running = true;

            stopButton.setEnabled(true);
            startButton.setEnabled(false);

            new Thread(this::acceptClients).start();
        } catch (IOException e) {
            log("Error starting server: " + e.getMessage());
        } catch (NumberFormatException e) {
            log("Invalid port");
        }
    }

    private void acceptClients() {
        int connectionCount = 0;

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                connectionCount++;

                final int currentConnectionCount = connectionCount;
                new Thread(() -> {
                    try {
                        log("\tConnection " + currentConnectionCount + " starting execution...");
                        handleClient(clientSocket, currentConnectionCount);
                        log("\tConnection " + currentConnectionCount + " [WORK DONE]");
                    } catch (IOException | ClassNotFoundException e) {
                        log("Error handling connection " + currentConnectionCount + ": " + e.getMessage());
                    }
                }).start();
            } catch (IOException e) {
                if (!running) {
                    return;
                } else {
                    log("Error accepting clients: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket, int connectionNumber) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Executable ex = (Executable) in.readObject();

            double startTime = System.nanoTime();
            Object output = ex.execute();
            double endTime = System.nanoTime();
            double completionTime = endTime - startTime;

            Result result = new ResultImpl(output, completionTime);
            out.writeObject(result);

            log("\tConnection " + connectionNumber + " result sent...");
        } catch (IOException | ClassNotFoundException e) {
            log("Error handling client " + connectionNumber + ": " + e.getMessage());
        } finally {
            clientSocket.close();
            log("\tConnection " + connectionNumber + " finished...");
        }
    }

    private void stopServer() {
        try {
            running = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            log("The server stops working...");
        } catch (IOException e) {
            log("Error stopping server: " + e.getMessage());
        } finally {
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }

    private void closeServer() {
        stopServer();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Server::new);
    }
}