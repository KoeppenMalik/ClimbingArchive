package de.malik.climbingarchive.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.listeners.OnLongClickViewHolder;
import de.malik.climbingarchive.ui.fragments.NewWayFragment;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class RecyclerAdapterData extends RecyclerView.Adapter<ViewHolderData> {

    private View mV;
    private DataManager mDataManager;
    private LifecycleManager mLcm;

    public RecyclerAdapterData(DataManager dataManager, LifecycleManager lcm) {
        mDataManager = dataManager;
        mLcm = lcm;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mV = inflater.inflate(R.layout.view_holder_data_layout, parent, false);
        return new ViewHolderData(mV);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        RouteRecord rec = mDataManager.getData().get(position);
        String climbingType;
        holder.getSummitName().setText(rec.getSummitName());
        holder.getWayName().setText(rec.getWayName());
        holder.getDifficulty().setText(rec.getDifficulty() + "");
        holder.getDate().setText(rec.getDate());
        if (rec.isLeadClimbed()) climbingType = "V";
        else climbingType = "N";
        holder.getLeadClimbed().setText("(" + climbingType + ")");
        if (rec.isMarked()) holder.getMarked().setVisibility(View.VISIBLE);
        else holder.getMarked().setVisibility(View.GONE);
        mV.setOnLongClickListener(new OnLongClickViewHolder(holder, rec, this, mDataManager));
        mV.setOnClickListener((v) -> {
            mLcm.replace(new NewWayFragment(mDataManager, mLcm, rec));
        });
    }

    @Override
    public int getItemCount() {
        return mDataManager.getData().size();
    }
}
