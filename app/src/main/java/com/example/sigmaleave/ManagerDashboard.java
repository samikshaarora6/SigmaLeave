package com.example.sigmaleave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerDashboard extends AppCompatActivity {

    private Button btn,btn2,btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);
        btn=findViewById(R.id.PROF);
        btn2=findViewById(R.id.apply);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerDashboard.this,ApplyForLeave.class);
                startActivity(intent);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerDashboard.this,UpdateProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
