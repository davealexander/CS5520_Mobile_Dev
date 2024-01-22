package edu.northeastern.numad23su_davidcenteno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinkCollector extends AppCompatActivity implements CardListener {

    FloatingActionButton addBtn;
    RecyclerView linkRecyclerView;

    Uri uri;

    static ArrayList<LinkModel> linkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        addBtn = findViewById(R.id.addLinkButton);

        addBtn.setOnClickListener(v -> linkEntryActivity());
        linkList = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        linkList = LinkEntry.getLinksList();
        linkRecyclerView = findViewById(R.id.linkRecyclerView);
        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkRecyclerView.setAdapter(new LinkRecyclerAdapter(linkList, this, this));
    }

    public void linkEntryActivity(){
        Intent linkEntryIntent = new Intent(this, LinkEntry.class);
        startActivity(linkEntryIntent);
    }

    public static ArrayList<LinkModel> getCurrentList(){
        return linkList;
    }

    @Override
    public void cardSelected(LinkModel linkModel) {
        uri = Uri.parse(linkModel.getLink());
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}