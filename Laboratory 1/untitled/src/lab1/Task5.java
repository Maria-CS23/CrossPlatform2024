package lab1;

import java.lang.reflect.*;
import java.util.Arrays;

interface Evaluatable {
    double eval(double x);
}

class Function1 implements Evaluatable {
    @Override
    public double eval(double x) {
        return Math.exp(-Math.abs(2.5 * x)) * Math.sin(x);
    }
}

class Function2 implements Evaluatable {
    @Override
    public double eval(double x) {
        return x * x;
    }
}

class ProfilingHandler implements InvocationHandler {
    private final Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        System.out.println(method.getName() + " took " + (endTime - startTime) + " ns");
        return result;
    }
}

class TracingHandler implements InvocationHandler {
    private final Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("[" + method.getName());
        if (args != null) {
            System.out.print("(" + Arrays.toString(args) + ")");
        }
        System.out.print("] = ");
        Object result = method.invoke(target, args);
        System.out.println(result);
        return result;
    }
}

public class Task5 {
    public static void main(String[] args) {
        Evaluatable f1 = new Function1();
        Evaluatable f2 = new Function2();

        Evaluatable profiledF1 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new ProfilingHandler(f1)
        );

        Evaluatable tracedF1 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new TracingHandler(f1)
        );

        Evaluatable profiledF2 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new ProfilingHandler(f2)
        );

        Evaluatable tracedF2 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new TracingHandler(f2)
        );

        System.out.println("F1: " + profiledF1.eval(1.0));
        System.out.println("F2: " + profiledF2.eval(1.0));

        System.out.println();

        System.out.println("F1: " + tracedF1.eval(1.0));
        System.out.println("F2: " + tracedF2.eval(1.0));
    }
}