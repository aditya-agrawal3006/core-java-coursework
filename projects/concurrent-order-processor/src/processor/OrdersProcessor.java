package processor;

import java.io.*;
import java.util.*;
import java.text.NumberFormat;

// Main driver: reads inputs, processes orders,  writes
// reports, and prints timing
public class OrdersProcessor {
	
	// Reads user prompts, starts threads (or runs single-threaded), writes 
	// results file, and outputs processing time
	public static void main(String[] args) throws IOException, InterruptedException {
        // Prompt and read the items data filename
		Scanner console = new Scanner(System.in);
        System.out.print("Enter item's data file name:  ");
        String itemsFile = console.nextLine().trim();
        
        // Prompt and read threading choice
        System.out.print("Enter 'y' for multiple threads, any other character otherwise: ");
        String line = console.nextLine().trim();
        boolean useThreads = (line.length() > 0 && line.charAt(0) == 'y');
        
        // Prompt and read number of orders
        System.out.print("Enter number of orders to process: ");
        int n = Integer.parseInt(console.nextLine().trim());
        
        // Prompt and read base filename for orders
        System.out.print("Enter order's base filename: ");
        String baseName = console.nextLine().trim();
        
        // Prompt and read results output filename
        System.out.print("Enter result's filename: ");
        String resultFile = console.nextLine().trim();

        // Initialize catalog and shared aggregates
        ItemCatalog catalog = new ItemCatalog(itemsFile);
        List<OrderResult> results = Collections.synchronizedList(new ArrayList<>());
        Map<String, Integer> totalCounts = new TreeMap<>();
        Map<String, Double> totalRevenue = new TreeMap<>();
        Object aggregateLock = new Object();

        // Start timing
        long startTime = System.currentTimeMillis();

        if (!useThreads) {
            // Single thread: process each order in the main thread
            for (int i = 1; i <= n; i++) {
                new OrderTask(baseName + i + ".txt", catalog, results, 
                				totalCounts, totalRevenue, aggregateLock).run();
            }
        } else {
            // Multi thread: spawn one thread per order
            List<Thread> threads = new ArrayList<>();
            
            for (int i = 1; i <= n; i++) {
                Thread t = new Thread(new OrderTask(baseName + i + ".txt", catalog, 
                		results, totalCounts, totalRevenue, aggregateLock));
                
                threads.add(t);
                t.start();
            }
            
            // wait for all threads to finish
            for (Thread t : threads) {
            	t.join();
            }
        }

        // Write the results file and close its stream before stopping the timer
        writeResults(resultFile, results, catalog, totalCounts, totalRevenue);
        
        // Stop timing and print time
        long endTime = System.currentTimeMillis();
        System.out.println("Processing time (msec): " + (endTime - startTime));
        System.out.println("Results can be found in the file: " + resultFile);
    }

    // Writes per order reports and the overall summary
    private static void writeResults(String filename, List<OrderResult> results,
            ItemCatalog catalog, Map<String, Integer> totalCounts,
            Map<String, Double> totalRevenue) throws IOException {
    	
    	NumberFormat fmt = NumberFormat.getCurrencyInstance();
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            // Per order reports
            Collections.sort(results, Comparator.comparingInt(OrderResult::getClientId));
            
            for (OrderResult or : results) {
            	pw.println("----- Order details for client with Id: " 
            	           						+ or.getClientId() + " -----");
                
            	for (String item : or.getCounts().keySet()) {
                    int qty = or.getCounts().get(item);
                    double unit = catalog.getPrice(item);
                    double line = or.getLineTotals().get(item);
                    pw.printf(
                    		  "Item's name: %s, Cost per item: %s, Quantity: %d, Cost: %s%n",
                    		  item, fmt.format(unit), qty, fmt.format(line));
                }
                
            	pw.printf("Order Total: %s%n", fmt.format(or.getOrderTotal()));
            }

            // Global summary
            pw.println("***** Summary of all orders *****");
            double grandTotal = 0.0;
            
            for (String item : totalCounts.keySet()) {
                int sold = totalCounts.get(item);
                double rev  = totalRevenue.get(item);
                double unit = catalog.getPrice(item);
                
                pw.printf(
                		  "Summary - Item's name: %s, Cost per item: %s, Number sold: %d, Item's Total: %s%n",
                		  item, fmt.format(unit), sold, fmt.format(rev)
                		);
                grandTotal += rev;
            }
            
            pw.printf("Summary Grand Total: %s%n", fmt.format(grandTotal));
        }
    }
}
