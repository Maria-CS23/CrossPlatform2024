package gui;

import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.ExpressionProgram;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class JFreeChartMainFrame extends JFrame {
    private JTextField textFieldFrom = null;
    private JTextField textFieldTo = null;
    private JTextField textFieldStep = null;
    private JTextField textFieldFunc = null;
    private XYSeries series = null;
    private XYSeries der = null;
    private double start, stop, step;

    public JFreeChartMainFrame() {
        setTitle("GUIApplication");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        contentPanel.setLayout(new BorderLayout());
        setContentPane(contentPanel);

        series = new XYSeries("Function");
        der = new XYSeries("Derivative");

        JPanel panelData = new JPanel();
        panelData.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelData.setPreferredSize(new Dimension(500, 50));
        contentPanel.add(panelData, BorderLayout.NORTH);

        JLabel lblFunction = new JLabel("Function");
        panelData.add(lblFunction);

        JPanel functionPanel = new JPanel(new BorderLayout());
        panelData.add(functionPanel);

        textFieldFunc = new JTextField();
        textFieldFunc.setText("sin(x)/x");
        textFieldFunc.setColumns(55);
        functionPanel.add(textFieldFunc, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        contentPanel.add(panelButtons, BorderLayout.SOUTH);

        JButton btnNewButtonPlot = new JButton("Plot");
        btnNewButtonPlot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start = Double.parseDouble(textFieldFrom.getText());
                stop = Double.parseDouble(textFieldTo.getText());
                step = Double.parseDouble(textFieldStep.getText());
                series.clear();
                der.clear();
                Parser parser = new Parser(Parser.STANDARD_FUNCTIONS);
                Variable var = new Variable("x");
                Variable par = new Variable("a");
                String funStr = String.valueOf(textFieldFunc.getText());
                parser.add(var);
                parser.add(par);
                ExpressionProgram funs = parser.parse(funStr);
                Expression ders = funs.derivative(var);
                double a = 0.5;
                par.setVal(a);
                for (double x = start; x < stop; x += step) {
                    var.setVal(x);
                    series.add(x, funs.getVal());
                    der.add(x, ders.getVal());
                }
            }
        });
        panelButtons.add(btnNewButtonPlot);

        JButton btnNewButtonExit = new JButton("Exit");
        btnNewButtonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelButtons.add(btnNewButtonExit);

        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(20, 50));
        panelButtons.add(spacerPanel);

        JLabel lblNewLabelStart = new JLabel("Start:");
        lblNewLabelStart.setPreferredSize(new Dimension(50, 50));
        panelButtons.add(lblNewLabelStart);

        textFieldFrom = new JTextField();
        textFieldFrom.setText("-6");
        textFieldFrom.setColumns(5);
        panelButtons.add(textFieldFrom);

        JLabel lblNewLabelStop = new JLabel("Stop:");
        lblNewLabelStop.setPreferredSize(new Dimension(50, 50));
        panelButtons.add(lblNewLabelStop);

        textFieldTo = new JTextField();
        textFieldTo.setText("6");
        textFieldTo.setColumns(5);
        panelButtons.add(textFieldTo);

        JLabel lblNewLabelStep = new JLabel("Step:");
        lblNewLabelStep.setPreferredSize(new Dimension(50, 50));
        panelButtons.add(lblNewLabelStep);

        textFieldStep = new JTextField();
        textFieldStep.setText("0.01");
        textFieldStep.setColumns(5);
        panelButtons.add(textFieldStep);

        JPanel panelChart = new JPanel();
        panelChart.setLayout(new BorderLayout());
        contentPanel.add(panelChart, BorderLayout.CENTER);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(der);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Function Graph",
                "X",
                "Y",
                dataset
        );

        chart.getPlot().setBackgroundPaint(ChartColor.WHITE);
        chart.getPlot().setForegroundAlpha(0.8f);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(chart);
        panelChart.add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFreeChartMainFrame frame = new JFreeChartMainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}