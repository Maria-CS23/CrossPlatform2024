package exercise1.client;

import exercise1.interfaces.Result;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private JTextField ipAddressField;
    private JTextField portField;
    private JTextField inputField;
    private JTextArea resultTextArea;
    private JButton calculateButton;
    private JButton clearButton;
    private JButton closeButton;

    public Client() {
        super("TCP Client");
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        ipAddressField = new JTextField("localhost", 10);
        portField = new JTextField("1025", 7);
        inputField = new JTextField(7);
        inputPanel.add(new JLabel("IP Address:"));
        inputPanel.add(ipAddressField);
        inputPanel.add(new JLabel("Port:"));
        inputPanel.add(portField);
        inputPanel.add(new JLabel("Number:"));
        inputPanel.add(inputField);
        add(inputPanel, BorderLayout.NORTH);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        calculateButton = new JButton("Calculate");
        clearButton = new JButton("Clear Result");
        closeButton = new JButton("Exit Program");

        calculateButton.addActionListener(e -> calculate());
        clearButton.addActionListener(e -> clearResults());
        closeButton.addActionListener(e -> closeClient());

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculate() {
        String ipAddress = ipAddressField.getText();
        int port;
        int num;

        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            resultTextArea.append("Invalid port\n");
            return;
        }

        try {
            num = Integer.parseInt(inputField.getText());
        } catch (NumberFormatException e) {
            resultTextArea.append("Invalid number\n");
            return;
        }

        try (Socket clientSocket = new Socket(ipAddress, port);
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            resultTextArea.append("Connected to server\n");

            JobOne aJob = new JobOne(num);
            out.writeObject(aJob);
            resultTextArea.append("Task sent to server\n");

            Result result = (Result) in.readObject();
            resultTextArea.append("Result: " + result.output() + "\n");
            resultTextArea.append("Time taken: " + result.scoreTime() + "ns\n");
        } catch (IOException | ClassNotFoundException e) {
            if (e.getMessage().contains("Connection refused")) {
                resultTextArea.append("Server is not available\n");
            } else {
                resultTextArea.append("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void clearResults() {
        resultTextArea.setText("");
        inputField.setText("");
    }

    private void closeClient() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}