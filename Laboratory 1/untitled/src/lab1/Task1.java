package lab1;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.Scanner;
public class Task1 extends JFrame {
    private JTextField classInputField;
    private JTextArea textArea;

    public Task1() {
        setTitle("Аналізатор класу");
        setSize(400, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel className = new JLabel("Введіть повне ім'я класу:");
        className.setBounds(20, 20, 120, 25);
        panel.add(className);

        classInputField = new JTextField(20);
        classInputField.setBounds(150, 20, 200, 25);
        panel.add(classInputField);

        JButton analyzeButton = new JButton("Аналіз");
        analyzeButton.setBounds(20, 240, 100, 25);
        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                analyzeClass(classInputField.getText());
            }
        });
        panel.add(analyzeButton);

        JButton clearButton = new JButton("Очистити");
        clearButton.setBounds(140, 240, 100, 25);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        panel.add(clearButton);

        JButton exitButton = new JButton("Завершити");
        exitButton.setBounds(260, 240, 100, 25);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 50, 350, 180);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    private void analyzeClass(String className) {
        if (className != null && !className.isEmpty()) {
            try {
                String description = getClassDescription(className);
                textArea.setText(description);
            } catch (ClassNotFoundException e) {
                textArea.setText("Клас не знайдено: " + className);
            }
        } else {
            textArea.setText("Введіть клас");
        }
    }

    public static String getClassDescription(String typeName) throws ClassNotFoundException {
        Class<?> cls = Class.forName(typeName);
        return getClassDescription(cls);
    }

    public static String getClassDescription(Class<?> cls) {
        StringBuilder sb = new StringBuilder();

        sb.append("Пакет: ").append(cls.getPackageName()).append("\n");
        sb.append("Модифікатори: ").append(Modifier.toString(cls.getModifiers())).append("\n");
        sb.append("Назва класу: ").append(cls.getSimpleName()).append("\n");

        Class<?> superclass = cls.getSuperclass();
        if (superclass != null) {
            sb.append("Суперклас: ").append(superclass.getSimpleName()).append("\n");
        }

        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            sb.append("Реалізовані інтерфейси: ");
            for (Class<?> iface : interfaces) {
                sb.append(iface.getSimpleName()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
        }

        Field[] fields = cls.getDeclaredFields();
        if (fields.length > 0) {
            sb.append("// Поля\n");
            for (Field field : fields) {
                sb.append("\t").append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getSimpleName()).append(" ").append(field.getName()).append("\n");
            }
        }

        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        if (constructors.length > 0) {
            sb.append("// Конструктори\n");
            for (Constructor<?> constructor : constructors) {
                sb.append("\t").append(Modifier.toString(constructor.getModifiers())).append(" ")
                        .append(constructor.getName()).append("(");
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    sb.append(parameterTypes[i].getSimpleName());
                    if (i < parameterTypes.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")\n");
            }
        }

        Method[] methods = cls.getDeclaredMethods();
        if (methods.length > 0) {
            sb.append("// Методи\n");
            for (Method method : methods) {
                sb.append("\t").append(Modifier.toString(method.getModifiers())).append(" ")
                        .append(method.getReturnType().getSimpleName()).append(" ").append(method.getName()).append("(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    sb.append(parameterTypes[i].getSimpleName());
                    if (i < parameterTypes.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")\n");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type Java class full name (for example java.util.Date): ");
        String input = scanner.nextLine();
        scanner.close();
        try {
            String description = getClassDescription(input);
            System.out.println(description);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task1();
            }
        });
    }
}