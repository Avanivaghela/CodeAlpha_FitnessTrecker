package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    TextView txtHistory;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        txtHistory = findViewById(R.id.txtHistory);

        db = new DatabaseHelper(this);

        Cursor cursor = db.getAllData();

        StringBuilder data = new StringBuilder();

        while (cursor.moveToNext()) {

            data.append("Exercise: ")
                    .append(cursor.getString(1))
                    .append("\n");

            data.append("Date: ")
                    .append(cursor.getString(5))
                    .append("\n");

            data.append("Workout Time: ")
                    .append(cursor.getString(2))
                    .append("\n");

            data.append("Steps: ")
                    .append(cursor.getInt(3))
                    .append("\n");


            data.append("Calories: ")
                    .append(cursor.getInt(4))
                    .append("\n\n");
        }

        txtHistory.setText(data.toString());
    }
}