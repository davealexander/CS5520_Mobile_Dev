package edu.northeastern.numad23su_davidcenteno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkEntry extends AppCompatActivity {

    TextView titleText, linkText;
    ConstraintLayout layout;
    Button submitBtn, undoBtn;

    int currentPosition;
    static ArrayList<LinkModel> linksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_entry);

        titleText = findViewById(R.id.titleTextField);
        linkText = findViewById((R.id.linkTextField));
        layout = findViewById(R.id.constraintLayout);
        submitBtn = findViewById(R.id.linkSubmitButton);

        if(linksList == null) {
            linksList = new ArrayList<>();
        }
        else{
            linksList = LinkCollector.getCurrentList();
        }

        submitBtn.setOnClickListener(v -> onSubmit());
    }

    public void onSubmit(){
        String title = titleText.getText().toString();
        String link = linkText.getText().toString();

        if (title.length() > 0 && link.length() > 0){
            linksList.add(new LinkModel(title, link));
            currentPosition = linksList.size();
            Snackbar success = Snackbar.make(layout, "Link successfully created.", Snackbar.LENGTH_LONG).setAction("Undo:", v -> linksList.remove(currentPosition - 1));
            linkText.setText("");
            titleText.setText("");
            success.show();
        }
        else{
            Snackbar failure = Snackbar.make(layout, "Fields cannot be blank.",Snackbar.LENGTH_LONG);
            failure.show();
        }
    }

    public static ArrayList<LinkModel> getLinksList(){
        return linksList;
    }
}