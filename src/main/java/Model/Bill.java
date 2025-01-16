package Model;

import Model.Exceptions.InsufficientStock;
import java.util.ArrayList;

public class Bill {
    private final ArrayList<Item> itemList;
    private final ArrayList<Integer> quantities;

    public Bill() {
        this.itemList = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public void addItem(Item item, int quantity) throws InsufficientStock {
        if (item.getQuantity() < quantity) {
            throw new InsufficientStock("Not enough stock for item: " + item.getItemName());
        }
        this.itemList.add(item);
        this.quantities.add(quantity);
        item.setQuantity(item.getQuantity() - quantity); // Update stock
    }

    public double calculateTotalProfit() {
        double totalProfit = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int quantity = quantities.get(i);
            totalProfit += item.getProfit() * quantity;
        }
        return totalProfit;
    }

    public String printBill() {
        StringBuilder bill = new StringBuilder();
        bill.append("Bill Details:\n");
        bill.append("--------------------------------------------------\n");
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int quantity = quantities.get(i);
            bill.append(String.format("Item: %s, Quantity: %d, Selling Price: %.2f, Total: %.2f\n",
                    item.getItemName(), quantity, item.getSellingPrice(), item.getSellingPrice() * quantity));
        }
        bill.append("--------------------------------------------------\n");
        bill.append(String.format("Total Profit: %.2f\n", calculateTotalProfit()));
        return bill.toString();
    }
}
