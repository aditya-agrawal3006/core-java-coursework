package processor;

import java.text.*;
import java.util.*;

// Holds counts and computes totals for a single orde
public class OrderResult {

	private int clientId;
    private Map<String, Integer> counts = new TreeMap<>();
    private Map<String, Double> lineTotals = new TreeMap<>();
    private double orderTotal = 0.0;

    // Creates a result holder for a specific client ID
    public OrderResult(int clientId) {
        this.clientId = clientId;
    }

    // Aads one unit of the given item to this order
    public void addItem(String item) {
        counts.put(item, counts.getOrDefault(item, 0) + 1);
    }

    // Calculates per item totals and add it to the order total
    public void calculateTotals(ItemCatalog catalog) {
        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            String item = e.getKey();
            int qty = e.getValue();
            
            double price = catalog.getPrice(item);
            double total = price * qty;
            
            lineTotals.put(item, total);
            orderTotal += total;
        }
    }

    // Formats this orders report section
    public String formatReport(ItemCatalog catalog) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Client ").append(clientId).append("\n");
        sb.append(String.format("%-30s %8s %12s %12s\n",
                "Item", "Quantity", "Price", "Total"));
        for (String item : counts.keySet()) {
            int qty = counts.get(item);
            double price = catalog.getPrice(item);
            double total = lineTotals.get(item);
            sb.append(String.format("%-30s %8d %12s %12s\n", item, qty,
                    					fmt.format(price), fmt.format(total)));
        }
        
        sb.append("Order Total: ").append(fmt.format(orderTotal)).append("\n\n");
        
        return sb.toString();
    }

    public int getClientId() {
        return clientId;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public Map<String, Double> getLineTotals() {
        return lineTotals;
    }

	public double getOrderTotal() {
		return orderTotal;
	}
    
}
