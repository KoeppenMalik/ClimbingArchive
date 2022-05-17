package de.malik.climbingarchive.utils.data;

import android.graphics.Bitmap;

import de.malik.climbingarchive.utils.AppFileManager;
import de.malik.mylibrary.managers.TimeManager;

public class RouteRecord {

    private long mId;
    private String mSummitName, mWayName, mComment, mDifficulty, mDate, mLocation;
    private boolean mLeadClimbed, mMarked;
    private Bitmap mImageBitmap = null;

    public RouteRecord(long id, String summitName, String wayName, String difficulty, String date, String location, String comment, boolean leadClimbed, boolean marked) {
        mId = id;
        mSummitName = summitName;
        mWayName = wayName;
        mDifficulty = difficulty;
        mComment = comment;
        mDate = date;
        mLocation = location;
        mLeadClimbed = leadClimbed;
        mMarked = marked;
    }

    public static RouteRecord getNewInstance(DataManager dataManager) {
        return new RouteRecord(dataManager.getNextId(), "-", "-", "-", TimeManager.currentDate(), "-", "-", false, false);
    }

    public String getRecord() {
        String sr = AppFileManager.SPLIT_REGEX;
        return mId+sr+mSummitName+sr+mWayName+sr+mDifficulty+sr+mDate+sr+mLocation+sr+mComment+sr+mLeadClimbed+sr+mMarked;
    }

    public long getId() {
        return mId;
    }

    public String getSummitName() {
        return mSummitName;
    }

    public String getWayName() {
        return mWayName;
    }

    public String getComment() {
        return mComment;
    }

    public String getDifficulty() {
        return mDifficulty;
    }

    public String getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setSummitName(String mSummitName) {
        this.mSummitName = mSummitName;
    }

    public void setWayName(String mWayName) {
        this.mWayName = mWayName;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public void setDifficulty(String mDifficulty) {
        this.mDifficulty = mDifficulty;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public boolean isLeadClimbed() {
        return mLeadClimbed;
    }

    public boolean isMarked() {
        return mMarked;
    }

    public void setLeadClimbed(boolean mLeadClimbed) {
        this.mLeadClimbed = mLeadClimbed;
    }

    public void setMarked(boolean mMarked) {
        this.mMarked = mMarked;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap mImageBitmap) {
        this.mImageBitmap = mImageBitmap;
    }
}
