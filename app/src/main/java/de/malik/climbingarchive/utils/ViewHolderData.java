package de.malik.climbingarchive.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.malik.climbingarchive.R;

public class ViewHolderData extends RecyclerView.ViewHolder {

    private TextView summitName, wayName, difficulty, date, leadClimbed;
    private ImageView marked;

    public ViewHolderData(@NonNull View itemView) {
        super(itemView);
        setIsRecyclable(false);
        summitName = itemView.findViewById(R.id.tvSummitName);
        wayName = itemView.findViewById(R.id.tvWayName);
        difficulty = itemView.findViewById(R.id.tvDifficulty);
        date = itemView.findViewById(R.id.tvDate);
        marked = itemView.findViewById(R.id.ivMarked);
        leadClimbed = itemView.findViewById(R.id.tvLeadClimbed);
    }

    public TextView getSummitName() {
        return summitName;
    }

    public TextView getWayName() {
        return wayName;
    }

    public TextView getDifficulty() {
        return difficulty;
    }

    public TextView getDate() {
        return date;
    }

    public ImageView getMarked() {
        return marked;
    }

    public TextView getLeadClimbed() {
        return leadClimbed;
    }
}
