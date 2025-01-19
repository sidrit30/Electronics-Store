package DAO;

import Model.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class BillDAO {
    private static final File BILL_FILE = new File("src/main/resources/data/bills.dat");
    private static final ObservableList<Bill> bills = FXCollections.observableArrayList();

    public ObservableList<Bill> getBills() {
        if (bills.isEmpty()) {
            loadBills();
        }
        return bills;
    }

    public void loadBills() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(BILL_FILE))) {
            while (true) {
                bills.add((Bill) input.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createBill(Bill bill) {
        try (FileOutputStream outputStream = new FileOutputStream(BILL_FILE, true)) {
            ObjectOutputStream writer;

            if(BILL_FILE.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else {
                writer = new ObjectOutputStream(outputStream);
            }

            writer.writeObject(bill);
            bills.add(bill);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteBill(Bill bill) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(BILL_FILE))) {
            for(Bill b : bills) {
                if(!b.equals(bill)) {
                    output.writeObject(b);
                }
            }
            bills.remove(bill);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteList(List<Bill> list) {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(BILL_FILE))) {
            for (Bill b : bills) {
                if(!list.contains(b))
                    output.writeObject(b);
            }
            bills.remove(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean UpdateAll() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(BILL_FILE))) {
            for (Bill b : bills) {
                output.writeObject(b);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
