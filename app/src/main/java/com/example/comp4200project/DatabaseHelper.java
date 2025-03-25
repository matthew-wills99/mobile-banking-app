package com.example.comp4200project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BankApp.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CARD_NUMBER = "card_number";
    private static final String COLUMN_EXPIRY_MM = "expiry_mm";
    private static final String COLUMN_EXPIRY_YY = "expiry_yy";
    private static final String COLUMN_CVV = "cvv";
    private static final String COLUMN_PIN = "pin";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_CARD_NUMBER + " TEXT,"
            + COLUMN_EXPIRY_MM + " TEXT,"
            + COLUMN_EXPIRY_YY + " TEXT,"
            + COLUMN_CVV + " TEXT,"
            + COLUMN_PIN + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long insertUser(String email, String password, String cardNumber, String expiryMM, String expiryYY, String cvv, String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_CARD_NUMBER, cardNumber);
        values.put(COLUMN_EXPIRY_MM, expiryMM);
        values.put(COLUMN_EXPIRY_YY, expiryYY);
        values.put(COLUMN_CVV, cvv);
        values.put(COLUMN_PIN, pin);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public boolean checkLoginWithEmail(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{email, password},
                null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public boolean checkLoginWithCard(String cardNumber, String expiryMM, String expiryYY, String cvv, String pin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_CARD_NUMBER + " = ? AND " + COLUMN_EXPIRY_MM + " = ? AND " + COLUMN_EXPIRY_YY + " = ? AND " + COLUMN_CVV + " = ? AND " + COLUMN_PIN + " = ?",
                new String[]{cardNumber, expiryMM, expiryYY, cvv, pin},
                null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }
}