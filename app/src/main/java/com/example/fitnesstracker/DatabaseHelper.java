package com.example.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitnessDB";
    private static final int DATABASE_VERSION = 3;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE workouts(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "exercise TEXT," +
                "time TEXT," +
                "steps INTEGER," +
                "calories INTEGER," +
                "date TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS workouts");
        onCreate(db);
    }

    public boolean insertWorkout(String exercise,
                                 String time,
                                 int steps,
                                 int calories,
                                 String date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("exercise", exercise);
        values.put("time", time);
        values.put("steps", steps);
        values.put("calories", calories);
        values.put("date", date);

        long result = db.insert("workouts", null, values);

        return result != -1;
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM workouts",
                null
        );
    }
}