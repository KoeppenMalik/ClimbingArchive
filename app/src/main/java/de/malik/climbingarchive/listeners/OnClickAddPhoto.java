package de.malik.climbingarchive.listeners;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.File;

import de.malik.climbingarchive.utils.AppFileManager;
import de.malik.climbingarchive.utils.LifecycleManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class OnClickAddPhoto implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private LifecycleManager mLcm;
    private RouteRecord mRec;

    public OnClickAddPhoto(LifecycleManager lcm, RouteRecord rec) {
        mLcm = lcm;
        mRec = rec;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = AppFileManager.createImageFile(mRec.getId() + "");
        Uri uri = FileProvider.getUriForFile(mLcm.getActivity(), "de.malik.climbingarchive.fileprovider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mLcm.getActivity().startActivity(intent);
    }
}
