package de.malik.climbingarchive.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import de.malik.climbingarchive.ui.MainActivity;

public class PermissionsManager {

    public static final int REQUEST_CODE_R = 1;
    public static final int REQUEST_CODE_BELOW_R = 2;

    public static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private MainActivity mActivity;

    public PermissionsManager(MainActivity activity) {
        mActivity = activity;
    }

    public void managePermissions() {
        if (!hasPermissions())
            requestPermissions();
    }

    private boolean hasPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            mActivity.startActivity(intent);
        }
        else ActivityCompat.requestPermissions(mActivity, PERMISSIONS, REQUEST_CODE_BELOW_R);
    }
}

