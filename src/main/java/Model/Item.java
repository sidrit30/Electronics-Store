package Model;

import java.util.Date;

public class Item {
    private static int nrItems;
    private final String itemId;
    private final String itemName;
    private final double sellingPrice;
    private double purchasePrice;
    //stock
    private int quantity;
    private String supplier;
    private String itemDescription;
    private String itemCategory;
    private final Date purchaseDate;

    public Item(String itemName, double salePrice, double purchasePrice, int quantity, String supplier, String itemDescription, String itemCategory) {
        this.itemId = String.format("%03d", ++nrItems);
        this.itemName = itemName;
        this.sellingPrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.supplier = supplier;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.purchaseDate = new Date();
    }

    public static int getNrItems() {
        return nrItems;
    }

    public String getItemId() {
        return itemId;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public double getProfit() {
        return sellingPrice - purchasePrice;
    }
}
