package com.example.sigmaleave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    OnItemClickListener onItemClickListener;
    SharedPreferences sharedPreferences;


    public LeavesAdapter(ArrayList<Leaves> contactList, Context mContext, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
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
        contactViewHolder.yess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(contactList.get(position).getEmpId(), position, true);
            }
        });
        contactViewHolder.No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(contactList.get(position).getEmpId(), position, false);
            }
        });


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

}
