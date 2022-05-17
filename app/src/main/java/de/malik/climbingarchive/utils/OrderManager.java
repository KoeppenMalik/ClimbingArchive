package de.malik.climbingarchive.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class OrderManager {

    public static final String[] ORDER_CAPTIONS = new String[] {
            "Datum", "Schwierigkeit", "Kletterart", "Markiert"
    };
    public static final int[] ORDER_IDS = new int[] {
            4, 3, 7, 8
    };

    public static final HashMap<String, Integer> DIFFICULTIES = new HashMap<>();

    public static void createData() {
        // difficulties
        DIFFICULTIES.put("I", 1);
        DIFFICULTIES.put("II", 2);
        DIFFICULTIES.put("III", 3);
        DIFFICULTIES.put("IV", 4);
        DIFFICULTIES.put("V", 5);
        DIFFICULTIES.put("VI", 6);
        DIFFICULTIES.put("VII", 7);
        DIFFICULTIES.put("VIII", 8);
        DIFFICULTIES.put("IX", 9);
    }

    public static ArrayList<RouteRecord> orderData(DataManager dataManager, int pos) {
        ArrayList<RouteRecord> result;
        ArrayList<String> data = dataManager.getSpecificData(ORDER_IDS[pos]);
        // date
        if (pos == 0) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            df.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
            data.sort((o1, o2) -> {
                try {
                    return df.parse(o2).compareTo(df.parse(o1));
                } catch (ParseException exception) {
                    exception.printStackTrace();
                }
                return 0;
            });
        }
        // difficulty
        else if (pos == 1) {
            ArrayList<String> containing = new ArrayList<>(), notContaining = new ArrayList<>();
            for (String s : data) {
                if (!s.equals("-"))
                    containing.add(s);
                else notContaining.add(s);
            }
            data.clear();
            containing.sort((o1, o2) -> DIFFICULTIES.get(o2).compareTo(DIFFICULTIES.get(o1)));
            data.addAll(containing);
            data.addAll(notContaining);
        }
        // climbing type (V/N)
        else if (pos == 2) {
            data.sort((o1, o2) -> o2.compareTo(o1));
        }
        // marked
        else {
            data.sort((o1, o2) -> Boolean.compare(Boolean.parseBoolean(o2), Boolean.parseBoolean(o1)));
        }
        result = dataManager.assignRecordToData(data, ORDER_IDS[pos]);
        return result;
    }
}
