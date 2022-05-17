package de.malik.climbingarchive.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.listeners.OnClickAddPhoto;
import de.malik.climbingarchive.listeners.OnClickSelectLocation;
import de.malik.climbingarchive.utils.LifecycleManager;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class NewWayFragment extends Fragment {

    private View mV;

    private DataManager mDataManager;
    private LifecycleManager mLcm;

    private EditText mTvSummitName, mTvWayName, mTvDifficulty, mTvComment, mTvDate, mTvLocation;
    private Button mButtonCancel, mButtonFinish;
    private Switch mSwitchLeadClimbed;
    private ImageButton mButtonLocation, mButtonAddPhoto;
    private ImageView mIvPhoto;

    private RouteRecord mRec;
    private TextView[] tvs;

    public NewWayFragment(DataManager dataManager, LifecycleManager lcm, RouteRecord record) {
        mDataManager = dataManager;
        mLcm = lcm;
        mRec = record;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.new_way_fragment_layout, container, false);
        manageUi();
        return mV;
    }

    private void manageUi() {
        createComponents();
        tvs = new TextView[] {
                mTvSummitName, mTvWayName, mTvDifficulty, mTvComment, mTvDate, mTvLocation
        };
        mTvSummitName.setText(mRec.getSummitName());
        mTvWayName.setText(mRec.getWayName());
        mTvDifficulty.setText(mRec.getDifficulty());
        mTvComment.setText(mRec.getComment());
        mTvDate.setText(mRec.getDate());
        mTvLocation.setText(mRec.getLocation());
        mSwitchLeadClimbed.setChecked(mRec.isLeadClimbed());
        if (mRec.getImageBitmap() != null)
            mIvPhoto.setImageBitmap(mRec.getImageBitmap());
        setListeners();
    }

    private void createComponents() {
        mTvSummitName = mV.findViewById(R.id.tvSummitName);
        mTvWayName = mV.findViewById(R.id.tvWayName);
        mTvDifficulty = mV.findViewById(R.id.tvDifficulty);
        mTvComment = mV.findViewById(R.id.tvComment);
        mTvDate = mV.findViewById(R.id.tvDate);
        mTvLocation = mV.findViewById(R.id.tvLocation);
        mButtonCancel = mV.findViewById(R.id.buttonCancel);
        mButtonFinish = mV.findViewById(R.id.buttonFinish);
        mSwitchLeadClimbed = mV.findViewById(R.id.switchLeadClimbed);
        mButtonLocation = mV.findViewById(R.id.buttonSelectLocation);
        mButtonAddPhoto = mV.findViewById(R.id.buttonAddPhoto);
        mIvPhoto = mV.findViewById(R.id.ivPhoto);
    }

    private void setListeners() {
        mButtonFinish.setOnClickListener(onClickFinish);
        mButtonCancel.setOnClickListener((v) -> {
            mLcm.replace(new DataListFragment(mDataManager, mLcm));
        });
        mButtonLocation.setOnClickListener(new OnClickSelectLocation(mDataManager, mTvLocation));
        mButtonAddPhoto.setOnClickListener(new OnClickAddPhoto(mLcm, mRec));
    }

    private View.OnClickListener onClickFinish = (v) -> {
        manageTvInput();
        mRec.setSummitName(mTvSummitName.getText().toString());
        mRec.setWayName(mTvWayName.getText().toString());
        mRec.setDifficulty(mTvDifficulty.getText().toString());
        mRec.setComment(mTvComment.getText().toString());
        mRec.setDate(mTvDate.getText().toString());
        mRec.setLocation(mTvLocation.getText().toString());
        mRec.setLeadClimbed(mSwitchLeadClimbed.isChecked());
        mDataManager.saveData();
        mLcm.replace(new DataListFragment(mDataManager, mLcm));
    };

    private void manageTvInput() {
        for (TextView tv : tvs)
            if (tv.getText().toString().equals(""))
                tv.setText("-");
    }
}
