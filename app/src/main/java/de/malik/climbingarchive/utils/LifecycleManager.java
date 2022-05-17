package de.malik.climbingarchive.utils;

import androidx.fragment.app.Fragment;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.ui.MainActivity;

public class LifecycleManager {

    private MainActivity mActivity;

    public LifecycleManager(MainActivity activity) {
        mActivity = activity;
    }

    public void replace(Fragment newFragment) {
        mActivity.getSupportFragmentManager().beginTransaction().replace(MainActivity.CONTAINER, newFragment).commit();
    }

    public void updateTotalRoutes(int count) {
        mActivity.getSupportActionBar().setTitle(mActivity.getString(R.string.app_caption) + " (Gesamt: " + count + ")");
    }

    public MainActivity getActivity() {
        return mActivity;
    }
}
