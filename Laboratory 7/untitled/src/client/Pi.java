package client;

import compute.Task;
import java.math.BigDecimal;

public class Pi implements Task<BigDecimal> {
    private static final long serialVersionUID = 1L;
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);

    private final int digits;

    public Pi(int digits) {
        this.digits = digits;
    }

    @Override
    public BigDecimal execute() {
        System.out.println("Computing Pi with " + digits + " digits...");
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);

        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
        BigDecimal result = pi.setScale(digits, BigDecimal.ROUND_HALF_UP);

        System.out.println("Task executed. Result: " + result);

        return result;
    }

    private static BigDecimal arctan(int inverseX, int scale) {
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = invX.multiply(invX);

        BigDecimal result = BigDecimal.ZERO;
        boolean add = false;

        for (int i = 0; ; i++) {
            BigDecimal term = invX.divide(invX2.add(BigDecimal.valueOf(2 * i + 1)), scale, BigDecimal.ROUND_HALF_EVEN);
            if (term.equals(BigDecimal.ZERO)) {
                break;
            }
            result = add ? result.add(term) : result.subtract(term);
            add = !add;
            invX = invX.multiply(invX2);
        }

        return result;
    }
}