package Model.Items;

import Model.UniqueIDGenerator;
import javafx.beans.property.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234L;
    //sth to look at in the end
    //private ImageView imageView;

    private final String itemID;
    private final String itemName;
    private final String itemCategory;
    private final String sectorName;

    private final LocalDateTime purchaseDate;
    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private transient DoubleProperty sellingPrice;
    private transient DoubleProperty purchasePrice;
    private transient IntegerProperty quantity;
    private transient StringProperty supplier;
    private transient StringProperty itemDescription;


    public Item(String itemName, String itemCategory, double salePrice, double purchasePrice, int quantity, String supplier, String itemDescription, String sectorName) {
        this.sectorName = sectorName;
        this.itemID = UniqueIDGenerator.getUniqueId();
        this.itemName = itemName;
        this.purchaseDate = LocalDateTime.now();
        this.itemCategory = itemCategory;

        this.sellingPrice = new SimpleDoubleProperty(salePrice);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.supplier = new SimpleStringProperty(supplier);
        this.itemDescription = new SimpleStringProperty(itemDescription);
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getSectorName() {
        return sectorName;
    }

    public double getSellingPrice() {
        return sellingPrice.get();
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice.set(sellingPrice);
    }

    public String getPurchaseDate() {
        return purchaseDate.format(formatter);
    }

    public double getPurchasePrice() {
        return purchasePrice.get();
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public String getItemDescription() {
        return itemDescription.get();
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription.set(itemDescription);
    }




    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(sellingPrice.getValue());
        out.writeDouble(purchasePrice.getValue());
        out.writeInt(quantity.getValue());
        out.writeUTF(supplier.getValueSafe());
        out.writeUTF(itemDescription.getValueSafe());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        sellingPrice = new SimpleDoubleProperty(in.readDouble());
        purchasePrice = new SimpleDoubleProperty(in.readDouble());
        quantity = new SimpleIntegerProperty(in.readInt());
        supplier = new SimpleStringProperty(in.readUTF());
        itemDescription = new SimpleStringProperty(in.readUTF());
    }

}
