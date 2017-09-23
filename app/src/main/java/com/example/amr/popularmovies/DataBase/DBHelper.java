package com.example.amr.popularmovies.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amr.popularmovies.Models.MainResponse;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Favourite";

    public static final String TableName = "FavouriteTable";
    public static final String Table_ID = "id";
    public static final String Movie_ID = "idd";
    public static final String Movie_Image = "imageposter";
    public static final String Movie_Image2 = "imageback";
    public static final String Movie_Title = "title";
    public static final String Movie_Rate = "rate";
    public static final String Movie_Year = "year";
    public static final String Movie_Overview = "overview";


    public static final String CREATE_TABLE_Movie = "create table " + TableName +
            "( " + Table_ID + " integer primary key autoincrement ," +
            Movie_ID + " text ," + Movie_Image + " text , " +
            Movie_Image2 + " text ," +
            Movie_Title + " text , " + Movie_Rate + " text , " +
            Movie_Year + " text , " + Movie_Overview + " text ) ;";

    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase mSQLiteDatabase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Movie);
    }

    public void addMovie(int _Movie_ID, String _Movie_Image, String _Movie_Image2, String _Movie_Title, Double _Movie_Rate, String _Movie_Year, String _Movie_Overview) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Movie_ID, _Movie_ID);
        contentValues.put(Movie_Image, _Movie_Image);
        contentValues.put(Movie_Image2, _Movie_Image2);
        contentValues.put(Movie_Title, _Movie_Title);
        contentValues.put(Movie_Rate, _Movie_Rate);
        contentValues.put(Movie_Year, _Movie_Year);
        contentValues.put(Movie_Overview, _Movie_Overview);
        mSQLiteDatabase.insert(TableName, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TableName);
        onCreate(db);
    }

    public Cursor checkmovie(int s) {
        mSQLiteDatabase = getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + TableName + " where " + Movie_ID + " = " + "'" + s + "'", null);
        return cursor;
    }

    public void deleterow(String Title) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TableName, Movie_Title + " = ?", new String[]{Title});
    }
}