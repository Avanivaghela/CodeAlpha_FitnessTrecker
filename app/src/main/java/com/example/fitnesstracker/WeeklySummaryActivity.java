package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class WeeklySummaryActivity extends AppCompatActivity {

    TextView txtWeeklySummary;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);

        txtWeeklySummary = findViewById(R.id.txtWeeklySummary);
        db = new DatabaseHelper(this);

        Cursor cursor = db.getAllData();

        int workouts = 0;
        int steps = 0;
        int calories = 0;

        while (cursor.moveToNext()) {
            workouts++;
            steps += cursor.getInt(3);
            calories += cursor.getInt(4);
        }

        txtWeeklySummary.setText(
                "🏋 Total Workouts : " + workouts +
                        "\n\n👣 Total Steps : " + steps +
                        "\n\n🔥 Total Calories : " + calories
        );
    }
}