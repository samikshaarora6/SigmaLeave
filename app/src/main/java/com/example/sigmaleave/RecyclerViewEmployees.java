package com.example.sigmaleave;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerViewEmployees extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Employee, EmployeesAdapter> recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleremp);

        //Firebase init
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Employee Details").child("Users");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewemp);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();
    }

    private void loadData()
    {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(databaseReference,Employee.class)
                        .build();
        recyclerAdapter = new FirebaseRecyclerAdapter<Employee,EmployeesAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EmployeesAdapter holder, int position, @NonNull Employee model) {
                holder.txtUserName.setText(model.getName());
                holder.txtUserSurname.setText(model.getManagerName());
                holder.txtUserNumber.setText(model.getEmail());
                holder.MobileNumber.setText(model.getMobileNumber());
                holder.DOB.setText(model.getDOB());
                holder.MS.setText(model.getMaritial_Status());
            }

            @NonNull
            @Override
            public EmployeesAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.employeelist,viewGroup,false);
                return new EmployeesAdapter(view);
            }
        };
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.startListening();
        recyclerView.setAdapter(recyclerAdapter);
    }
}