package com.example.sigmaleave;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {
    private EditText name,password,email;
    private Button button;
    private Employee employee;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name=findViewById(R.id.Addname);
        email=findViewById(R.id.Addemail);
        password=findViewById(R.id.Addpass);
        button=findViewById(R.id.Added);
        employee=new Employee();
        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Employee Details");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employee.setName(name.getText().toString());
                employee.setEmail(email.getText().toString());
                employee.setPassword(password.getText().toString());
                databaseReference.child(employee.getEmail()).setValue(employee);

            }
        });
       // Toast. makeText(Add.this," Employee Added Successfully ",Toast. LENGTH_SHORT);
    }
}