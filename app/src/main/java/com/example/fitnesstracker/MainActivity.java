package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etExercise, etSteps, etCalories;
    Button btnSave, btnView;
    ProgressBar progressBar;
    TextView txtTotalSteps, txtTotalCalories;
    DatabaseHelper db;

    EditText etTime;
    Button btnWeekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etExercise = findViewById(R.id.etExercise);
        etSteps = findViewById(R.id.etSteps);
        etCalories = findViewById(R.id.etCalories);

        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnWeekly = findViewById(R.id.btnWeekly);

        progressBar = findViewById(R.id.progressBar);

        txtTotalSteps = findViewById(R.id.txtTotalSteps);
        txtTotalCalories = findViewById(R.id.txtTotalCalories);
        etTime = findViewById(R.id.etTime);

        db = new DatabaseHelper(this);

        updateDashboard();


        btnSave.setOnClickListener(v -> {
            String time = etTime.getText().toString();
            String date = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()).format(new Date());


            String exercise = etExercise.getText().toString();

            int steps = Integer.parseInt(etSteps.getText().toString());

            int calories = Integer.parseInt(etCalories.getText().toString());


            boolean result = db.insertWorkout(
                    exercise,
                    time,
                    steps,
                    calories,
                    date
            );


            if (result) {

                Toast.makeText(this, "Workout Saved", Toast.LENGTH_SHORT).show();

                updateDashboard();

                etExercise.setText("");
                etSteps.setText("");
                etCalories.setText("");

            } else {

                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

            }

        });

        btnView.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);

        });
        btnWeekly.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeeklySummaryActivity.class);
            startActivity(intent);
        });

    }

    private void updateDashboard() {

        Cursor cursor = db.getAllData();

        int totalSteps = 0;
        int totalCalories = 0;

        while (cursor.moveToNext()) {
            totalSteps += cursor.getInt(2);
            totalCalories += cursor.getInt(3);
        }

        txtTotalSteps.setText("Total Steps: " + totalSteps);
        txtTotalCalories.setText("Total Calories: " + totalCalories);

        int progress = Math.min((totalSteps * 100) / 10000, 100);
        progressBar.setProgress(progress);
    }
}