package edu.northeastern.numad23su_davidcenteno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FindPrimeActivity extends AppCompatActivity {

    Button btnFindPrime, btnTerminate;
    TextView currentNum, latestNum;

    public int currentNumber, latestNumber;
    private volatile boolean stopThread = true;
    private volatile boolean resume = false;

    private Handler currentTextHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);

        btnFindPrime = findViewById(R.id.primeBtn);
        btnTerminate = findViewById(R.id.terminateBtn);
        currentNum = findViewById(R.id.currentNumValue);
        latestNum = findViewById(R.id.latestNumValue);

        if (savedInstanceState != null){
            latestNumber = savedInstanceState.getInt("latestNumber");
            currentNumber = savedInstanceState.getInt("currentNumber");
            currentNum.setText(savedInstanceState.getString("currentNumberString"));
            latestNum.setText(savedInstanceState.getString("latestNumberString"));
            resume = true;
        }
    }

    //findPrimeNumber returns a boolean based off a number that is prime.
    public boolean findPrimeNumber(int number) {
        if (number == 3){
            return true;
        }
        else if (number % 2 == 0){
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number); i += 2){
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }

    public void updatePrimeUI(int iterator, boolean primeNumber){
            currentTextHandler.post( () -> currentNum.setText(String.valueOf(iterator)));
            if (primeNumber){
                currentTextHandler.post( () -> latestNum.setText(String.valueOf(iterator)));
            }
        }

    public void primeButtonClicked(View view){
        //if process is resumed stop current thread.
        if(resume){
            stopThread = true;
        }
        //if process has not been resumed set currentNumber to 3;
        if(currentNumber != 3 && !resume){
            currentNumber = 3;
        }
        //Start process
        stopThread = false;
        PrimeThread primeThread = new PrimeThread();
        new Thread(primeThread).start();

    }

    public void terminateThread(View view){
        //If resume is not active stop thread.If resume active reset settings to pre-resume.
        if(!resume) {
            stopThread = true;
        }
        else{
            stopThread = true;
            resume = false;
            currentNumber = 3;
        }
    }

    //Creates a new thread when the find prime button is clicked.
    public class PrimeThread implements Runnable{
        @Override
        public void run() {
           if (currentNumber <= 100 && !stopThread) {
               updatePrimeUI(currentNumber, findPrimeNumber(currentNumber));
               Handler primeHandler = new Handler(Looper.getMainLooper());
               primeHandler.postDelayed(this, 500);
               currentNumber += 2;
           }
        }
    };

    public void exitAlert(){
        AlertDialog.Builder exitScreen = new AlertDialog.Builder(this);
        exitScreen.setTitle("Warning!");
        exitScreen.setMessage("A process is running are you sure you want to leave?");
        exitScreen.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopThread = true;
                onBackPressed();
            }
        });
        exitScreen.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        exitScreen.create().show();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("latestNumber", latestNumber);
        outState.putInt("currentNumber", currentNumber);
        outState.putString("latestNumberString", String.valueOf(latestNum.getText()));
        outState.putString("currentNumberString", String.valueOf(currentNum.getText()));
    }
    @Override
    public void onBackPressed() {
        if(!stopThread){
            exitAlert();
        } else {
            super.onBackPressed();
        }
    };
}
