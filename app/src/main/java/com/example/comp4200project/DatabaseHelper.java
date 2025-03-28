package com.example.comp4200project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BankApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_MIDDLE_NAME = "middle_name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CARD_NUMBER = "card_number";
    private static final String COLUMN_EXPIRY_MM = "expiry_mm";
    private static final String COLUMN_EXPIRY_YY = "expiry_yy";
    private static final String COLUMN_CVV = "cvv";
    private static final String COLUMN_PIN = "pin";

    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ACCOUNT_TYPE = "account_type"; // "SAVINGS" or "CHEQUING"
    private static final String COLUMN_BALANCE = "balance";
    private static final String COLUMN_IS_ACTIVE = "is_active";

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_ACCOUNT_ID_FK = "account_id";
    private static final String COLUMN_TRANSACTION_TYPE = "transaction_type"; // "DEPOSIT" or "WITHDRAWAL"
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // Create tables SQL
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_MIDDLE_NAME + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_CARD_NUMBER + " TEXT UNIQUE,"
            + COLUMN_EXPIRY_MM + " TEXT,"
            + COLUMN_EXPIRY_YY + " TEXT,"
            + COLUMN_CVV + " TEXT,"
            + COLUMN_PIN + " TEXT"
            + ")";

    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNTS + "("
            + COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_ACCOUNT_TYPE + " TEXT,"
            + COLUMN_BALANCE + " REAL DEFAULT 0,"
            + COLUMN_IS_ACTIVE + " INTEGER DEFAULT 1,"
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
            + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ACCOUNT_ID_FK + " INTEGER,"
            + COLUMN_TRANSACTION_TYPE + " TEXT,"
            + COLUMN_AMOUNT + " REAL,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + COLUMN_ACCOUNT_ID_FK + ") REFERENCES " + TABLE_ACCOUNTS + "(" + COLUMN_ACCOUNT_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ACCOUNTS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void insertUser(String email, String password, String firstName, String lastName,
                           String middleName, String address, String cardNumber,
                           String expiryMM, String expiryYY, String cvv, String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CARD_NUMBER, cardNumber);
        values.put(COLUMN_EXPIRY_MM, expiryMM);
        values.put(COLUMN_EXPIRY_YY, expiryYY);
        values.put(COLUMN_CVV, cvv);
        values.put(COLUMN_PIN, pin);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public Pair<Boolean, String> checkLoginWithEmail(String email, String password) {
        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FIRST_NAME},
                COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{email, password},
                null, null, null)) {

            boolean success = cursor.getCount() > 0;
            String firstName = "";

            if (success && cursor.moveToFirst()) {
                int firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME);
                if (firstNameIndex >= 0) {
                    firstName = cursor.getString(firstNameIndex);
                }
            }
            return new Pair<>(success, firstName);
        }
    }

    public Pair<Boolean, String> checkLoginWithCard(String cardNumber, String expiryMM, String expiryYY, String cvv, String pin) {
        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FIRST_NAME},
                COLUMN_CARD_NUMBER + " = ? AND " + COLUMN_EXPIRY_MM + " = ? AND " +
                        COLUMN_EXPIRY_YY + " = ? AND " + COLUMN_CVV + " = ? AND " + COLUMN_PIN + " = ?",
                new String[]{cardNumber, expiryMM, expiryYY, cvv, pin},
                null, null, null)) {

            boolean success = cursor.getCount() > 0;
            String firstName = "";

            if (success && cursor.moveToFirst()) {
                int firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME);
                if (firstNameIndex >= 0) {
                    firstName = cursor.getString(firstNameIndex);
                }
            }
            return new Pair<>(success, firstName);
        }
    }
}