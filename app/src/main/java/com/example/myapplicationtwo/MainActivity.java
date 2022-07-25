package com.example.myapplicationtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    Button save;
    Button next;
    ArrayList<String> addArrayWorkerName = new ArrayList<String>();
    ArrayList<String> addArrayOrigTime = new ArrayList<String>();
    ArrayList<Double> addArrayCalcTime = new ArrayList<Double>();

    EditText txt;
    EditText txtOne;
    ListView show;
    ListView showOne;
    ListView showTwo;
    Double sum=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Add Worker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt=(EditText)findViewById(R.id.WorkerNameInput);
        txtOne=(EditText)findViewById(R.id.txtTime);
        TextView sumAll = findViewById(R.id.sumHours);
        TextView unsum = findViewById(R.id.unView);
        show=(ListView)findViewById(R.id.listView);
        showOne=(ListView)findViewById(R.id.listViewOne);
        showTwo=(ListView)findViewById(R.id.listViewTwo);
        save=(Button)findViewById(R.id.buttonAdd);
        next=(Button)findViewById(R.id.nextButton);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getInput=txt.getText().toString();
                String getInputOne=txtOne.getText().toString();

                if(addArrayWorkerName.contains(getInput)) {
                    Toast.makeText(getBaseContext(), "Item already to the Array", Toast.LENGTH_LONG).show();
                }
                else if(getInput == null || getInput.trim().equals("") || getInputOne == null || getInputOne.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    addArrayWorkerName.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayWorkerName);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.WorkerNameInput)).setText(" ");

                    addArrayOrigTime.add(getInputOne);
                    ArrayAdapter<String> adapterOne = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayOrigTime);
                    showOne.setAdapter(adapterOne);
                    ((EditText)findViewById(R.id.txtTime)).setText(" ");

                    addArrayCalcTime.add(ExChange(getInputOne));
                    ArrayAdapter<Double> adapterTwo = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayCalcTime);
                    showTwo.setAdapter(adapterTwo);

                    sumAll.setText( "סהכ שעות במשמרת: " );
                    unsum.setText(new DecimalFormat("##.##").format(sumHours(ExChange(getInputOne))));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sumfinsh = unsum.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("message_key", sumfinsh);
                intent.putExtra("NameListExtra",addArrayWorkerName);
                intent.putExtra("TimeListExtra",addArrayCalcTime);
                startActivity(intent);
            }
        });
    }

    public Double ExChange(String time) {
            String Hour=time.split(":")[0];
            String Minute=time.split(":")[1];
            int hour=Integer.parseInt(Hour.trim());
            double minute=Integer.parseInt(Minute);
            if(minute < 2.5) {
                minute = 0.0;
            }
            else if(minute <= 7.5 && minute >= 2.5) {
                minute = 0.08;
            }
            else if(minute <= 12.5 && minute > 7.5) {
                minute = 0.17;
            }
            else if(minute <= 17.5 && minute > 12.5) {
                minute = 0.25;
            }
            else if(minute <= 22.5 && minute > 17.5) {
                minute = 0.33;
            }
            else if(minute <= 27.5 && minute > 22.5) {
                minute = 0.41;
            }
            else if(minute <= 32.5 && minute > 27.5) {
                minute = 0.50;
            }
            else if(minute <= 37.5 && minute > 32.5) {
                minute = 0.58;
            }
            else if(minute <= 42.5 && minute > 37.5) {
                minute = 0.67;
            }
            else if(minute <= 47.5 && minute > 42.5) {
                minute = 0.75;
            }
            else if(minute <= 52.5 && minute > 47.5) {
                minute = 0.83;
            }
            else if(minute <= 59 && minute >= 52.5) {
                minute = 0.91;
            }
            return Double.valueOf(new DecimalFormat("##.##").format(hour + minute));
    }

    public Double sumHours(Double num) {
        sum += num;
        return sum;
    }
}