package exercise2.bulletinBoardService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class MainFrame extends JFrame {
    private JTextField textFieldAddr;
    private JTextField textFieldPort;
    private JTextField textFieldName;
    private JButton connectButton;
    private JButton disconnectButton;
    private JButton clearButton;
    private JButton exitButton;
    private JTextArea textArea;
    private JTextField textFieldMsg;
    private JButton sendButton;
    private Messanger messanger = null;

    public MainFrame() {
        setTitle("Чат конференції");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        textFieldMsg = new JTextField(20);
        topPanel.add(textFieldMsg);
        sendButton = new JButton("Надіслати");
        sendButton.addActionListener(new SendListener());
        topPanel.add(sendButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(new JLabel("Група:"), gbc);
        gbc.gridy++;
        leftPanel.add(new JLabel("Порт:"), gbc);
        gbc.gridy++;
        leftPanel.add(new JLabel("Ім'я:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        textFieldAddr = new JTextField(7);
        textFieldAddr.setText("224.0.0.1");
        leftPanel.add(textFieldAddr, gbc);
        gbc.gridy++;
        textFieldPort = new JTextField(7);
        textFieldPort.setText("12345");
        leftPanel.add(textFieldPort, gbc);
        gbc.gridy++;
        textFieldName = new JTextField(7);
        leftPanel.add(textFieldName, gbc);

        panel.add(leftPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        connectButton = new JButton("З'єднати");
        connectButton.addActionListener(new ConnectListener());
        buttonPanel.add(connectButton);
        disconnectButton = new JButton("Роз'єднати");
        disconnectButton.addActionListener(new DisconnectListener());
        buttonPanel.add(disconnectButton);
        clearButton = new JButton("Очистити");
        clearButton.addActionListener(new ClearListener());
        buttonPanel.add(clearButton);
        exitButton = new JButton("Завершити");
        exitButton.addActionListener(new ExitListener());
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String addr = textFieldAddr.getText();
            int port = Integer.parseInt(textFieldPort.getText());
            String name = textFieldName.getText();
            InetAddress address;
            try {
                address = InetAddress.getByName(addr);
                UITasks ui = (UITasks) java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{UITasks.class}, new EDTInvocationHandler(new UITasksImpl(textFieldMsg, textArea)));
                messanger = new MessangerImpl(address, port, name, ui);
                messanger.start();
                connectButton.setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DisconnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (messanger != null) {
                messanger.stop();
                connectButton.setEnabled(true);
            }
        }
    }

    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (messanger != null) {
                messanger.send();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}