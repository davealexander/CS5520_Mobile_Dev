package edu.northeastern.numad23su_davidcenteno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class ClickyClicky extends AppCompatActivity {

    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    Button btnE;
    Button btnF;
    String btnText;

    TextView pInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);

        btnA = findViewById(R.id.buttonA);
        btnB = findViewById(R.id.buttonB);
        btnC = findViewById(R.id.buttonC);
        btnD = findViewById(R.id.buttonD);
        btnE = findViewById(R.id.buttonE);
        btnF = findViewById(R.id.buttonF);
        pInput = findViewById(R.id.pressedInput);

        btnA.setOnClickListener(v -> setPressedInput(btnA));
        btnB.setOnClickListener(v -> setPressedInput(btnB));
        btnC.setOnClickListener(v -> setPressedInput(btnC));
        btnD.setOnClickListener(v -> setPressedInput(btnD));
        btnE.setOnClickListener(v -> setPressedInput(btnE));
        btnF.setOnClickListener(v -> setPressedInput(btnF));

    }

    public void setPressedInput(Button btn){
        btnText = btn.getText().toString();
        pInput.setText(btnText);
    }
}