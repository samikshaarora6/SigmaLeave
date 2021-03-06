package com.example.sigmaleave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

public class ApplyForLeave extends AppCompatActivity {

    private Button apply;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Calendar myCalendar;
    private TextView starDateEditText;
    private TextView endDateEditText;
    private EditText reasonEditText;
    private TextView chunks, days;
    Leaves leaves;
    Employee employee;
    private DatePickerDialog.OnDateSetListener sD;
    private DatePickerDialog.OnDateSetListener eD;
    private static final String TAG = "ApplyForLeave";

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

        starDateEditText = findViewById(R.id.startDate);
        endDateEditText = findViewById(R.id.endDate);
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
        starDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ApplyForLeave.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        sD,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        sD = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                starDateEditText.setText(date);
            }
        };
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ApplyForLeave.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        eD,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        eD = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                endDateEditText.setText(date);
            }
        };
        if ((sharedPreferences.getInt(Constant.Current_EMP_ID, -1) >= 0)) {
            currentEMPID = sharedPreferences.getInt(Constant.Current_EMP_ID, 0);
            database.getReference().child("Employee Details").child("Users").child(sharedPreferences.getString(Constant.Current_Employee_M_ID, "MID4")).child(FIRST_EMPID + currentEMPID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("no_of_chunks")) {
                        chunk = dataSnapshot.child("no_of_chunks").getValue(Integer.class);
                        AVleaves = dataSnapshot.child("no_of_leaves").getValue(Integer.class);
                        currentEMPID = dataSnapshot.child("e_ID").getValue(Integer.class);
                        editor.putInt(Constant.Current_EMP_ID, currentEMPID);
                        editor.apply();
                        employee = dataSnapshot.getValue(Employee.class);
                        Leave_NO = dataSnapshot.child(LeaveId).getValue(Integer.class);
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
                leaves.setManagerApproved(false);
                leaves.setEmpId(currentEMPID);
                leaves.setAdminApproval(false);
                leaves.setLeaveId(Leave_NO);
                leaves.setNo_of_leaves(15);
                leaves.setNo_of_chunks(30);
                leaves.setM_ID(employee.getM_ID());
                databaseReference.child(FIRST_EMPID + currentEMPID).child(LeaveId + Leave_NO).setValue(leaves);
                database.getReference().child("Users").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).setValue(employee);
                database.getReference().child("Users").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child("no_of_chunks").setValue(employee.getNo_of_chunks());
                database.getReference().child("Users").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child("no_of_leaves").setValue(employee.getNo_of_leaves());
                Leave_NO++;
                database.getReference().child("Users").child(sharedPreferences.getString(Constant.USER_TYPE, "Employees")).child(FIRST_EMPID + currentEMPID).child(LeaveId).setValue(Leave_NO);
                Toast.makeText(ApplyForLeave.this, " Leave Applied ! "
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void radioFunctions() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.Fullday1:
                leaves.setLeaveType(fullDay.getText().toString().trim());
            case R.id.Halfday1:
                leaves.setLeaveType(halfDay.getText().toString().trim());
            case R.id.quarter:
                leaves.setLeaveType(quarterDay.getText().toString().trim());
                chunk = chunk - 1;
        }
    }
}