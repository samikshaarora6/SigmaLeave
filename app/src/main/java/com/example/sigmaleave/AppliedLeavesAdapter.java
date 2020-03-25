package com.example.sigmaleave;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AppliedLeavesAdapter extends RecyclerView.Adapter<AppliedLeavesAdapter.ContactViewHolder> {

    private ArrayList<Leaves> contactList;
    private Context mContext;
    OnItemClickListener onItemClickListener;
    SharedPreferences sharedPreferences;


    public AppliedLeavesAdapter(ArrayList<Leaves> contactList, Context mContext, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.leavesemployees, viewGroup, false);
        return new ContactViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, final int position) {
        contactViewHolder.managerApproved.setText(""+contactList.get(position).isManagerApproved());
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView managerApproved;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            managerApproved = itemView.findViewById(R.id.details);

        }
    }

}
