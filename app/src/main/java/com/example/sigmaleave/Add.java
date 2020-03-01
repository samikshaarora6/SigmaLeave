package com.example.sigmaleave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Add extends AppCompatActivity {
    private EditText name, password, email, manag, bloodGroup, MaritialStat, mobileNumber,EMPId;
    TextView date;
    private Button button;
    private Employee employee;
    private Manager m;
    private RadioGroup radioEmpGroup;
    private RadioButton manager, emp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private static final String FIRST_EMPID = "EID";
    private static final String FIRST_MID = "MID";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Add";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int currentEMPID = 0;
    private static final String LeaveId = "LeaveNumber";
    private static Integer Leave_NO = 0;
    int currentMID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name = findViewById(R.id.Addname);
        email = findViewById(R.id.Addemail);
        password = findViewById(R.id.Addpass);
        EMPId=findViewById(R.id.AddEid);
        MaritialStat = findViewById(R.id.AddMaritial);
        mobileNumber = findViewById(R.id.AddMobileNumber);
        date = findViewById(R.id.AddDOB);
        manag = findViewById(R.id.AddManager);
        bloodGroup = findViewById(R.id.AddBloodGroup);
        button = findViewById(R.id.Added);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        employee = new Employee();
        m = new Manager();
        database = FirebaseDatabase.getInstance();
        radioEmpGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioEmpGroup.clearCheck();
        manager = findViewById(R.id.radioButton1);
        emp = findViewById(R.id.radioButton);
        databaseReference = database.getReference().child("Employee Details");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Add.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                    String datee = day + "-" + month + "-" + year;
                date.setText(datee);
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioEmpGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        database.getReference().child("CurrentEMPID").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue(Integer.class) > 0) {
                                    currentEMPID = dataSnapshot.getValue(Integer.class);
                                    employee.setName(name.getText().toString().trim());
                                    employee.setEmail(email.getText().toString().trim());
                                    employee.setPassword(password.getText().toString().trim());
                                    employee.setNo_of_chunks(30);
                                    employee.setNo_of_leaves(15);
                                    employee.setE_ID(currentEMPID);
                                   // employee.setEmpID(EMPId.getText().toString().trim());
                                    employee.setBloodGroup(bloodGroup.getText().toString().trim());
                                    employee.setDOB(date.getText().toString().trim());
                                    employee.setManagerName(manag.getText().toString().trim());
                                    employee.setMaritial_Status(MaritialStat.getText().toString().trim());
                                    employee.setMobileNumber(mobileNumber.getText().toString().trim());
                                    databaseReference.child("Employees").child(FIRST_EMPID + currentEMPID).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @SuppressLint("ShowToast")
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            databaseReference.child("Employees").child(FIRST_EMPID + currentEMPID).child(LeaveId).setValue(Leave_NO);
                                            currentEMPID++;
                                            database.getReference().child("CurrentEMPID").setValue(currentEMPID);
                                            editor.putInt(Constant.Running_EMP_ID, currentEMPID);
                                            editor.apply();

                                            Toast.makeText(Add.this, " Employee Added Successfully ", Toast.LENGTH_SHORT);

                                        }
                                    });

                                } else {
                                    currentEMPID = 0;
                                    employee.setName(name.getText().toString());
                                    employee.setEmail(email.getText().toString());
                                    employee.setPassword(password.getText().toString());
                                    employee.setNo_of_chunks(30);
                                    employee.setNo_of_leaves(15);
                                    employee.setE_ID(currentEMPID);
                                   // employee.setEmpID(EMPId.getText().toString().trim());
                                    employee.setBloodGroup(bloodGroup.getText().toString().trim());
                                    employee.setDOB(date.getText().toString().trim());
                                    employee.setManagerName(manag.getText().toString().trim());
                                    employee.setMaritial_Status(MaritialStat.getText().toString().trim());
                                    employee.setMobileNumber(mobileNumber.getText().toString().trim());
                                    databaseReference.child("Employees").child(FIRST_EMPID + currentEMPID).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @SuppressLint("ShowToast")
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            databaseReference.child("Employees").child(FIRST_EMPID + currentEMPID).child(LeaveId).setValue(Leave_NO);
                                            currentEMPID++;
                                            database.getReference().child("CurrentEMPID").setValue(currentEMPID);
                                            editor.putInt(Constant.Running_EMP_ID, currentEMPID);
                                            editor.apply();
                                            Toast.makeText(Add.this, " Employee Added Successfully ", Toast.LENGTH_SHORT);

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case R.id.radioButton1:
                        database.getReference().child("CurrentMID").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue(Integer.class) > 0) {
                                    currentMID = dataSnapshot.getValue(Integer.class);
                                    m.setName(name.getText().toString());
                                    m.setEmail(email.getText().toString());
                                    m.setPassword(password.getText().toString());
                                    m.setNo_of_chunks(30);
                                    m.setNo_of_leaves(15);
                                    m.setM_ID(currentMID);
//                                        employee.setBloodGroup(bloodGroup.getText().toString().trim());
//                                        employee.setDOB(date.getText().toString().trim());
//                                        employee.setManagerName(manag.getText().toString().trim());
//                                        employee.setMaritial_Status(MaritialStat.getText().toString().trim());
//                                        employee.setMobileNumber(mobileNumber.getText().toString().trim());
                                    databaseReference.child("Managers").child(FIRST_MID + currentMID).setValue(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            databaseReference.child("Managers").child(FIRST_MID + currentMID).child(LeaveId).setValue(Leave_NO);
                                            currentMID++;
                                            database.getReference().child("CurrentMID").setValue(currentMID);
                                            editor.putInt(Constant.Running_M_ID, currentMID);
                                            editor.apply();
                                            Toast.makeText(Add.this, " Manager Added Successfully ", Toast.LENGTH_SHORT);
                                        }
                                    });

                                } else {
                                    currentMID = 0;
                                    m.setName(name.getText().toString());
                                    m.setEmail(email.getText().toString());
                                    m.setPassword(password.getText().toString());
                                    m.setNo_of_chunks(30);
                                    m.setNo_of_leaves(15);
                                    m.setM_ID(currentMID);
//                                        employee.setBloodGroup(bloodGroup.getText().toString().trim());
//                                        employee.setDOB(date.getText().toString().trim());
//                                        employee.setManagerName(manag.getText().toString().trim());
//                                        employee.setMaritial_Status(MaritialStat.getText().toString().trim());
//                                        employee.setMobileNumber(mobileNumber.getText().toString().trim());
                                    databaseReference.child("Managers").child(FIRST_MID + currentMID).setValue(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            databaseReference.child("Managers").child(FIRST_MID + currentMID).child(LeaveId).setValue(Leave_NO);
                                            currentMID++;
                                            database.getReference().child("CurrentMID").setValue(currentMID);
                                            editor.putInt(Constant.Running_M_ID, currentMID);
                                            editor.apply();
                                            Toast.makeText(Add.this, " Manager Added Successfully ", Toast.LENGTH_SHORT);
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
        });
    }
}