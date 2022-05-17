package de.malik.climbingarchive.utils;

import java.io.File;
import java.io.IOException;

import de.malik.climbingarchive.utils.data.RouteRecord;
import de.malik.mylibrary.managers.FileManager;

public class AppFileManager {

    public static final String FOLDER_NAME = "ca_data";
    public static final String PHOTO_FOLDER_NAME = "ca_photos";
    public static final String FILE_NAME = "ca_data.csv";

    public static final String FILE_SEPARATOR = "_";
    public static final String SPLIT_REGEX = ";";

    public AppFileManager(String folderPath) {
        createFiles(folderPath);
    }

    private void createFiles(String folderPath) {
        FileManager.createFolder(FOLDER_NAME, folderPath);
        File photosFolder = new File(FileManager.CREATED_FOLDERS.get(FOLDER_NAME), PHOTO_FOLDER_NAME);
        if (!photosFolder.exists())
            photosFolder.mkdir();
        FileManager.CREATED_FOLDERS.put(PHOTO_FOLDER_NAME, photosFolder);
        try {
            FileManager.createFile(FILE_NAME, FileManager.CREATED_FOLDERS.get(FOLDER_NAME));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static File createImageFile(String name) {
        return new File(getFolder(PHOTO_FOLDER_NAME), name + "_img.jpg");
    }

    public static File deletePhoto(RouteRecord rec) {
        File[] files = getFolder(PHOTO_FOLDER_NAME).listFiles();
        File deletedFile = null;
        if (files != null) {
            for (File file : files) {
                if (file.getName().split(FILE_SEPARATOR)[0].equals(rec.getId() + "")) {
                    deletedFile = file;
                    file.delete();
                }
            }
        }
        return deletedFile;
    }

    public static File getFolder(String folderName) {
        return FileManager.CREATED_FOLDERS.get(folderName);
    }
}
