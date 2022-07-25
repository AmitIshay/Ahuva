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

    Button save;                                                // save new worker
    Button next;                                                // moving to second screen
    ArrayList<String> addArrayWorkerName = new ArrayList<String>();  // array of names workers
    ArrayList<String> addArrayOrigTime = new ArrayList<String>();   // array of times in shift per worker
    ArrayList<Double> addArrayCalcTime = new ArrayList<Double>();   // array of times in shift per worker after calculation using a formula of the business place

    EditText WorkerNameInput; // worker name input
    EditText WorkerTimeInput; // worker time input
    ListView show;            // shows on screen the worker name
    ListView showOne;        // shows on screen the shift time worker
    ListView showTwo;       // shows on screen the shift time after using the formula
    Double sum=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Add Worker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WorkerNameInput=(EditText)findViewById(R.id.WorkerNameInput); // sets and connect to the objects on the activity
        WorkerTimeInput=(EditText)findViewById(R.id.WorkerTimeInput); // sets and connect to the objects on the activity
        TextView sumAll = findViewById(R.id.sumHours);                // sets and connect to the objects on the activity
        TextView totlHoursUpdating = findViewById(R.id.totlHoursUpdating); // sets and connect to the objects on the activity
        show=(ListView)findViewById(R.id.listView);                   // sets and connect to the objects on the activity
        showOne=(ListView)findViewById(R.id.listViewOne);             // sets and connect to the objects on the activity
        showTwo=(ListView)findViewById(R.id.listViewTwo);             // sets and connect to the objects on the activity
        save=(Button)findViewById(R.id.buttonAdd);                    // sets and connect to the objects on the activity
        next=(Button)findViewById(R.id.nextButton);                   // sets and connect to the objects on the activity
        save.setOnClickListener(new View.OnClickListener() {           // after push the button save its saves the inputs that entered by the user

            @Override
            public void onClick(View v) {
                String getInput=WorkerNameInput.getText().toString();
                String getInputOne=WorkerTimeInput.getText().toString();

                if(addArrayWorkerName.contains(getInput)) {                                                     // if the name already used
                    Toast.makeText(getBaseContext(), "Item already to the Array", Toast.LENGTH_LONG).show();
                }
                else if(getInput == null || getInput.trim().equals("") || getInputOne == null || getInputOne.trim().equals("")) {   // if the field is empty
                    Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    addArrayWorkerName.add(getInput);                                       // add the name of the worker
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayWorkerName);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.WorkerNameInput)).setText(" ");

                    addArrayOrigTime.add(getInputOne);                                      // add thr time of the shift
                    ArrayAdapter<String> adapterOne = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayOrigTime);
                    showOne.setAdapter(adapterOne);
                    ((EditText)findViewById(R.id.WorkerTimeInput)).setText(" ");

                    addArrayCalcTime.add(ExChange(getInputOne));                            // add the time of the shift after using the formula
                    ArrayAdapter<Double> adapterTwo = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, addArrayCalcTime);
                    showTwo.setAdapter(adapterTwo);

                    sumAll.setText( "סהכ שעות במשמרת: " );                                  // updating the total hours of all the worker
                    totlHoursUpdating.setText(new DecimalFormat("##.##").format(sumHours(ExChange(getInputOne))));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {       // after push the button next its saves the arrays and moving the data to the next screen
            @Override
            public void onClick(View v) {
                String sumfinsh = totlHoursUpdating.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("message_key", sumfinsh);
                intent.putExtra("NameListExtra",addArrayWorkerName);
                intent.putExtra("TimeListExtra",addArrayCalcTime);
                startActivity(intent);
            }
        });
    }

    public Double ExChange(String time) {                   // the formula to calc the time shift
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

    public Double sumHours(Double num) {        // calc the total hours in shift of all workers
        sum += num;
        return sum;
    }
}