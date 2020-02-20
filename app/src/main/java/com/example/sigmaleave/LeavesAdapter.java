package com.example.sigmaleave;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.ContactViewHolder> {

    private ArrayList<Leaves> contactList;
    private Context mContext;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;


    public LeavesAdapter(ArrayList<Leaves> contactList, Context mContext) {
        this.contactList = contactList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.leaves, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, final int position) {
        contactViewHolder.details.setText(contactList.get(position).getStartDate());
       // contactViewHolder.details.setText(contactList.get(position).getEndDate());

//        contactViewHolder.yess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openforYes();
//            }
//        });
//        contactViewHolder.  No.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openforNo();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView details;
        Button yess, No;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            yess = itemView.findViewById(R.id.yes);
            No = itemView.findViewById(R.id.no);
            details = itemView.findViewById(R.id.details);
        }
    }

//    private void openforNo() {
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference();
//        //example bta rha hu
//
//        databaseReference.child("Leave Approved").child("leaves").child("employeeid").child("leaveapproved").setValue(trueorfalse);
//    }
//
//    private void openforYes(boolean value) {
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference().child("Leave Requests");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    //String key = dataSnapshot1.getKey();
//                    if (dataSnapshot1.hasChild("Leave Approved")) {
//                            databaseReference.child("Leave Requests").child("Leaves").child("Leave Approved").setValue(true);
//                        }
//
//                    }
//
//                }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }





