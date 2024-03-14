package lab1;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}

public class Task3 {
    public static void callMethod(Object obj, String methodName, List<Object> parameters) throws FunctionNotFoundException {
        Class<?>[] paramTypes = new Class<?>[parameters.size()];
        Object[] paramValues = parameters.toArray();

        for (int i = 0; i < parameters.size(); i++) {
            paramTypes[i] = parameters.get(i).getClass();
        }

        try {
            Method[] methods = obj.getClass().getMethods();
            Method methodToCall = null;

            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterCount() == parameters.size()) {
                    methodToCall = method;
                    break;
                }
            }

            if (methodToCall == null) {
                throw new FunctionNotFoundException("Метод '" + methodName + "' із " + parameters.size() + " параметрами не знайдено");
            }

            Object result = methodToCall.invoke(obj, paramValues);
            System.out.println(obj);
            System.out.println("Типи: " + Arrays.toString(paramTypes) + ", значення: " + parameters);
            System.out.println("Результат виклику: " + result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Метод '" + methodName + "' неможливо викликати");
        }
    }

    public static void main(String[] args) {
        TestClass test = new TestClass(1.0);
        try {
            callMethod(test, "evaluate", Arrays.asList(1.0));
            callMethod(test, "evaluate", Arrays.asList(1.0, 1));
        } catch (FunctionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class TestClass {
    double a;

    public TestClass(double a) {
        this.a = a;
    }

    public double evaluate(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    public double evaluate(double x, int y) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    public String toString() {
        return "TestClass [a=" + a + ", exp(-abs(a)*x)*sin(x)]";
    }
}