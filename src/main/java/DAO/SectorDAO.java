package DAO;


import Model.Items.Item;
import Model.Items.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;


public class SectorDAO {
    private static final File SECTOR_FILE = new File("src/main/resources/data/sectors.dat");
    private static final ObservableList<Sector> sectors = FXCollections.observableArrayList();

    public ObservableList<Sector> getSectors() {
        if (sectors.isEmpty()) {
            loadSectors();
        }
        return sectors;
    }


    public ObservableList<Sector> getSectorsByName(ArrayList<String> sectorNames) {
        ObservableList<Sector> filteredSectors = FXCollections.observableArrayList();
        for (Sector sector : getSectors()) {
            if(sectorNames.contains(sector.getSectorName()))
                filteredSectors.add(sector);
        }
        return filteredSectors;
    }

    public Sector getSectorByName(String sectorName) {
        for (Sector sector : getSectors()) {
            if(sector.getSectorName().equals(sectorName))
                return sector;
        }
        return null;
    }


    public void loadSectors() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(SECTOR_FILE))) {
            while (true) {
                sectors.add((Sector) input.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createSector(Sector sector) {
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

    public boolean deleteSector(Sector sector) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            for(Sector s : sectors) {
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



    public boolean UpdateAll() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            for (Sector s : sectors) {
                output.writeObject(s);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public ObservableList<String> getSectorNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Sector s : getSectors()) {
            names.add(s.getSectorName());
        }
        return names;
    }

    public ObservableList<Item> getAllItems() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        for(Sector s : getSectors()) {
            items.addAll(s.getItems());
        }
        return items;
    }
}
