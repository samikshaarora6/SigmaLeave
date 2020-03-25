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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewLeavesAdmin extends AppCompatActivity implements OnItemClickListener {
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
        setContentView(R.layout.recyclerview);
        t1 = findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        dialog = new ProgressDialog(RecyclerViewLeavesAdmin.this);
        dialog.setMessage("Loading....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        LeavesArrayList = new ArrayList<Leaves>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        getEmployeeDetails();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeavesAdapter(LeavesArrayList, RecyclerViewLeavesAdmin.this, this);
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
                        if (dataSnapshot2.hasChild("adminApproval")||dataSnapshot2.hasChild("managerApproved")) {
                            if (!dataSnapshot2.child("adminApproval").getValue(Boolean.class))
                            { if (dataSnapshot2.hasChild("startDate")) {
                                    Leaves leaves = dataSnapshot2.getValue(Leaves.class);
                                    LeavesArrayList.add(leaves);
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
    public void onItemClick(final int id, final int position, boolean status, final int leaveId, String m_Id) {
        if (status){
            dialog.show();
            Toast.makeText(this, "Status True", Toast.LENGTH_SHORT).show();
        //    final Employee[] employee = {new Employee()};
            String startDate = LeavesArrayList.get(position).getStartDate();
            String endDate = LeavesArrayList.get(position).getEndDate();
            final int diff= getCountOfDays(startDate,endDate);
//      final finalCurrentChunks = 0;
//      final int[] finalCurrentDays = {0};
            //TODO Please Calculate Difference between two dates , acc. to that subtract chunks and leaves as like below code
            database.getReference().child("Employee Details").child("Users").child(m_Id).child("EID"+id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("e_ID")) {
                            Employee employee1=dataSnapshot.getValue(Employee.class);
                           // employee[0] = dataSnapshot.getValue(Employee.class);
                            if (employee1.getNo_of_leaves() >= diff && employee1.getNo_of_chunks() != 0) {
                                int currentChunks = employee1.getNo_of_chunks();
                                int currentDays = employee1.getNo_of_leaves();
                                String type=getLeaveType(leaveId,id);
                                if (diff == 0)
                                {
                                    if(type=="Quarter"){
                                        currentChunks=currentChunks-1;
                                        currentDays= (int) (currentDays-0.25);
                                    }
                                    else if(type=="null"){
                                        currentChunks=currentChunks-1;
                                        currentDays=(int) (currentDays-0.5);
                                    }
                                }
                                else if (diff >= 1)
                                {
                                   if(type=="Full"){
                                       currentChunks = currentChunks - 1;
                                       currentDays = (int) (currentDays - diff);
                                   }
//                                    if (dataSnapshot.child("leaveType").getValue(String.class).equals("Full")) {
//                                        currentChunks = currentChunks - 1;
//                                        currentDays = (int) (currentDays - diff);
//                                    }
                                }
                                finalCurrentChunks =currentChunks;
                                finalCurrentDays=currentDays;
                            }
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            if (database.getReference().child("Leave Requests").child("EID" + id).child("LeaveNumber" + leaveId).child("managerApproved").equals(true)) {
                database.getReference().child("Leave Requests").child("EID" + id).child("LeaveNumber" + leaveId).child("adminApproval").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecyclerViewLeavesAdmin.this, "Approved", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
            else{
                Toast.makeText(this, "Manager has not approved it yet", Toast.LENGTH_SHORT).show();
            }
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);
            dialog.dismiss();
            getEmployeeDetails();
        }
        else{
            database.getReference().child("Leave Requests").child("EID" + id).child("LeaveNumber" + leaveId).child("adminApproval").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(RecyclerViewLeavesAdmin.this, "Disapproved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_chunks").setValue(finalCurrentChunks);
            database.getReference().child("Employee Details").child("EID" + id).child("no_of_leaves").setValue(finalCurrentDays);
            dialog.dismiss();
            getEmployeeDetails();
        }
    }
    public String getLeaveType(int leaveID,int id){
        final String[] leaveType1 = new String[10];
        database.getReference().child("Leave Requests").child("EID"+id).child("LeaveNumber"+leaveID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("m_ID")) {
                    Leaves leaves1 = dataSnapshot.getValue(Leaves.class);
                    leaveType1[0] = dataSnapshot.child("leaveType").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return leaveType1[0];
    }
    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date createdConvertedDate = null;
        Date expireCovertedDate =null;
        Date todayWithZeroTime=null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);
            Date today = new Date();
            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int cYear = 0, cMonth = 0, cDay = 0;
        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }
        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);
        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);
        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return (int) dayCount;
    }

}
