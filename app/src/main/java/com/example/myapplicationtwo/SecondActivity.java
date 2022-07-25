package com.example.myapplicationtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView allowance;
    TextView totalmin;
    TextView perHour;
    TextView finalTxt;
    TextView totlhours;
    ArrayList<Double> workerNames = new ArrayList<>();   // array of the names workers from the first activity
    ArrayList<Double> workerTime = new ArrayList<>();    // array of the time shift workers from the first activity
    Button buttonCalc;                                  // calc button for calculations
    EditText allTipsInput;                              // enter the total tips in shift
    ArrayList<Integer> TipsPerWorkerArr = new ArrayList<Integer>(); // new array that save the how much money the worker made in shift
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Calc tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allowance = (TextView)findViewById(R.id.allowanceTextView);     // sets and connect to the objects on the activity
        totalmin = (TextView)findViewById(R.id.totalMinusAllw);         // sets and connect to the objects on the activity
        perHour = (TextView)findViewById(R.id.perHour);                 // sets and connect to the objects on the activity
        finalTxt = (TextView)findViewById(R.id.finalTxt);               // sets and connect to the objects on the activity
        allTipsInput=(EditText)findViewById(R.id.allTipsInput);         // sets and connect to the objects on the activity
        totlhours = (TextView)findViewById(R.id.totlhours);             // sets and connect to the objects on the activity

        buttonCalc=(Button)findViewById(R.id.buttonCalc);               // sets and connect to the objects on the activity
        Intent intent = getIntent();
        Double totalHours = Double.valueOf(intent.getStringExtra("message_key"));   // getting the data from the first activity
        workerNames = (ArrayList<Double>) getIntent().getSerializableExtra("NameListExtra");     // getting the data from the first activity
        workerTime = (ArrayList<Double>) getIntent().getSerializableExtra("TimeListExtra");       // getting the data from the first activity

        allowance.setVisibility(View.GONE); // hiding the text field
        totalmin.setVisibility(View.GONE);  // hiding the text field
        perHour.setVisibility(View.GONE);   // hiding the text field
        finalTxt.setVisibility(View.GONE);  // hiding the text field
        totlhours.setVisibility(View.GONE); // hiding the text field
        totlhours.setText("סהכ שעות: " + totalHours); // updating the text field
        buttonCalc.setOnClickListener(new View.OnClickListener() { // push the button calc

            @Override
            public void onClick(View view) {
                allowance.setVisibility(View.VISIBLE); // visible the text field
                totalmin.setVisibility(View.VISIBLE);  // visible the text field
                perHour.setVisibility(View.VISIBLE);  // visible the text field
                finalTxt.setVisibility(View.VISIBLE);  // visible the text field
                totlhours.setVisibility(View.VISIBLE);  // visible the text field

                Double getInput= Double.valueOf(allTipsInput.getText().toString());
                int all = (int) (totalHours*6); // allowance for insurance
                int total = (int) (getInput-all);   // the tips without the allowance
                allowance.setText("סך הכל הפרשה: " + all + "₪"); // updating the text field
                totalmin.setText("טיפ ללא הפרשה: " + total + "₪"); // updating the text field
                int fatalFish = (int) (total/totalHours);   // calc how much money per hour
                perHour.setText("סך הכל טיפ לשעה: " + fatalFish + "₪"); // updating the text field
                calcPerTips(workerTime,fatalFish,TipsPerWorkerArr); // calc the money per worker
//                tipsPerWorker.setText(TipsPerWorkerArr.toString());
                finalTxt.setText(gaga()); // updating the text field
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void calcPerTips(ArrayList<Double> workerTime,int fatalFish,ArrayList<Integer> TipsPerWorkerArr) { //calc the money per worker and add to new array
            for (Double aDouble : workerTime) {
                TipsPerWorkerArr.add((int) (fatalFish*aDouble));
            }
    }
    public String gaga() {                                                      // toString to show the results
        StringBuffer str = new StringBuffer("חלוקת הטיפים(שם/שעות/כסף):" + "\n");
        for(int i=0; i<TipsPerWorkerArr.size(); i++) {
            str.append((i+1)+") "+ workerNames.get(i)+", " +workerTime.get(i)+", "  +TipsPerWorkerArr.get(i) + "₪" + "\n");
        }
        return str.toString();
    }
}