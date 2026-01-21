package DAO;

import Model.Bill;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

import static Main.Launcher.PATH;

public class BillDAO {
    private static final File BILL_FILE = new File(PATH + File.separator + "data" + File.separator + "bills.dat");
    private static final ObservableList<Bill> bills = FXCollections.observableArrayList();
    private final Logger logger = Logger.getLogger(getClass().getName());

    public ObservableList<Bill> getBills() {
        if (bills.isEmpty()) {
            loadBills();
        }
        return bills;
    }

    public ObservableList<Bill> getBillsByDate(LocalDate dateFrom, LocalDate dateTo) {
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
        LocalDateTime from = dateFrom.atStartOfDay();
        LocalDateTime to = dateTo.atStartOfDay();
        for (Bill bill : getBills()) {
            if(bill.getBillTime().isAfter(from) && bill.getBillTime().isBefore(to))
                filteredBills.add(bill);
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
            }
        }
        return filteredBills;
    }


    public void loadBills() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(BILL_FILE))) {
            while (input.available() > 0) {
                bills.add((Bill) input.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Could not read bills!");
            logger.info(e.getMessage());
        }
    }

    public boolean createBill(Bill bill) {
        bills.add(bill);
        logger.info("Bill printed by: " + bill.getCashier().getFullName());
        return UpdateAll();
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
                logger.info("Updated bills!");
            }
            return true;
        } catch (IOException e) {
            logger.severe("Error updating bills!");
            return false;
        }
    }
}
