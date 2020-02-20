package com.example.sigmaleave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add extends AppCompatActivity {
    private EditText name, password, email;
    private Button button;
    private Employee employee;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private static final String FIRST_EMPID = "EID";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int currentEMPID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name = findViewById(R.id.Addname);
        email = findViewById(R.id.Addemail);
        password = findViewById(R.id.Addpass);
        button = findViewById(R.id.Added);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        employee = new Employee();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Employee Details");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt(Constant.Running_EMP_ID, 0) == 0) {
                    database.getReference().child("CurrentEMPID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue(Integer.class) > 0) {
                                currentEMPID = dataSnapshot.getValue(Integer.class);
                                employee.setName(name.getText().toString());
                                employee.setEmail(email.getText().toString());
                                employee.setPassword(password.getText().toString());
                                employee.setNo_of_chunks(20);
                                employee.setNo_of_leaves(50);
                                employee.setE_ID(currentEMPID);
                                databaseReference.child(FIRST_EMPID + currentEMPID).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
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
                                employee.setNo_of_chunks(20);
                                employee.setNo_of_leaves(50);
                                employee.setE_ID(currentEMPID);
                                databaseReference.child(FIRST_EMPID + currentEMPID).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
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

                } else {
                    employee.setName(name.getText().toString());
                    employee.setEmail(email.getText().toString());
                    employee.setPassword(password.getText().toString());
                    employee.setNo_of_chunks(20);
                    employee.setNo_of_leaves(50);
                    employee.setE_ID(currentEMPID);
                    databaseReference.child(FIRST_EMPID + currentEMPID).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            currentEMPID++;
                            database.getReference().child("CurrentEMPID").setValue(currentEMPID);
                            editor.putInt(Constant.Running_EMP_ID, currentEMPID);
                            editor.apply();
                            Toast.makeText(Add.this, " Employee Added Successfully ", Toast.LENGTH_SHORT);

                        }
                    });
                }


            }
        });
    }
}