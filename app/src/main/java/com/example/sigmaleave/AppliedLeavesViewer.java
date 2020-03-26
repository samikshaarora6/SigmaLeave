package com.example.sigmaleave;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

public class AppliedLeavesViewer extends AppCompatActivity implements OnItemClickListener {
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
    int finalCurrentChunks = 0;
    int finalCurrentDays = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavesviewer);
        t1 = findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        dialog = new ProgressDialog(AppliedLeavesViewer.this);
        dialog.setMessage("Loading....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        LeavesArrayList = new ArrayList<Leaves>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewemp);
        getEmployeeDetails();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeavesAdapter(LeavesArrayList, AppliedLeavesViewer.this, this);
        recyclerView.setAdapter(adapter);
    }
    public void getEmployeeDetails() {
        dialog.show();
        employeeArrayList = new ArrayList<>();
        LeavesArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("EID","");

        database.getReference().child("Leave Requests").child(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.hasChild("adminApproval")) {
                            //       boolean val= dataSnapshot1.child("adminApproval").getValue(Boolean.class);
                            Leaves leaves = dataSnapshot2.getValue(Leaves.class);
                            LeavesArrayList.add(leaves);
                            //     t1.setText(""+val);
                        } else {
                            //  boolean val= dataSnapshot1.child("adminApproval").getValue(Boolean.class);
                            Leaves leaves = dataSnapshot1.getValue(Leaves.class);
                            LeavesArrayList.add(leaves);
                            // t1.setText(""+val);
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
    public void onItemClick(int id, int position, boolean status, int leaveId, String M_Id) {
    }
}
