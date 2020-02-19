package com.example.sigmaleave;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public abstract class ApplyForLeave extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Button apply;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Calendar myCalendar;
    private EditText starDateEditText;
    private EditText endDateEditText;
    private EditText reasonEditText;
    private TextView chunks,days;
    Employee employee;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;

    private Button cancelButton;
    private Button applyButton;
    String[] typeLeaves = { "Full Day","Half Day","Quarter Day"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_leave);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        starDateEditText = (EditText) findViewById(R.id.startDate);
        endDateEditText = (EditText) findViewById(R.id.endDate);
        reasonEditText = (EditText) findViewById(R.id.reason);
        applyButton = (Button) findViewById(R.id.apply);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        chunks=findViewById(R.id.chunksLeft);
        days=findViewById(R.id.leavesLeft);


        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeLeaves);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        final Leaves leaves=new Leaves();
        employee=new Employee();

        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Leave Requests");
        myCalendar = Calendar.getInstance();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reasonEditText.getText().toString().equals("")) {
                            reasonEditText.setError("Reason is required!");}
                        leaves.setStartDate(starDateEditText.getText().toString());
                        leaves.setEndDate(endDateEditText.getText().toString());
                        leaves.setReason(reasonEditText.getText().toString());

                        databaseReference.child("Leaves").setValue(15);
                        databaseReference.child("Leaves").setValue(30);

                    }
                });
            }
    });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),typeLeaves[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}