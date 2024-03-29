package test;

import consoleTasks.Point2D;

import java.io.*;
import java.util.*;

public class FileInterpolationTreeMap extends DataInterpolationTreeMap {
    public FileInterpolationTreeMap() {
        super();
    }

    public static void main(String[] args) {
        FileInterpolationTreeMap fun = new FileInterpolationTreeMap();

        int num;
        double x;

        Scanner in = new Scanner(System.in);

        do {
            System.out.print("Кількість точок: ");
            num = in.nextInt();
        } while (num <= 0);

        for (int i = 0; i < num; i++) {
            x = 1.0 + (5.0 - 1.0) * Math.random();
            fun.addPoint(new Point2D(x, Math.sin(x)));
        }

        System.out.println("Інтерполяція по: " + fun.numPoints() + " точкам");
        System.out.println("\nНе отсортований набір: ");
        for (int i = 0; i < fun.numPoints(); i++) {
            System.out.println("Точка " + (i + 1) + ": " + fun.getPoint(i));
        }

        System.out.println("\nОтсортований набір: ");
        for (int i = 0; i < fun.numPoints(); i++) {
            System.out.println("Точка " + (i + 1) + ": " + fun.getPoint(i));
        }

        System.out.println("\nМінімальне значення x: " + fun.getPoint(0).getX());
        System.out.println("Максимальне значення x: " +
                fun.getPoint(fun.numPoints() - 1).getX());

        System.out.println("\nЗберігаємо у файл");
        try {
            fun.writeToFile("dataTreeMap.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Зчитуємо з файла");
        fun.clear();
        try {
            fun.readFromFile("dataTreeMap.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        System.out.println("\nДані з файлу: ");
        for (int i = 0; i < fun.numPoints(); i++) {
            System.out.println("Точка " + (i + 1) + ": " + fun.getPoint(i));
        }
        System.out.println("\nМінімальне значення x: " + fun.getPoint(0).getX());
        System.out.println("Максимальне значення x: " +
                fun.getPoint(fun.numPoints() - 1).getX());
        x = 0.5 * (fun.getPoint(0).getX() + fun.getPoint(fun.numPoints() - 1).getX());

        System.out.println("\nЗначення інтерполяції fun(" + x + ") = " +
                fun.evalf(x));
        System.out.println("Точне значення sin(" + x + ") = " + Math.sin(x));
        System.out.println("Абсолютна похибка = " + Math.abs(fun.evalf(x) -
                Math.sin(x)));

        fun.clear();
        for (x = 1.0; x <= 7.0; x += 0.1) {
            fun.addPoint(new Point2D(x, Math.sin(x)));
        }
        try {
            fun.writeToFile("TblFuncTreeMap.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public void readFromFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s = in.readLine();
        clear();
        while ((s = in.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            addPoint(new Point2D(x, y));
        }
        in.close();
    }

    public void writeToFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        out.printf("%9s%25s\n", "x", "y");
        for (int i = 0; i < numPoints(); i++) {
            out.println(getPoint(i).getX() + "\t" + getPoint(i).getY());
        }
        out.close();
    }
}