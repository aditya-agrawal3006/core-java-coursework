package processor;

import java.io.*;
import java.util.*;

// Loads and provides access to item prices
public class ItemCatalog {

	private Map<String, Double> priceMap = new TreeMap<>();

	// Constructs an itemcatalog by reading the given data file
    public ItemCatalog(String dataFile) throws IOException {
        loadItems(dataFile);
    }

    // reads each item and its prce into the priceMap
    private void loadItems(String filename) throws IOException {
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNext()) {
                String name = sc.next();
                double price = sc.nextDouble();
                priceMap.put(name, price);
            }
        }
    }


    public double getPrice(String item) {
        return priceMap.getOrDefault(item, 0.0);
    }

    public Map<String, Double> getAllPrices() {
        return priceMap;
    }
	
}
