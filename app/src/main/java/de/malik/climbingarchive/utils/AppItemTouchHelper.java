package de.malik.climbingarchive.utils;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;
import de.malik.mylibrary.managers.FileManager;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AppItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private DataManager mDataManager;
    private RecyclerAdapterData mAdapter;
    private LifecycleManager mLcm;

    public AppItemTouchHelper(int dragDirs, int swipeDirs, DataManager dataManager, RecyclerAdapterData adapter, LifecycleManager lcm) {
        super(dragDirs, swipeDirs);
        mDataManager = dataManager;
        mAdapter = adapter;
        mLcm = lcm;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        RouteRecord rec = mDataManager.getData().get(viewHolder.getAdapterPosition());
        File deletedPhoto = null;
        if (direction == ItemTouchHelper.LEFT) {
            mDataManager.getData().remove(rec);
            mLcm.updateTotalRoutes(mDataManager.getData().size());
            deletedPhoto = AppFileManager.deletePhoto(rec);
            mDataManager.saveData();
            mAdapter.notifyDataSetChanged();
            final File finalDeletedPhoto = deletedPhoto;
            Snackbar.make(viewHolder.itemView, "Weg gelöscht", Snackbar.LENGTH_LONG)
                    .setAction("Rückgängig machen", (v) -> {
                        long id = mDataManager.getNextId();
                        RouteRecord deleted = new RouteRecord(id, rec.getSummitName(), rec.getWayName(), rec.getDifficulty(), rec.getDate(), rec.getLocation(), rec.getComment(), rec.isLeadClimbed(), rec.isMarked());
                        File file = new File(finalDeletedPhoto.getParent(), deleted.getId() + "_img.jpg");
                        finalDeletedPhoto.renameTo(file);
                        try {
                            finalDeletedPhoto.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mDataManager.getData().add(deleted);
                        mLcm.updateTotalRoutes(mDataManager.getData().size());
                        mDataManager.saveData();
                        mAdapter.notifyDataSetChanged();
                    }).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(Color.parseColor("#D30918"))
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .create()
                .decorate();
    }
}
