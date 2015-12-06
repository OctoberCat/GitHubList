package com.repodb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RepositoryHelper extends SQLiteOpenHelper {


    public static final String TASKS_TABLE_NAME = "repositories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STARS = "stars";
    public static final String COLUMN_FORKS = "forks";
    public static final String COLUMN_WATCHERS = "watchers";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_LANGUAGE = "language";

    private static final String DATABASE_NAME = "repositories.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TASKS_TABLE_NAME + " ( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_STARS + " text not null, " + COLUMN_FORKS + " text not null, " + COLUMN_WATCHERS + " text not null, " + COLUMN_FULL_NAME
            + " text not null, " + COLUMN_DESCRIPTION + " text not null, " + COLUMN_URL + " text not null, " + COLUMN_LANGUAGE + " text  " + ");";

    public RepositoryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RepositoryHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        onCreate(db);
    }

}

