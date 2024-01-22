package edu.northeastern.numad23su_davidcenteno;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;

public class LinkRecyclerAdapter extends RecyclerView.Adapter<LinkViewHolder> {

    private ArrayList<LinkModel> links;
    private Context context;
    private CardListener cardListener;

    public LinkRecyclerAdapter(ArrayList <LinkModel> links, Context context, CardListener cardListener){
        this.links = links;
        this.context = context;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.link_collector_card, parent, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        holder.titleView.setText(links.get(position).getTitle());
        holder.linkView.setText(links.get(position).getLink());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardListener.cardSelected(links.get(position));
            }
        });

    }
    @Override
    public int getItemCount() {
        if(links == null){
            return 0;
        };
        return links.size();
    }
}
