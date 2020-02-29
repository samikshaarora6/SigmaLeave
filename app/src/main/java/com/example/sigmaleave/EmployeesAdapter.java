package com.example.sigmaleave;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeesAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtUserName,txtUserSurname,txtUserNumber,DOB,MS,MobileNumber;


    public EmployeesAdapter(@NonNull View itemView) {
        super(itemView);
        txtUserName = (TextView)itemView.findViewById(R.id.userName_textView);
        txtUserSurname = (TextView)itemView.findViewById(R.id.userSurname_textView);
        txtUserNumber = (TextView)itemView.findViewById(R.id.user_number_textView);
        DOB=itemView.findViewById(R.id.userDOB);
        MS=itemView.findViewById(R.id.useStat);
        MobileNumber=itemView.findViewById(R.id.useMOB);
    }

    @Override
    public void onClick(View v) {

    }
}