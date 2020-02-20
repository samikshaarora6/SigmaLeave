package com.example.sigmaleave;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewLeaves extends AppCompatActivity {
    private static final String TAG = "RecyclerViewLeaves";
    TextView t1;
    Button b1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private ArrayList<Leaves> LeavesArrayList;
    Leaves leaves;
    ProgressDialog dialog;
    LeavesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        t1 = findViewById(R.id.textView);
        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference();

        dialog = new ProgressDialog(RecyclerViewLeaves.this);
        dialog.setMessage("Loading....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        LeavesArrayList = new ArrayList<Leaves>();
        getEmployeeDetails();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
   adapter = new LeavesAdapter(LeavesArrayList, RecyclerViewLeaves.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void getEmployeeDetails() {
        dialog.show();
        databaseReference.child("Leave Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.hasChild("startDate")) {
                        String StartDate= dataSnapshot1.child("startDate").getValue(String.class);
                        String EndDate = dataSnapshot1.child("endDate").getValue(String.class);
                        Leaves leaves=new Leaves(StartDate,EndDate);
                        LeavesArrayList.add(leaves);
                        t1.setText(StartDate);
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dialog.dismiss();
        }
    }


