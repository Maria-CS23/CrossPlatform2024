import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ConferenceClientGUI extends JFrame {
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextField textFieldName;
    private JTextField textFieldFamilyName;
    private JTextField textFieldPlaceOfWork;
    private JTextField textFieldReportTitle;
    private JTextField textFieldEmail;
    private JButton registerButton;
    private JButton clearButton;
    private JButton getInfoButton;
    private JButton finishButton;

    public ConferenceClientGUI() {
        setTitle("Conference Client");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(new JLabel("Host:"));
        textFieldHost = new JTextField("localhost");
        topPanel.add(textFieldHost);
        topPanel.add(new JLabel("Port:"));
        textFieldPort = new JTextField("1099");
        topPanel.add(textFieldPort);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centerPanel.add(new JLabel("Name:"));
        textFieldName = new JTextField();
        centerPanel.add(textFieldName);
        centerPanel.add(new JLabel("Family:"));
        textFieldFamilyName = new JTextField();
        centerPanel.add(textFieldFamilyName);
        centerPanel.add(new JLabel("Organization:"));
        textFieldPlaceOfWork = new JTextField();
        centerPanel.add(textFieldPlaceOfWork);
        centerPanel.add(new JLabel("Report:"));
        textFieldReportTitle = new JTextField();
        centerPanel.add(textFieldReportTitle);
        centerPanel.add(new JLabel("e-mail:"));
        textFieldEmail = new JTextField();
        centerPanel.add(textFieldEmail);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        registerButton = new JButton("Register");
        clearButton = new JButton("Clear");
        getInfoButton = new JButton("Get Info");
        finishButton = new JButton("Finish");

        bottomPanel.add(registerButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(getInfoButton);
        bottomPanel.add(finishButton);
        add(bottomPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new RegisterButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        getInfoButton.addActionListener(new GetInfoButtonListener());
        finishButton.addActionListener(new FinishButtonListener());
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String host = textFieldHost.getText();
                int port = Integer.parseInt(textFieldPort.getText());
                Registry registry = LocateRegistry.getRegistry(host, port);
                ConferenceServer server = (ConferenceServer) registry.lookup("ConferenceServer");

                String name = textFieldName.getText();
                String familyName = textFieldFamilyName.getText();
                String placeOfWork = textFieldPlaceOfWork.getText();
                String reportTitle = textFieldReportTitle.getText();
                String email = textFieldEmail.getText();

                Participant participant = new Participant(name, familyName, placeOfWork, reportTitle, email);
                int count = server.registerParticipant(participant);

                JOptionPane.showMessageDialog(ConferenceClientGUI.this, "Registration successful! Total participants: " + count, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceClientGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textFieldName.setText("");
            textFieldFamilyName.setText("");
            textFieldPlaceOfWork.setText("");
            textFieldReportTitle.setText("");
            textFieldEmail.setText("");
        }
    }

    private class GetInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String host = textFieldHost.getText();
                int port = Integer.parseInt(textFieldPort.getText());
                Registry registry = LocateRegistry.getRegistry(host, port);
                ConferenceServer server = (ConferenceServer) registry.lookup("ConferenceServer");

                String info = server.exportParticipantsToXML();
                List<Participant> participants = XMLUtils.fromXML(info);

                StringBuilder infoBuilder = new StringBuilder();
                infoBuilder.append("Participants Info:\n");
                for (Participant participant : participants) {
                    infoBuilder.append("- Name: ").append(participant.getName()).append(", ")
                            .append("Family Name: ").append(participant.getFamilyName()).append(", ")
                            .append("Place of Work: ").append(participant.getPlaceOfWork()).append(", ")
                            .append("Report Title: ").append(participant.getReportTitle()).append(", ")
                            .append("Email: ").append(participant.getEmail()).append("\n");
                }

                JOptionPane.showMessageDialog(ConferenceClientGUI.this, infoBuilder.toString(), "Participants Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ConferenceClientGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class FinishButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConferenceClientGUI clientGUI = new ConferenceClientGUI();
            clientGUI.setVisible(true);
            clientGUI.setLocationRelativeTo(null);
        });
    }
}