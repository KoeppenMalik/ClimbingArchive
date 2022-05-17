package de.malik.climbingarchive.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.utils.AppItemTouchHelper;
import de.malik.climbingarchive.utils.LifecycleManager;
import de.malik.climbingarchive.utils.OrderManager;
import de.malik.climbingarchive.utils.RecyclerAdapterData;
import de.malik.climbingarchive.utils.data.DataManager;
import de.malik.climbingarchive.utils.data.RouteRecord;

public class DataListFragment extends Fragment {

    private View mV;
    private ImageButton mButtonAdd;
    private RecyclerView mRecycler;
    private DataManager mDataManager;
    private LifecycleManager mLcm;
    private Spinner mSpinner;
    private Context mContext;

    public DataListFragment(DataManager dataManager, LifecycleManager lcm) {
        mDataManager = dataManager;
        mLcm = lcm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.list_fragment_layout, container, false);
        mContext = container.getContext();
        mLcm.updateTotalRoutes(mDataManager.getData().size());
        mDataManager.updateUsedLocations();
        manageUi();
        return mV;
    }

    private void manageUi() {
        createComponents();
        mRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        RecyclerAdapterData adapter = new RecyclerAdapterData(mDataManager, mLcm);
        mRecycler.setAdapter(adapter);
        ItemTouchHelper ith = new ItemTouchHelper(new AppItemTouchHelper(0, ItemTouchHelper.LEFT, mDataManager, adapter, mLcm));
        ith.attachToRecyclerView(mRecycler);
        manageSpinner();
        setListeners();
    }

    private void createComponents() {
        mButtonAdd = mV.findViewById(R.id.buttonAdd);
        mRecycler = mV.findViewById(R.id.recycler);
        mSpinner = mV.findViewById(R.id.spinnerOrder);
    }

    private void setListeners() {
        mButtonAdd.setOnClickListener((v) -> {
            RouteRecord rec = RouteRecord.getNewInstance(mDataManager);
            mDataManager.getData().add(rec);
            mLcm.replace(new NewWayFragment(mDataManager, mLcm, rec));
        });
    }

    private void manageSpinner() {
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, OrderManager.ORDER_CAPTIONS);
        mSpinner.setAdapter(arrAdapter);
        mSpinner.setOnItemSelectedListener(onSelected);
    }

    private AdapterView.OnItemSelectedListener onSelected = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mDataManager.setData(OrderManager.orderData(mDataManager, adapterView.getSelectedItemPosition()));
            mDataManager.saveData();
            mRecycler.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    };
}
