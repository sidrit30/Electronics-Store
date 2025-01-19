package DAO;

import Model.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class SectorDAO {
    private static final File SECTOR_FILE = new File("src/main/resources/data/sectors.dat");
    private static final ObservableList<Bill> sectors = FXCollections.observableArrayList();

    public ObservableList<Bill> getSectors() {
        if (sectors.isEmpty()) {
            loadSectors();
        }
        return sectors;
    }

    public void loadSectors() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(SECTOR_FILE))) {
            while (true) {
                sectors.add((Bill) input.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createSector(Bill sector) {
        try (FileOutputStream outputStream = new FileOutputStream(SECTOR_FILE, true)) {
            ObjectOutputStream writer;

            if(SECTOR_FILE.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else {
                writer = new ObjectOutputStream(outputStream);
            }

            writer.writeObject(sector);
            sectors.add(sector);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteSector(Bill sector) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            for(Bill s : sectors) {
                if(!s.equals(sector)) {
                    output.writeObject(s);
                }
            }
            sectors.remove(sector);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteList(List<Bill> list) {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            for (Bill s : sectors) {
                if(!list.contains(s))
                    output.writeObject(s);
            }
            sectors.remove(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean UpdateAll() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            for (Bill s : sectors) {
                output.writeObject(s);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
