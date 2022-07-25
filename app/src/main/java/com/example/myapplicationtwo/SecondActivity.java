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
    ArrayList<Double> workerNames = new ArrayList<>();
    ArrayList<Double> workerTime = new ArrayList<>();
    Button Calc;
    EditText allTips;
    ArrayList<Integer> TipsPerWorkerArr = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Calc tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allowance = (TextView)findViewById(R.id.allowanceTextView);
        totalmin = (TextView)findViewById(R.id.totalMinusAllw);
        perHour = (TextView)findViewById(R.id.perHour);
        finalTxt = (TextView)findViewById(R.id.finalTxt);
        allTips=(EditText)findViewById(R.id.allTips);
        totlhours = (TextView)findViewById(R.id.totlhours);

        Calc=(Button)findViewById(R.id.buttonCalc);
        Intent intent = getIntent();
        Double str = Double.valueOf(intent.getStringExtra("message_key"));
        workerNames = (ArrayList<Double>) getIntent().getSerializableExtra("NameListExtra");
        workerTime = (ArrayList<Double>) getIntent().getSerializableExtra("TimeListExtra");

        allowance.setVisibility(View.GONE);
        totalmin.setVisibility(View.GONE);
        perHour.setVisibility(View.GONE);
        finalTxt.setVisibility(View.GONE);
        totlhours.setVisibility(View.GONE);
        totlhours.setText("סהכ שעות: " + str);
        Calc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                allowance.setVisibility(View.VISIBLE);
                totalmin.setVisibility(View.VISIBLE);
                perHour.setVisibility(View.VISIBLE);
                finalTxt.setVisibility(View.VISIBLE);
                totlhours.setVisibility(View.VISIBLE);

                Double getInput= Double.valueOf(allTips.getText().toString());
                int all = (int) (str*6);
                int total = (int) (getInput-all);
                allowance.setText("סך הכל הפרשה: " + all + "₪");
                totalmin.setText("טיפ ללא הפרשה: " + total + "₪");
                int fatalFish = (int) (total/str);
                perHour.setText("סך הכל טיפ לשעה: " + fatalFish + "₪");
                calcPerTips(workerTime,fatalFish,TipsPerWorkerArr);
//                tipsPerWorker.setText(TipsPerWorkerArr.toString());
                finalTxt.setText(gaga());
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void calcPerTips(ArrayList<Double> workerTime,int fatalFish,ArrayList<Integer> TipsPerWorkerArr) {
            for (Double aDouble : workerTime) {
                TipsPerWorkerArr.add((int) (fatalFish*aDouble));
            }
    }
    public String gaga() {
        StringBuffer str = new StringBuffer("חלוקת הטיפים(שם/שעות/כסף):" + "\n");
        for(int i=0; i<TipsPerWorkerArr.size(); i++) {
            str.append((i+1)+") "+ workerNames.get(i)+", " +workerTime.get(i)+", "  +TipsPerWorkerArr.get(i) + "₪" + "\n");
        }
        return str.toString();
    }
}