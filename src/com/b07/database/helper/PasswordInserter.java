package com.b07.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.b07.security.PasswordHelpers;

import java.math.BigDecimal;

public class PasswordInserter extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventorymgmt.db";

    public PasswordInserter(Context context) {

    super(context, DATABASE_NAME, null, 1);
  }
    
    protected void insertUnhashedPassword(String password, int userId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("USERID", userId);
        contentValues.put("PASSWORD", password);
        sqLiteDatabase.insert("USERPW", null, contentValues);
        sqLiteDatabase.close();
      }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
