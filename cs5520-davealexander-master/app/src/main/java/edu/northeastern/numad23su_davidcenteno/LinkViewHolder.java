package edu.northeastern.numad23su_davidcenteno;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView, linkView;
    public CardView cardView;

    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);
        this.titleView = itemView.findViewById(R.id.titleView);
        this.linkView = itemView.findViewById(R.id.linkView);
        this.cardView = itemView.findViewById(R.id.cardView);
    }
}