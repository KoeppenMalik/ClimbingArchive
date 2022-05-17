package de.malik.climbingarchive.utils.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import de.malik.climbingarchive.utils.AppFileManager;
import de.malik.mylibrary.managers.FileManager;

public class DataManager {

    private ArrayList<RouteRecord> data;
    private ArrayList<String> existingLocations = new ArrayList<>();

    public DataManager() {
        File file = FileManager.CREATED_FILES.get(AppFileManager.FILE_NAME);
        try {
            data = AppFileParser.parseData(FileManager.getReader().readLines(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUsedLocations() {
        for (RouteRecord rec : data)
            if (!existingLocations.contains(rec.getLocation()) && !rec.getLocation().equals("-"))
                existingLocations.add(rec.getLocation());
    }

    public void saveData() {
        try {
            FileManager.getPrinter().print(FileManager.CREATED_FILES.get(AppFileManager.FILE_NAME), false, getRecordsList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<String> getRecordsList() {
        ArrayList<String> records = new ArrayList<>();
        for (RouteRecord rec : data) {
            records.add(rec.getRecord());
        }
        return records;
    }

    public long getNextId() {
        ArrayList<Long> ids = new ArrayList<>();
        if (data.size() == 0) return 1;
        for (RouteRecord rec : data) {
            ids.add(rec.getId());
        }
        return Collections.max(ids) +1;
    }

    public ArrayList<String> getSpecificData(int id) {
        ArrayList<String> data = new ArrayList<>();
        for (RouteRecord rec : this.data) {
            data.add(rec.getRecord().split(AppFileManager.SPLIT_REGEX)[id]);
        }
        return data;
    }

    public ArrayList<RouteRecord> assignRecordToData(ArrayList<String> sorted, int id) {
        ArrayList<RouteRecord> assigned = new ArrayList<>();
        ArrayList<RouteRecord> data = new ArrayList<>(this.data);
        for (String s : sorted) {
            for (RouteRecord rec : data) {
                if (s.equals(rec.getRecord().split(AppFileManager.SPLIT_REGEX)[id]) && !assigned.contains(rec)) {
                    assigned.add(rec);
                }
            }
        }
        return assigned;
    }

    public ArrayList<RouteRecord> getData() {
        return data;
    }

    public ArrayList<String> getExistingLocations() {
        return existingLocations;
    }

    public void setData(ArrayList<RouteRecord> data) {
        this.data = data;
    }
}
