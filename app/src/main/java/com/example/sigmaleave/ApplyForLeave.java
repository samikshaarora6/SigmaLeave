package com.example.sigmaleave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ApplyForLeave extends AppCompatActivity {

    private Button apply;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Calendar myCalendar;
    private EditText starDateEditText;
    private EditText endDateEditText;
    private EditText reasonEditText;
    private TextView chunks, days;
    Leaves leaves;
    Employee employee;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;

    private Button cancelButton;
    private Button applyButton;
    private RadioGroup radioGroup;
    private RadioButton fullDay, halfDay, quarterDay;
    private static final String FIRST_EMPID = "EID";
    private static final String LeaveId = "LeaveNumber";
    private static Integer Leave_NO = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static int currentEMPID = 0;
    static int chunk = 30;
    static int AVleaves = 15;


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
        radioGroup = findViewById(R.id.radio_1);
        radioGroup.clearCheck();
        fullDay = findViewById(R.id.Fullday1);
        halfDay = findViewById(R.id.Halfday1);
        quarterDay = findViewById(R.id.quarter);

        leaves = new Leaves();
        employee = new Employee();

        database = FirebaseDatabase.getInstance();
        if ((sharedPreferences.getInt(Constant.Current_EMP_ID, -1) >= 0)) {
            currentEMPID = sharedPreferences.getInt(Constant.Current_EMP_ID, 0);
            database.getReference().child("Employee Details").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("no_of_chunks")) {
                        chunk = dataSnapshot.child("no_of_chunks").getValue(Integer.class);
                        AVleaves = dataSnapshot.child("no_of_leaves").getValue(Integer.class);
                        currentEMPID = dataSnapshot.child("e_ID").getValue(Integer.class);
                        editor.putInt(Constant.Current_EMP_ID,currentEMPID);
                        editor.apply();
                        employee.setEmail(dataSnapshot.child("email").getValue(String.class));
                        employee.setName(dataSnapshot.child("name").getValue(String.class));
                        employee.setNo_of_chunks(dataSnapshot.child("no_of_chunks").getValue(Integer.class));
                        employee.setNo_of_leaves(dataSnapshot.child("no_of_leaves").getValue(Integer.class));
                        employee.setManagerName(dataSnapshot.child("managerName").getValue().toString());
                        employee.setMaritial_Status(dataSnapshot.child("maritial_Status").getValue().toString());
                        employee.setBloodGroup(dataSnapshot.child("bloodGroup").getValue().toString());
                        employee.setDOB(dataSnapshot.child("dob").getValue().toString());
                        Leave_NO = dataSnapshot.child(LeaveId).getValue(Integer.class);
//                        chunks.setText("Chunk Available" + dataSnapshot.child("no_of_chunks").getValue());
//                        days.setText("Days Available" + dataSnapshot.child("no_of_leaves").getValue());
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
                    return;
                }
                currentEMPID = sharedPreferences.getInt(Constant.Current_EMP_ID, 0);
                radioFunctions();
                leaves.setStartDate(starDateEditText.getText().toString());
                leaves.setEndDate(endDateEditText.getText().toString());
                leaves.setReason(reasonEditText.getText().toString());
                leaves.setStatus(false);
                leaves.setEmpId(currentEMPID);
                leaves.setLeaveId(starDateEditText.getText().toString() + "" + endDateEditText.getText().toString() + "" + currentEMPID);
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("End Date").setValue(leaves.getEndDate());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("Start Date").setValue(leaves.getStartDate());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("Reason").setValue(leaves.getReason());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("Status").setValue(leaves.isStatus());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("EmpID").setValue(leaves.getEmpId());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child("Leave Type").setValue(leaves.getLeaveType());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).child(LeaveId).setValue(Leave_NO);
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//
////                        databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId+LeaveNo).child("no_of_chunks").setValue(employee.getNo_of_chunks());
////                        databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId+LeaveNo).child("no_of_leaves").setValue(employee.getNo_of_leaves());
////                        databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId+LeaveNo).child("EID").setValue(currentEMPID);
////                        databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId+LeaveNo).child("name").setValue(employee.getName());
//
//                        //databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId+LeaveNo).child("Leave Type").setValue(leaves.getLeaveType());
//                    }
//                });
                database.getReference().child("Employee Details").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child("no_of_chunks").setValue(employee.getNo_of_chunks());
                database.getReference().child("Employee Details").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child("no_of_leaves").setValue(employee.getNo_of_leaves());

                Leave_NO++;
                database.getReference().child("Employee Details").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child(LeaveId).setValue(Leave_NO);

            }
        });
    }

    private void radioFunctions() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.Fullday1:
                leaves.setLeaveType(fullDay.getText().toString().trim());
                chunk = chunk - 1;
                AVleaves = AVleaves - 1;
                employee.setNo_of_chunks(chunk);
                employee.setNo_of_leaves(AVleaves);


            case R.id.Halfday1:
                leaves.setLeaveType(halfDay.getText().toString().trim());
                chunk = chunk - 1;
                AVleaves = (int) (AVleaves - 0.5);
                employee.setNo_of_chunks(chunk);
                employee.setNo_of_leaves(AVleaves);

            case R.id.quarter:
                leaves.setLeaveType(quarterDay.getText().toString().trim());
                chunk = chunk - 1;
                AVleaves = (int) (AVleaves - 0.25);
                employee.setNo_of_chunks(chunk);
                employee.setNo_of_leaves(AVleaves);
        }
    }
}