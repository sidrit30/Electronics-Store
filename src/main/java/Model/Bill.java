package Model;

import Model.Exceptions.InsufficientStockException;
import Model.Items.Item;
import Model.Users.Employee;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bill {
    private final String billID;
    private final ArrayList<Item> itemList;
    private final ArrayList<Integer> quantities;
    private final Employee cashier;
    private final LocalDateTime billTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private double profit;

    public Bill(Employee cashier) {
        this.cashier = cashier;
        this.billTime = LocalDateTime.now();
        billID = UniqueIDGenerator.getUniqueId();
        this.itemList = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public String getBillID() {
        return billID;
    }

    public Employee getCashier() {
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

    private double calculateCost() {
       double cost = 0;
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            cost += item.getPurchasePrice() * quantities.get(i);
        }
        return cost;
    }

    private double calculateRevenue() {
        double revenue = 0;
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            revenue += item.getSellingPrice() * quantities.get(i);
        }
        return revenue;
    }

    private double calculateProfit() {
        return calculateRevenue() - calculateCost();
    }

    public String printBill() {
        StringBuilder bill = new StringBuilder();
        bill.append("Bill Details:\n");
        bill.append("--------------------------------------------------\n");
        bill.append("Bill Date: ").append(billTime.format(formatter)).append("\n");
        bill.append("Cashier: ").append(cashier.getFullName()).append("\n");
        bill.append("--------------------------------------------------\n");
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int quantity = quantities.get(i);
            bill.append(String.format("Item: %s\n Quantity: %d\n Item Price: %.2f\n Total: %.2f\n",
                    item.getItemName(), quantity, item.getSellingPrice(), item.getSellingPrice() * quantity));
        }
        bill.append("--------------------------------------------------\n");
        bill.append(String.format("Total: %.2f\n", calculateRevenue()));
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
