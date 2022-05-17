package de.malik.climbingarchive.listeners;

import android.view.View;

import de.malik.climbingarchive.utils.RecyclerAdapterData;
import de.malik.climbingarchive.utils.ViewHolderData;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class OnLongClickViewHolder implements View.OnLongClickListener {

    private ViewHolderData mHolder;
    private RouteRecord mRec;
    private RecyclerAdapterData mAdapter;
    private DataManager mDataManager;

    public OnLongClickViewHolder(ViewHolderData holder, RouteRecord rec, RecyclerAdapterData adapter, DataManager dataManager) {
        mHolder = holder;
        mRec = rec;
        mAdapter = adapter;
        mDataManager = dataManager;
    }

    @Override
    public boolean onLongClick(View view) {
        mRec.setMarked(!mRec.isMarked());
        if (mRec.isMarked()) mHolder.getMarked().setVisibility(View.VISIBLE);
        else mHolder.getMarked().setVisibility(View.GONE);
        mDataManager.saveData();
        mAdapter.notifyDataSetChanged();
        return false;
    }
}
