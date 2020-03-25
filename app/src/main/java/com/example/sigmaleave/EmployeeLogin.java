package com.example.sigmaleave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLogin extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputEmail, inputPassword,inputManager,inputEID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    Employee employee;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private CheckBox mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeelogin);

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        inputEmail = findViewById(R.id.em);
        inputPassword = findViewById(R.id.p);
        inputManager=findViewById(R.id.manager);
        inputEID=findViewById(R.id.EID);
        btnLogin = findViewById(R.id.Login);
        mgr=findViewById(R.id.checkBox);
        employee = new Employee();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = inputEmail.getText().toString();
                final String Password = inputPassword.getText().toString();
                String eID=inputEID.getText().toString();
                if (TextUtils.isEmpty((Email))) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((Password))) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mgr.isChecked()) {
                    DatabaseReference managerRef=databaseReference.child("Employee Details").child("Users");
                    ValueEventListener event = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String e = ds.child("email").getValue(String.class);
                                String p = ds.child("password").getValue(String.class);
                                if (e.equals(Email) && p.equals(Password)) {
                                    editor.putInt(Constant.Current_M_ID, ds.child("m_ID").getValue(Integer.class));
                                    editor.putString(Constant.USER_TYPE,"Managers");
                                    editor.apply();
                                    Intent it = new Intent(EmployeeLogin.this, ManagerDashboard.class);
                                    startActivity(it);
                                    Toast.makeText(EmployeeLogin.this, "Welcome Manager!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    {
                                    Toast.makeText(EmployeeLogin.this, "Check Credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    };
                    managerRef.addListenerForSingleValueEvent(event);
                }
                else {
                    String managerName=inputManager.getText().toString().trim();
                    DatabaseReference usersdRef = databaseReference.child("Employee Details").child("Users").child(managerName);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String email = ds.child("email").getValue(String.class);
                                    String password = ds.child("password").getValue(String.class);
                                    if (email.equals(Email) && password.equals(Password))
                                    {
                                        editor.putString(Constant.Current_Employee_M_ID, ds.child("m_ID").getValue(String.class));
                                        editor.putInt(Constant.Current_EMP_ID, ds.child("e_ID").getValue(Integer.class));
                                        editor.putString(Constant.USER_TYPE, "Employees");
                                        editor.apply();
                                        Toast.makeText(EmployeeLogin.this, "Welcome ! ", Toast.LENGTH_SHORT).show();
                                        Intent it = new Intent(EmployeeLogin.this, EmployeeDashboard.class);
                                        startActivity(it);
                                    }
                                    else {
                                        Toast.makeText(EmployeeLogin.this, "Check Credentials.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    usersdRef.addListenerForSingleValueEvent(eventListener);
                }
            }
        });
    }
}
