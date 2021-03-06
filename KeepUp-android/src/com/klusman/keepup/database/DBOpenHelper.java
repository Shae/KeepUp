package com.klusman.keepup.database;

import com.klusman.keepup.MainKeepUp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "highScore.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_SCORES = "scoreTable";
	public static final String COLUMN_ID = "scoreID";
	public static final String COLUMN_NAME = "playerName";
	public static final String COLUMN_SCORE = "playerScore";
	public static final String COLUMN_DIFFICULTY = "gameDifficulty";


	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_SCORES + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT NOT NULL, " +
			COLUMN_SCORE + " INTEGER NOT NULL," +
			COLUMN_DIFFICULTY + " INTEGER NOT NULL" +

			")";


	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(TABLE_CREATE);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
		onCreate(db);

	}
}
