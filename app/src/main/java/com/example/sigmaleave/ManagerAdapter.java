package com.example.sigmaleave;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ContactViewHolder> {
    private ArrayList<Leaves> contactList;
    private Context mContext;
    OnItemClickListener onItemClickListener;
    SharedPreferences sharedPreferences;


    public ManagerAdapter(ArrayList<Leaves> contactList, Context mContext, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ManagerAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.leaves, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManagerAdapter.ContactViewHolder contactViewHolder, final int position) {
        contactViewHolder.startDate.setText(contactList.get(position).getStartDate());
        contactViewHolder.endDate.setText(contactList.get(position).getEndDate());
        contactViewHolder.reason.setText(contactList.get(position).getEmpId());
        contactViewHolder.yess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(contactList.get(position).getEmpId(), position, true,contactList.get(position).getLeaveId());
            }
        });
        contactViewHolder.No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(contactList.get(position).getEmpId(), position, false,contactList.get(position).getLeaveId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView startDate, endDate,reason;
        Button yess, No;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            yess = itemView.findViewById(R.id.yes);
            No = itemView.findViewById(R.id.no);
            startDate = itemView.findViewById(R.id.details);
            endDate=itemView.findViewById(R.id.details2);
            reason=itemView.findViewById(R.id.details3);
        }
    }
}
