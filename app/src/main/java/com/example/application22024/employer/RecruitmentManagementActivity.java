package com.example.application22024.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.application22024.First_Activity;
import com.example.application22024.R;
import com.google.android.material.navigation.NavigationView;

public class RecruitmentManagementActivity extends AppCompatActivity {
    Button openMenuButton;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recruitment_management);

        // Correct the usage of 'findViewById' instead of 'view.findViewById'
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                // Handle menu item selection
                switch (item.getItemId()) {
                    case R.id.addRecruitment:
                        intent = new Intent(RecruitmentManagementActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.Logout:
                        // Logout and navigate to the first activity
                        intent = new Intent(RecruitmentManagementActivity.this, First_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish(); // Close the current activity
                        return true;

                    default:
                        return false;
                }
            }
        });

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        openMenuButton = findViewById(R.id.button_open_menu);
        // Open the drawer when the button is clicked
        openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END); // Open menu from the right
            }
        });
    }
}
