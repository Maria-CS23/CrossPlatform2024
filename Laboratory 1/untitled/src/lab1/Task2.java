package lab1;

import java.lang.reflect.*;
import java.util.Scanner;

public class Task2 {
    public static void analyzeObject(Object obj) {
        Class<?> cls = obj.getClass();
        System.out.println("Стан об'єкту:");

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(field.getType().getSimpleName() + " " + field.getName() + " = " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nСписок відкритих методів:");
        Method[] methods = cls.getDeclaredMethods();
        int count = 1;
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                System.out.println(count + "). " + method.toString());
                count++;
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведіть порядковий номер методу [1 ," + (count - 1) + "]: ");
        int choice = scanner.nextInt();
        if (choice >= 1 && choice < count) {
            try {
                Method selectedMethod = methods[choice - 1];
                Object result = selectedMethod.invoke(obj);
                System.out.println("Результат виклику методу: " + result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Невірний вибір методу");
        }
    }

    public static void main(String[] args) {
        Check check = new Check(3.0, 4.0);

        analyzeObject(check);
    }
}

class Check {
    private double x;
    private double y;

    public Check(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double Dist() {
        return Math.sqrt(x * x + y * y);
    }

    public void setRandomData() {
        x = Math.random() * 10;
        y = Math.random() * 10;
    }

    @Override
    public String toString() {
        return "[x = " + x + ", y = " + y + "]";
    }
}