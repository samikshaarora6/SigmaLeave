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
    private static final String LeaveId = "LeaveNumber";
    final int finalCurrentChunks = 0;
    final int finalCurrentDays = 0;


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
        database.getReference().child("Leave Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.hasChild("status")) {
                            if (!dataSnapshot2.child("status").getValue(Boolean.class)) {
                                if (dataSnapshot2.hasChild("startDate")) {
//                                    String StartDate = dataSnapshot2.child("Start Date").getValue(String.class);
//                                    String EndDate = dataSnapshot2.child("End Date").getValue(String.class);
////                        Leaves leaves = new Leaves(StartDate, EndDate);
//
//
//
//
//                                    leaves.setEmpId(dataSnapshot2.child("EmpID").getValue(Integer.class));
//                                    leaves.setStartDate(dataSnapshot2.child("Start Date").getValue(String.class));
//                                    leaves.setEndDate(dataSnapshot2.child("End Date").getValue(String.class));
//                                    leaves.setLeaveId(dataSnapshot2.child(LeaveId).getValue(Integer.class));
//                                    leaves.setLeaveType(dataSnapshot2.child("Leave Type").getValue(String.class));
//                                    leaves.setReason(dataSnapshot2.child("Reason").getValue(String.class));
//                                    leaves.setAdminApproval(dataSnapshot2.child("Status").getValue(Boolean.class));
                                    Leaves leaves = dataSnapshot2.getValue(Leaves.class);
                                    Employee employee = dataSnapshot2.getValue(Employee.class);
                                    employeeArrayList.add(employee);
                                    LeavesArrayList.add(leaves);
                                    //t1.setText(StartDate);
                                }
                            }

                        }
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();

            }
        });

    }

    @Override
    public void onItemClick(final int id, final int position, boolean status, int leaveId) {
        if (status) {
            dialog.show();
            Toast.makeText(this, "Status True", Toast.LENGTH_SHORT).show();
            final Employee[] employee = {new Employee()};
            String startDate = LeavesArrayList.get(position).getStartDate();
            String endDate = LeavesArrayList.get(position).getEndDate();
            //TODO Please Calculate Difference between two dates , acc. to that subtract chunks and leaves as like below code
            final int days = 2;
            database.getReference().child("Employee Details").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.hasChild("EID" + id)) {
                            employee[0] = dataSnapshot1.child("EID" + id).getValue(Employee.class);
                            if (employee[0].getNo_of_leaves() >= days && employee[0].getNo_of_chunks() != 0) {
                                int currentChunks = employee[0].getNo_of_chunks();
                                int currentDays = employee[0].getNo_of_leaves();
                                /////
                                currentChunks = currentChunks - 1;
                                currentDays = currentDays - days;
                                final int finalCurrentChunks = currentChunks;
                                final int finalCurrentDays = currentDays;


                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            database.getReference().child("Leave Requests").child("EID" + id).child("LeaveNumber" + leaveId).child("adminApproval").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // database.getReference().child("Leave Requests").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
//                        database.getReference().child("Leave Requests").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);

                    Toast.makeText(RecyclerViewLeaves.this, "Approved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);
//

            dialog.dismiss();
           getEmployeeDetails();
        }

    }
}
