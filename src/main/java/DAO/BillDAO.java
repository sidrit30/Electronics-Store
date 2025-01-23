package DAO;

import Model.Bill;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

public class BillDAO {
    private static final File BILL_FILE = new File("src/main/resources/data/bills.dat");
    private static final ObservableList<Bill> bills = FXCollections.observableArrayList();

    public ObservableList<Bill> getBills() {
        if (bills.isEmpty()) {
            loadBills();
        }
        return bills;
    }

    public ObservableList<Bill> getBillsByDate(LocalDate dateFrom, LocalDate dateTo) {
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
        for (Bill bill : getBills()) {
            if(bill.getBillTime().getDayOfMonth() >= dateFrom.getDayOfMonth() && bill.getBillTime().getMonthValue() >=
                    dateFrom.getMonthValue() && bill.getBillTime().getYear() >= dateFrom.getYear()) {
                    if (bill.getBillTime().getDayOfMonth() <= dateFrom.getDayOfMonth() && bill.getBillTime().getMonthValue() <=
                            dateTo.getMonthValue() && bill.getBillTime().getYear() <= dateTo.getYear())
                        filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    public ObservableList<Bill> getBillsByEmployee(Employee employee) {
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
        for (Bill bill : getBills()) {
            if(Objects.equals(bill.getCashier().getId(), employee.getId())) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    public ObservableList<Bill> getBillsBySectors(ObservableList<String> sectorNames) {
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
        for (Bill bill : getBills()) {
            if(sectorNames.contains(bill.getCashier().getSectorName())) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    public ObservableList<Bill> getBillsBySector(String sectorName) {
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
        for (Bill bill : getBills()) {
            if(bill.getCashier().getSectorName().equals(sectorName)) {
                filteredBills.add(bill);
                System.out.println(bill.getCashier().getFullName());
            }
        }
        return filteredBills;
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
