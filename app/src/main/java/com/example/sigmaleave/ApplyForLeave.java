package com.example.sigmaleave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class ApplyForLeave extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Button apply;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Calendar myCalendar;
    private EditText starDateEditText;
    private EditText endDateEditText;
    private EditText reasonEditText;
    private TextView chunks, days;
    Employee employee;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;

    private Button cancelButton;
    private Button applyButton;
    String[] typeLeaves = {"Full Day", "Half Day", "Quarter Day"};
    private static final String FIRST_EMPID = "EID";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static int currentEMPID = 0;
    static int chunk = 0;
    static int AVleaves = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_leave);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        starDateEditText = (EditText) findViewById(R.id.startDate);
        endDateEditText = (EditText) findViewById(R.id.endDate);
        reasonEditText = (EditText) findViewById(R.id.reason);
        applyButton = (Button) findViewById(R.id.apply);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        chunks = findViewById(R.id.chunksLeft);
        days = findViewById(R.id.leavesLeft);


        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeLeaves);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        final Leaves leaves = new Leaves();
        employee = new Employee();

        database = FirebaseDatabase.getInstance();
        if (sharedPreferences.getInt(Constant.Current_EMP_ID, -1) >= 0) {
            currentEMPID = sharedPreferences.getInt(Constant.Current_EMP_ID, 0);
            database.getReference().child("Employee Details").child(FIRST_EMPID + currentEMPID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("no_of_chunks")) {
                        chunk = dataSnapshot.child("no_of_chunks").getValue(Integer.class);
                        AVleaves = dataSnapshot.child("no_of_leaves").getValue(Integer.class);
                        employee.setEmail(dataSnapshot.child("email").getValue(String.class));
                        employee.setName(dataSnapshot.child("name").getValue(String.class));
                        employee.setNo_of_chunks(dataSnapshot.child("no_of_chunks").getValue(Integer.class));
                        employee.setNo_of_leaves(dataSnapshot.child("no_of_leaves").getValue(Integer.class));
                        chunks.setText("Chunk Available" + dataSnapshot.child("no_of_chunks").getValue());
                        days.setText("Days Available" + dataSnapshot.child("no_of_leaves").getValue());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        databaseReference = database.getReference().child("Leave Requests");
        myCalendar = Calendar.getInstance();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reasonEditText.getText().toString().equals("")) {
                    reasonEditText.setError("Reason is required!");
                }
                leaves.setStartDate(starDateEditText.getText().toString());
                leaves.setEndDate(endDateEditText.getText().toString());
                leaves.setReason(reasonEditText.getText().toString());
                leaves.setStatus(false);
                leaves.setEmpId(currentEMPID);
                leaves.setLeaveId(starDateEditText.getText().toString() + "" + endDateEditText.getText().toString() + "" + currentEMPID);
                databaseReference.child(FIRST_EMPID + currentEMPID).setValue(leaves).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.child(FIRST_EMPID + currentEMPID).child("email").setValue(employee.getEmail());
                        databaseReference.child(FIRST_EMPID + currentEMPID).child("no_of_chunks").setValue(employee.getNo_of_chunks());
                        databaseReference.child(FIRST_EMPID + currentEMPID).child("no_of_leaves").setValue(employee.getNo_of_leaves());
                        databaseReference.child(FIRST_EMPID + currentEMPID).child("EID").setValue(currentEMPID);
                        databaseReference.child(FIRST_EMPID + currentEMPID).child("name").setValue(employee.getName());


                    }
                });


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), typeLeaves[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void calculateLeaves() {


    }
}