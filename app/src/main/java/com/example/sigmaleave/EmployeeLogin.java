package com.example.sigmaleave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLogin extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeelogin);
        inputEmail = findViewById(R.id.em);
        inputPassword = findViewById(R.id.p);
        btnLogin = findViewById(R.id.Login);
        employee = new Employee();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = inputEmail.getText().toString();
                final String Password = inputPassword.getText().toString();

                if (TextUtils.isEmpty((Email))) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty((Password))) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference = database.getReference();
                DatabaseReference usersdRef = databaseReference.child("Employee Details");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String email = ds.child("email").getValue(String.class);
                            String password = ds.child("password").getValue(String.class);
                            if (email.equals(Email)&&password.equals(Password)){
                                Toast.makeText(EmployeeLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent it=new Intent(EmployeeLogin.this, EmployeeDashboard.class);
                                startActivity(it);

                            }
                            else{
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
        });
    }
}
