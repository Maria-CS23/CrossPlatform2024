package tcpWork.operations;

public class PayMoneyOperation extends CardOperation {
    private String serNum;
    private double amount;

    public PayMoneyOperation(String serNum, double amount) {
        this.serNum = serNum;
        this.amount = amount;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}