package Model.Items;

import Model.UniqueIDGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234L;
    private final String itemID;
    private final String itemName;
    private final double sellingPrice;
    private double purchasePrice;
    private int quantity;
    private String supplier;
    private String itemDescription;
    private final Date purchaseDate;

    public Item(String itemName, double salePrice, double purchasePrice, int quantity, String supplier, String itemDescription) {
        itemID = UniqueIDGenerator.getUniqueId();
        this.itemName = itemName;
        this.sellingPrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.supplier = supplier;
        this.itemDescription = itemDescription;
        this.purchaseDate = new Date();
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getProfit() {
        return sellingPrice - purchasePrice;
    }
}
