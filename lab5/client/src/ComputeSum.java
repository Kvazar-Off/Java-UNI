package client;

import compute.Compute;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class ComputeSum {
    public static void main(String args[]) {
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Compute comp = (Compute) registry.lookup(name);
            Counter task = new Counter(Arrays.copyOfRange(args, 1, args.length));
            System.out.print(task.execute());
        } catch (Exception e) {
            System.err.println("ComputeSum exception:");
            e.printStackTrace();
        }
    }
}