package exercise1.client;

import exercise1.interfaces.Executable;
import java.io.Serializable;
import java.math.BigInteger;

public class JobOne implements Executable, Serializable {
    private static final long serialVersionUID = 1L;
    private int n;

    public JobOne(int n) {
        this.n = n;
    }

    @Override
    public Object execute() {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}