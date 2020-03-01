package com.example.sigmaleave;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewLeaves extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "RecyclerViewLeaves";
    TextView t1;
    Button b1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private ArrayList<Leaves> LeavesArrayList;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    Leaves leaves;
    ProgressDialog dialog;
    LeavesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        t1 = findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        dialog = new ProgressDialog(RecyclerViewLeaves.this);
        dialog.setMessage("Loading....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        LeavesArrayList = new ArrayList<Leaves>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        getEmployeeDetails();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeavesAdapter(LeavesArrayList, RecyclerViewLeaves.this, this);
        recyclerView.setAdapter(adapter);

    }

    public void getEmployeeDetails() {
        dialog.show();
        employeeArrayList = new ArrayList<>();
        LeavesArrayList = new ArrayList<>();
        databaseReference.child("Leave Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.hasChild("status")) {
                        if (!dataSnapshot1.child("status").getValue(Boolean.class)) {
                            if (dataSnapshot1.hasChild("startDate")) {
                                String StartDate = dataSnapshot1.child("startDate").getValue(String.class);
                                String EndDate = dataSnapshot1.child("endDate").getValue(String.class);
//                        Leaves leaves = new Leaves(StartDate, EndDate);
                                Leaves leaves = dataSnapshot1.getValue(Leaves.class);
                                Employee employee = dataSnapshot1.getValue(Employee.class);
                                employeeArrayList.add(employee);
                                LeavesArrayList.add(leaves);
                                t1.setText(StartDate);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();

            }
        });

    }

    @Override
    public void onItemClick(final int id, final int position, boolean status) {
        if (status) {
            Toast.makeText(this, "Status True", Toast.LENGTH_SHORT).show();
            String startDate = LeavesArrayList.get(position).getStartDate();
            String endDate = LeavesArrayList.get(position).getEndDate();
            //TODO Please Calculate Difference between two dates , acc. to that subtract chunks and leaves as like below code
            int days = 2;
            if (employeeArrayList.get(position).getNo_of_leaves() >= days && employeeArrayList.get(position).getNo_of_chunks() != 0) {
                int currentChunks = employeeArrayList.get(position).getNo_of_chunks();
                int currentDays = employeeArrayList.get(position).getNo_of_leaves();
                /////
                currentChunks = currentChunks - 1;
                currentDays = currentDays - days;
                final int finalCurrentChunks = currentChunks;
                final int finalCurrentDays = currentDays;

                databaseReference.child("Leave Requests").child("EID" + id).child("status").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("Employee Details").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
                        database.getReference().child("Employee Details").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);
                        database.getReference().child("Leave Requests").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
                        database.getReference().child("Leave Requests").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);

                        Toast.makeText(RecyclerViewLeaves.this, "Approved", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

        getEmployeeDetails();

    }
}
