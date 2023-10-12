package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    Button btninsert, btnfetch;

    EditText medname,medDate;

    Spinner spinner;
    TextView medTextView;

    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = findViewById(R.id.aswitch);
        btnfetch=findViewById(R.id.btnfetch);
        btninsert=findViewById(R.id.btninsert);
        medname = findViewById(R.id.edtmedname);
        medDate=findViewById(R.id.edtdate);
        spinner = findViewById(R.id.spinner);
        medTextView = findViewById(R.id.medTextView);

        dbhelper = new DatabaseHelper(this);

        btnfetch.setVisibility(View.INVISIBLE);



        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    btnfetch.setVisibility(View.INVISIBLE);
                    btninsert.setVisibility(View.VISIBLE);
                    medname.setVisibility(View.VISIBLE);
                    medTextView.setVisibility(View.VISIBLE);

                }
                else{
                    btnfetch.setVisibility(View.VISIBLE);
                    btninsert.setVisibility(View.INVISIBLE);
                    medname.setVisibility(View.INVISIBLE);
                    medTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medname.getText().toString().trim();
                String date = medDate.getText().toString().trim();
                String time = spinner.getSelectedItem().toString().trim();

                boolean insert = dbhelper.insertMedicine(name,date,time);
                if(insert){
                    Toast.makeText(MainActivity.this, "medicine Saved", Toast.LENGTH_SHORT).show();
                    medname.setText("");
                    medDate.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnfetch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String date = medDate.getText().toString().trim();
                String time = spinner.getSelectedItem().toString().trim();
                String med = "";
               Cursor c = dbhelper.FetchMedicine(date,time);
               if(c.moveToFirst()){
                   do{
                      med = med + String.valueOf(c.getString(c.getColumnIndex("Medicine_name")));
                       med = med + "\n";
                   }
                   while(c.moveToNext());
                   Toast.makeText(MainActivity.this, med, Toast.LENGTH_SHORT).show();

               }
               else{
                   Toast.makeText(MainActivity.this, "no entries found", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
}