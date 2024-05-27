import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.List;

public class ConferenceServerGUI extends JFrame {
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextArea textArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    private Registry registry;
    private ConferenceServerImpl server;

    public ConferenceServerGUI() {
        setTitle("Conference Server");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Host:"));
        textFieldHost = new JTextField("localhost");
        topPanel.add(textFieldHost);
        topPanel.add(new JLabel("Port:"));
        textFieldPort = new JTextField("1099");
        topPanel.add(textFieldPort);
        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");

        stopButton.setEnabled(false);
        saveButton.setEnabled(false);
        loadButton.setEnabled(true);

        bottomPanel.add(startButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new StartButtonListener());
        stopButton.addActionListener(new StopButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        loadButton.addActionListener(new LoadButtonListener());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int port = Integer.parseInt(textFieldPort.getText());
                registry = LocateRegistry.createRegistry(port);
                server = new ConferenceServerImpl(textArea);
                registry.rebind("ConferenceServer", server);
                textArea.append("Server started on port " + port + "\n");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                saveButton.setEnabled(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                registry.unbind("ConferenceServer");
                UnicastRemoteObject.unexportObject(registry, true);
                textArea.append("Server stopped\n");
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                saveButton.setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(ConferenceServerGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().toLowerCase().endsWith(".xml")) {
                        file = new File(file.getParentFile(), file.getName() + ".xml");
                    }
                    String xml = server.exportParticipantsToXML();
                    FileWriter writer = new FileWriter(file);
                    writer.write(xml);
                    writer.close();
                    JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Participants saved to " + file.getAbsolutePath(), "Save", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(ConferenceServerGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    FileReader reader = new FileReader(file);
                    char[] buffer = new char[1024];
                    int numRead = reader.read(buffer);
                    StringBuilder sb = new StringBuilder();
                    while (numRead != -1) {
                        sb.append(buffer, 0, numRead);
                        numRead = reader.read(buffer);
                    }
                    reader.close();
                    List<Participant> participants = XMLUtils.fromXML(sb.toString());
                    server.loadParticipants(participants);
                    JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Participants loaded from " + file.getAbsolutePath(), "Load", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceServerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConferenceServerGUI serverGUI = new ConferenceServerGUI();
            serverGUI.setVisible(true);
            serverGUI.setLocationRelativeTo(null);
        });
    }
}