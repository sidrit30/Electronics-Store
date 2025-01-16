package Model;

import Model.Exceptions.InsufficientStockException;
import Model.Items.Item;
import Model.Users.Cashier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bill {
    private final String billID;
    private ArrayList<Item> itemList;
    private ArrayList<Integer> quantities;
    private final Cashier cashier;
    private final LocalDateTime billTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private double profit;

    public Bill(Cashier cashier) {
        this.cashier = cashier;
        this.billTime = LocalDateTime.now();
        billID = UniqueIDGenerator.getUniqueId();
        this.itemList = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public String getBillID() {
        return billID;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public LocalDateTime getBillTime() {
        return billTime;
    }

    public void addItem(Item item, int quantity) throws InsufficientStockException {
        if (item.getQuantity() < quantity) {
            throw new InsufficientStockException("Not enough stock for item: " + item.getItemName());
        }
        this.itemList.add(item);
        this.quantities.add(quantity);
        item.setQuantity(item.getQuantity() - quantity);
    }

    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    private double calculateTotalProfit() {
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
        bill.append("Bill Date: ").append(billTime.format(formatter)).append("\n");
        bill.append("--------------------------------------------------\n");
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int quantity = quantities.get(i);
            bill.append(String.format("Item: %s\n Quantity: %d\n Selling Price: %.2f\n Total: %.2f\n",
                    item.getItemName(), quantity, item.getSellingPrice(), item.getSellingPrice() * quantity));
        }
        bill.append("--------------------------------------------------\n");
        bill.append(String.format("Total Profit: %.2f\n", calculateTotalProfit()));
        bill.append(String.format("\nBill Time: %s\n", billTime));
        return bill.toString();
    }

    public void saveBillToFile() {
        File file = new File("file:src/main/resources/Bills/bill" + billID + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(printBill());
            System.out.println("Bill saved to " + file);
        } catch (IOException e) {
            System.err.println("An error occurred while saving the bill: " + e.getMessage());
        }
    }
}
