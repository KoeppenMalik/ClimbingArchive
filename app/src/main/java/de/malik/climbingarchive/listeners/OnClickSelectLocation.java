package de.malik.climbingarchive.listeners;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import de.malik.climbingarchive.R;
import de.malik.climbingarchive.utils.data.DataManager;

public class OnClickSelectLocation implements View.OnClickListener {

    private DataManager mDataManager;
    private EditText mTarget;

    public OnClickSelectLocation(DataManager dataManager, EditText target) {
        mDataManager = dataManager;
        mTarget = target;
    }

    @Override
    public void onClick(View v) {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.select_location_dialog_layout);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), R.layout.spinner_item, mDataManager.getExistingLocations());
        Spinner spinner = dialog.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        Button buttonFinish = dialog.findViewById(R.id.buttonFinish),
                buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonFinish.setOnClickListener((view) -> {
            mTarget.setText((String) spinner.getSelectedItem());
            dialog.dismiss();
        });
        buttonCancel.setOnClickListener((view) -> {
            dialog.dismiss();
        });
        dialog.create();
        dialog.show();
    }
}
