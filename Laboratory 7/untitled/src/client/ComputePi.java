package client;

import compute.Compute;
import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ComputePi {
    private String hostname;
    private String digits;

    public ComputePi(String hostname, String digits) {
        this.hostname = hostname;
        this.digits = digits;
    }

    public void execute() {
        try {
            Registry registry = LocateRegistry.getRegistry(hostname, 1099);

            String name = "Compute";
            Compute comp = (Compute) registry.lookup(name);

            int digits = Integer.parseInt(this.digits);
            Pi task = new Pi(digits);
            System.out.println("Submitting task to server...");
            BigDecimal pi = comp.executeTask(task);

            System.out.println("Computed value of Pi: " + pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}