import java.util.*;
import java.util.concurrent.*;

class InventoryManager {
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();
    private final Queue<String> waitlist = new ConcurrentLinkedQueue<>();

    public InventoryManager() {
        stock.put("PHONE15_256GB", 100);
    }

    // Synchronized purchase method
    public synchronized void purchase(String productId, String user) {
        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {
            stock.put(productId, available - 1);
            System.out.println(user + " purchase SUCCESS. Remaining stock: " + (available - 1));
        } else {
            waitlist.add(user);
            System.out.println(user + " added to WAITLIST. Position: " + waitlist.size());
        }
    }

    public void showWaitlist() {
        System.out.println("Waitlist: " + waitlist);
    }
}

public class FlashSaleDemo {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        // Simulating multiple users using threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 110; i++) {
            String user = "User" + i;
            executor.execute(() -> manager.purchase("PHONE15_256GB", user));
        }

        executor.shutdown();
    }
}