package com.example.sigmaleave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomePage extends AppCompatActivity {
    private CardView c1,c2,c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        c1=findViewById(R.id.addEmployee);
        c2=findViewById(R.id.CheckForLeaves);
        c3=findViewById(R.id.CheckEmps);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomePage.this,RecyclerViewEmployees.class);
                startActivity(intent);
                finish();

            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomePage.this, RecyclerViewLeavesAdmin.class);
                startActivity(intent);
                finish();

            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttt=new Intent(AdminHomePage.this,Add.class);
                startActivity(intenttt);
                finish();
            }
        });
    }
}
