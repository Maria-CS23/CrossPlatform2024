package engine;

import compute.Compute;
import compute.Task;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Compute {
    public ComputeEngine() {}

    @Override
    public <T> T executeTask(Task<T> t) throws RemoteException {
        System.out.println("Executing task on server...");
        T result = t.execute();
        System.out.println("Task executed. Result: " + result);
        return result;
    }

    public void startServer() {
        try {
            Compute engine = new ComputeEngine();

            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Compute", stub);

            System.out.println("ComputeEngine is ready to work");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}