package application;

import beans.*;
import xml.XMLFileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MainFrame extends JFrame {
    private final DataSheetTablePanel dataSheetTablePanel;
    private final DataSheetGraph dataSheetGraph;
    private final JFileChooser fileChooser;

    public MainFrame() {
        setTitle("Java Beans");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dataSheetTablePanel = new DataSheetTablePanel();
        dataSheetGraph = new DataSheetGraph();
        add(dataSheetTablePanel, BorderLayout.WEST);
        add(dataSheetGraph, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
        JButton openButton = new JButton("Відкрити");
        JButton saveButton = new JButton("Зберегти");
        JButton clearButton = new JButton("Очистити");
        JButton exitButton = new JButton("Завершити");

        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        fileChooser = new JFileChooser(new File("XML Files"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));

        openButton.addActionListener(e -> {
            fileChooser.setDialogTitle("Open File");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File xmlFile = fileChooser.getSelectedFile();
                try {
                    DataSheet dataSheet = new DataSheet();
                    dataSheet.fillDocument(xmlFile);

                    dataSheetTablePanel.getDataSheetTable().getModel().setDataSheet(dataSheet);
                    dataSheetTablePanel.getDataSheetTable().revalidate();
                    dataSheetGraph.setDataSheet(dataSheet);
                    dataSheetGraph.repaint();
                } catch (ParserConfigurationException | FileNotFoundException | TransformerException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(e -> {
            fileChooser.setDialogTitle("Save File");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File directory = fileChooser.getSelectedFile();
                String fileName = XMLFileManager.generateNewFileName();
                File fileToSave = new File(directory, fileName);

                try {
                    dataSheetTablePanel.getDataSheetTable().getModel().getDataSheet().saveDocument(fileToSave);

                    JOptionPane.showMessageDialog(this, "File " + fileName + " saved!", "Результати збережені", JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException | TransformerException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while saving the file: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(e -> {
            dataSheetTablePanel.getDataSheetTable().getModel().clearDataSheet();
            dataSheetTablePanel.getDataSheetTable().revalidate();

            dataSheetGraph.setDataSheet(dataSheetTablePanel.getDataSheetTable().getModel().getDataSheet());
            dataSheetGraph.repaint();
        });

        exitButton.addActionListener(e -> System.exit(0));

        dataSheetTablePanel.getDataSheetTable().getModel().addDataSheetChangeListener(e -> {
            dataSheetGraph.setDataSheet(dataSheetTablePanel.getDataSheetTable().getModel().getDataSheet());
            dataSheetGraph.repaint();
        });
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
    }
}