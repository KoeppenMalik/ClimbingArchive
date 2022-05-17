package de.malik.climbingarchive.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.listeners.OnClickAddPhoto;
import de.malik.climbingarchive.ui.fragments.DataListFragment;
import de.malik.climbingarchive.utils.AppFileManager;
import de.malik.climbingarchive.utils.LifecycleManager;
import de.malik.climbingarchive.utils.OrderManager;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.PermissionsManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class MainActivity extends AppCompatActivity {

    public static final int CONTAINER = R.id.container_layout;

    public static final String FOLDER_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();

    private AppFileManager mFileManager;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OrderManager.createData();
        PermissionsManager pm = new PermissionsManager(this);
        mFileManager = new AppFileManager(FOLDER_PATH);
        mDataManager = new DataManager();
        LifecycleManager lcm = new LifecycleManager(this);
        getSupportActionBar().setTitle(R.string.app_caption);
        pm.managePermissions();
        lcm.replace(new DataListFragment(mDataManager, lcm));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OnClickAddPhoto.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bm = (Bitmap) extras.get("data");
        }
    }
}