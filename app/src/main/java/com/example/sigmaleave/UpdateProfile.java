package com.example.sigmaleave;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UpdateProfile extends AppCompatActivity {

    private static final String TAG = "UpdateProfile";
    private TextView mDisplayDate;
    private EditText name, email, np, cp,cmail;
    private Button submit;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up);

        mDisplayDate = findViewById(R.id.DOB);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        cp = findViewById(R.id.CPassword);
        np = findViewById(R.id.Npassword);
        cmail=findViewById(R.id.cemail);
        submit = findViewById(R.id.submit);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        UpdateProfile.this,
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
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = name.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String cEmail=cmail.getText().toString().trim();
                final String Cpassword = cp.getText().toString().trim();
                final String Npassword = np.getText().toString().trim();
                final DatabaseReference usersdRef = databaseReference.child("Employee Details").child("Users");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String email = ds.child("email").getValue(String.class);
                            String password = ds.child("password").getValue(String.class);
                            if (email.equals(cEmail) && password.equals(Cpassword)) {
                                editor.putInt(Constant.Current_EMP_ID, ds.child("e_ID").getValue(Integer.class));
                                editor.apply();
                                usersdRef.child(ds.getKey()).child("email").setValue(Email);
                                usersdRef.child(ds.getKey()).child("password").setValue(Npassword);
                                usersdRef.child(ds.getKey()).child("name").setValue(Name);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                usersdRef.addListenerForSingleValueEvent(eventListener);
                Toast.makeText(UpdateProfile.this, "Successfully Updated",Toast.LENGTH_SHORT).show();
            }

        });
    }
}


