package tcpWork.operations;

public class CardInfoOperation extends CardOperation {
    private String serialNumber;

    public CardInfoOperation(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public CardInfoOperation() {
        this("null");
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}