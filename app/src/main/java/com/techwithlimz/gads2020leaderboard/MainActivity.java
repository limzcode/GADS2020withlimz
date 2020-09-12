package com.techwithlimz.gads2020leaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.techwithlimz.gads2020leaderboard.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        MaterialButton submitProjectButton = findViewById(R.id.submit_project_button);
        submitProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSubmissionActivity();
            }
        });
    }

    private void projectSubmissionActivity() {
        Intent projectSubmission = new Intent(this, SubmitProjectActivity.class);
        startActivity(projectSubmission);
    }
}