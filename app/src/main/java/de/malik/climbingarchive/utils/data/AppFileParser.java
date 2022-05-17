package de.malik.climbingarchive.utils.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;

import de.malik.climbingarchive.utils.AppFileManager;

public class AppFileParser {

    public static ArrayList<RouteRecord> parseData(ArrayList<String> lines) {
        ArrayList<RouteRecord> data = new ArrayList<>();
        if (lines.size() == 0) return new ArrayList<>();
        for (String line : lines) {
            String[] cols = line.split(AppFileManager.SPLIT_REGEX);
            data.add(new RouteRecord(Long.parseLong(cols[0]), cols[1], cols[2], cols[3], cols[4], cols[5], cols[6], Boolean.parseBoolean(cols[7]), Boolean.parseBoolean(cols[8])));
        }
        File[] allPhotos = AppFileManager.getFolder(AppFileManager.PHOTO_FOLDER_NAME).listFiles();
        if (allPhotos != null) {
            for (RouteRecord rec : data) {
                for (File file : allPhotos) {
                    long id = Long.parseLong(file.getName().split(AppFileManager.FILE_SEPARATOR)[0]);
                    if (rec.getId() == id) {
                        Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                        rec.setImageBitmap(bm);
                        break;
                    }
                }
            }
        }
        return data;
    }
}
