package processor;

import java.io.*;
import java.util.*;

// Processes a single order file and updates shared results and aggregates
public class OrderTask implements Runnable{

	private final String filename;
    private final ItemCatalog catalog;
    private final List<OrderResult> results;
    private final Map<String, Integer> totalCounts;
    private final Map<String, Double> totalRevenue;
    private final Object aggregateLock;

    // initializes task
    public OrderTask(String filename, ItemCatalog catalog, List<OrderResult> results,
                     Map<String, Integer> totalCounts,
                     Map<String, Double> totalRevenue, Object aggregateLock) {
    	
        this.filename = filename;
        this.catalog = catalog;
        this.results = results;
        this.totalCounts = totalCounts;
        this.totalRevenue = totalRevenue;
        this.aggregateLock = aggregateLock;
    }

    
    // Overrides the run function in runnable with my own code
    public void run() {
        try (Scanner sc = new Scanner(new File(filename))) {
            String header = sc.nextLine().trim();
            int clientId = Integer.parseInt(header.split(":")[1].trim());
            System.out.println("Reading order for client with id: " + clientId);
            
            OrderResult or = new OrderResult(clientId);
            while (sc.hasNext()) {
                String item = sc.next();
                sc.next(); // skip date
                or.addItem(item);
            }
            
            or.calculateTotals(catalog);

            synchronized (results) {
                results.add(or);
            }
            
            synchronized (aggregateLock) {
                for (Map.Entry<String, Integer> e : or.getCounts().entrySet()) {
                    String item = e.getKey();
                    int qty = e.getValue();
                    double line = or.getLineTotals().get(item);
                    
                    totalCounts.put(item, totalCounts.getOrDefault(item, 0) + qty);
                    totalRevenue.put(item, totalRevenue.getOrDefault(item, 0.0) + line);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error processing " + filename + ": " + ex.getMessage());
        }
    }
	
}
