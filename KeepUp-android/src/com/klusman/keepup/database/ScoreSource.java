package com.klusman.keepup.database;

import java.util.ArrayList;
import java.util.List;

import com.klusman.keepup.MainKeepUp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreSource {

	SQLiteOpenHelper dbHelper;
	static SQLiteDatabase database;

	public static final String[] allColumns = {
		DBOpenHelper.COLUMN_ID,
		DBOpenHelper.COLUMN_NAME,
		DBOpenHelper.COLUMN_SCORE,
		DBOpenHelper.COLUMN_DIFFICULTY
	};

	public ScoreSource(Context context){

		dbHelper = new DBOpenHelper(context);
		Log.i(MainKeepUp.TAG, "DB Score Source");

	}


	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.i(MainKeepUp.TAG , "Database OPENED");
	}

	public void close() {
		Log.i(MainKeepUp.TAG , "Database CLOSED");
		dbHelper.close();
	}

	public ScoreObject createScore(String name, int score, int difficulty){
		open();
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.COLUMN_NAME, name);
		values.put(DBOpenHelper.COLUMN_SCORE, score);
		values.put(DBOpenHelper.COLUMN_DIFFICULTY, difficulty);
		long insertid = database.insert(DBOpenHelper.TABLE_SCORES, null, values); // GET AUTO ID
		Cursor cursor = database.query(DBOpenHelper.TABLE_SCORES,
				allColumns, DBOpenHelper.COLUMN_ID + " = " + insertid, null,
				null, null, null);
		cursor.moveToFirst();
		ScoreObject newScore = cursorToScore(cursor);
		cursor.close();

		Log.i(MainKeepUp.TAG, "Score Built");
		return newScore;

	};

	private ScoreObject cursorToScore(Cursor cursor) {
		ScoreObject myScore = new ScoreObject();
		myScore.setId(cursor.getLong(0));
		myScore.setName(cursor.getString(1));
		myScore.setScore(cursor.getInt(2));
		return myScore;
	}

	public void saveScoreToLocal(String name, int score, int difficulty) {
		ContentValues cv = new ContentValues();
		cv.put(DBOpenHelper.COLUMN_NAME, name);
		cv.put(DBOpenHelper.COLUMN_SCORE, score);
		cv.put(DBOpenHelper.COLUMN_DIFFICULTY, difficulty);

		try {
			database.insert(DBOpenHelper.TABLE_SCORES, null, cv);
			Log.i(MainKeepUp.TAG, "Saved to local leaderboard");
		} catch (Exception e) {
			Log.i(MainKeepUp.TAG, "FAILED to Save to local leaderboard");
			e.printStackTrace();
		}

	}

	public static List<ScoreObject> findAllNoFilter(){
		Log.i(MainKeepUp.TAG, "**START find all no Filter");
		String orderBy =  DBOpenHelper.COLUMN_SCORE + " DESC";
		List<ScoreObject> Scores = new ArrayList<ScoreObject>();
		Cursor c = database.query(DBOpenHelper.TABLE_SCORES, allColumns, null, null, null, null, orderBy);
		Log.i(MainKeepUp.TAG, "Scores List Returned " + c.getCount() + " rows");

		if(c.getCount() > 0){
			while(c.moveToNext()){
				ScoreObject score = new ScoreObject();
				score.setId(c.getLong(c.getColumnIndex(DBOpenHelper.COLUMN_ID)));
				score.setName(c.getString(c.getColumnIndex(DBOpenHelper.COLUMN_NAME)));
				score.setScore(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_SCORE)));
				score.setDifficulty(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_DIFFICULTY)));
				Scores.add(score);
			}
		}
		return Scores;
	} 

	public static List<ScoreObject> findAllHighToLow(){
		Log.i(MainKeepUp.TAG, "**START find all no Filter");
		String orderBy =  DBOpenHelper.COLUMN_SCORE + " DESC";
		List<ScoreObject> Scores = new ArrayList<ScoreObject>();
		Cursor c = database.query(DBOpenHelper.TABLE_SCORES, allColumns, null, null, null, null, orderBy);
		Log.i(MainKeepUp.TAG, "Scores List Returned " + c.getCount() + " rows");

		if(c.getCount() > 0){
			while(c.moveToNext()){
				ScoreObject score = new ScoreObject();
				score.setId(c.getLong(c.getColumnIndex(DBOpenHelper.COLUMN_ID)));
				score.setName(c.getString(c.getColumnIndex(DBOpenHelper.COLUMN_NAME)));
				score.setScore(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_SCORE)));
				score.setDifficulty(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_DIFFICULTY)));
				Scores.add(score);
			}
		}
		return Scores;
	} 

	public static List<ScoreObject> findAllbyName(){
		Log.i(MainKeepUp.TAG, "**START find all no Filter");
		String orderBy =  DBOpenHelper.COLUMN_NAME + " ASC";
		List<ScoreObject> Scores = new ArrayList<ScoreObject>();
		Cursor c = database.query(DBOpenHelper.TABLE_SCORES, allColumns, null, null, null, null, orderBy);
		Log.i(MainKeepUp.TAG, "Scores List Returned " + c.getCount() + " rows");

		if(c.getCount() > 0){
			while(c.moveToNext()){
				ScoreObject score = new ScoreObject();
				score.setId(c.getLong(c.getColumnIndex(DBOpenHelper.COLUMN_ID)));
				score.setName(c.getString(c.getColumnIndex(DBOpenHelper.COLUMN_NAME)));
				score.setScore(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_SCORE)));
				score.setDifficulty(c.getInt(c.getColumnIndex(DBOpenHelper.COLUMN_DIFFICULTY)));
				Scores.add(score);
			}
		}
		return Scores;
	} 



	public void deleteTableAndRebuild(){
		database.execSQL("DROP TABLE IF EXISTS " + DBOpenHelper.TABLE_SCORES);
		Log.i(MainKeepUp.TAG, "DROP TABLE");
		database.execSQL(DBOpenHelper.TABLE_CREATE);
		Log.i(MainKeepUp.TAG, "REBUILD TABLE");
	}

}
