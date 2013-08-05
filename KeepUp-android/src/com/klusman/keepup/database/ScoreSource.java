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
		DBOpenHelper.COLUMN_SCORE
	};
	
	public ScoreSource(Context context){
		
        dbHelper = new DBOpenHelper(context);

	}
	
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.i(MainKeepUp.TAG , "Database OPENED");
	}
	
	public void close() {
		Log.i(MainKeepUp.TAG , "Database CLOSED");
		dbHelper.close();
	}
	
	public ScoreObject createScore(String name, int score){
		open();
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.COLUMN_NAME, name);
		values.put(DBOpenHelper.COLUMN_SCORE, score);
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
	
	public void saveScoreToLocal(String name, int score) {
		ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.COLUMN_NAME, name);
			cv.put(DBOpenHelper.COLUMN_SCORE, score);

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
			Scores.add(score);
		}
	}
	return Scores;
} 
	
	
	
//	public List<ScoreObject> findAll(String sort){
//		Log.i(TAG, "**START find all with sort");
//		Log.i(TAG, sort);
//		List<Weapon> weapons = new ArrayList<Weapon>();
//		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_WEAPONS, allColumns, null, null, null, null, null);
//		Log.i(TAG, "Weapons List Returned " + c.getCount() + " rows");
//		
//		if(c.getCount() > 0){
//			while(c.moveToNext()){
//				Weapon weapon = new Weapon();
//				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
//				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
//				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
//				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
//				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
//				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
//				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
//				weapon.setDateUpdated(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_UPDATEDAT)));
//				//Log.i(TAG, "findAll quantity call : " + c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
//				weapons.add(weapon);
//			}
//		}
//		return weapons;
//	} // END LIST WEAPON
//	
//	public void updateItemQuant(int quant, int id){
//		ContentValues args = new ContentValues();
//		args.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, quant);
//		database.update(WeaponsDBOpenHelper.TABLE_WEAPONS, args, WeaponsDBOpenHelper.COLUMN_ID + "=" + id, null);
//	}
//	
//	public List<Weapon> preFilterALL(String sort){
//		//Log.i(TAG, "**START preFilter ALL with sort");
//		if(sort.equals("ID")){
//			Log.i(TAG, "***SORT ALL ID***");
//			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_ID);
//		}
//		if(sort.equals("Damage")){
//			Log.i(TAG, "***SORT ALL DAMAGE***");
//			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_DAMAGE);
//		}
//		if(sort.equals("Alpha")){
//			Log.i(TAG, "***SORT ALL ALPHA***");
//			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_NAME);
//		}
//		return null;
//	}
//	
//	public List<Weapon> preFilterByType(int type, String sort){
//		//int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();  <--------  COOL FOR DEBUGGING
//		//Log.i(TAG, "**START preFilterByType with sort" + "LINE#: " + String.valueOf(lineNumber));
//		if(sort.equals("ID")){
//			Log.i(TAG, "***SORT ID***");
//			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_ID);
//		}
//		if(sort.equals("Damage")){
//			Log.i(TAG, "***SORT DAMAGE***");
//			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_DAMAGE);
//		}
//		if(sort.equals("Alpha")){
//			Log.i(TAG, "***SORT ALPHA***");
//			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_NAME);
//		}
//		return null;
//	}
//	
//
//	
//	
//	public List<Weapon> filterbyTypeSortby(int type, String sort){
//		Log.i(TAG, "**START filterByType with sort");
//		Log.i(TAG, sort + " plus type: " + type);
//		List<Weapon> weaponsByType = new ArrayList<Weapon>();
//		//database.execSQL("SELECT * FROM weapons WHERE weapons.type = 1");
//		Cursor c = database.query
//				(
//				WeaponsDBOpenHelper.TABLE_WEAPONS, 
//				allColumns, 
//				WeaponsDBOpenHelper.COLUMN_TYPE + "=?", 
//				new String[] { String.valueOf(type) }, 
//				null, 
//				null, 
//				sort
//				);
//
//		//Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
//		
//		if(c.getCount() > 0){
//			while(c.moveToNext()){
//				Weapon weapon = new Weapon();
//				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
//				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
//				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
//				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
//				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
//				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
//				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
//				weapon.setDateUpdated(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_UPDATEDAT)));
//				weaponsByType.add(weapon);
//				//Log.i(TAG, weapon.getName());
//			}
//		}
//		return weaponsByType;
//	} // END LIST WEAPON
//
//
//	public void updateAnItem(long longID, int wepDwn) {
//		// TODO Auto-generated method stub
//		Log.i(TAG, "UPDATE LOCAL: " + String.valueOf(wepDwn) + " & " + String.valueOf(longID));
//		ContentValues cvUpdate = new ContentValues();
//		//cvUpdate.put(WeaponsDBOpenHelper.COLUMN_ID, longID);
//		cvUpdate.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, wepDwn);
//		database.update(WeaponsDBOpenHelper.TABLE_WEAPONS, cvUpdate, WeaponsDBOpenHelper.COLUMN_ID + "=" + longID, null);
//		Log.i(TAG, "UPDATE LOCAL: DONE");
//	}
//	


	
	
	public void deleteTableAndRebuild(){
		database.execSQL("DROP TABLE IF EXISTS " + DBOpenHelper.TABLE_SCORES);
		Log.i(MainKeepUp.TAG, "DROP TABLE");
		database.execSQL(DBOpenHelper.TABLE_CREATE);
		Log.i(MainKeepUp.TAG, "REBUILD TABLE");
	}
	
//	public List<Weapon> filterbyType(int type){
//	List<Weapon> weaponsByType = new ArrayList<Weapon>();
//	//database.execSQL("SELECT * FROM weapons WHERE weapons.type = 1");
//	Cursor c = database.query
//			(
//			WeaponsDBOpenHelper.TABLE_WEAPONS, 
//			allColumns, 
//			WeaponsDBOpenHelper.COLUMN_TYPE + "=?", 
//			new String[] { String.valueOf(type) }, 
//			null, 
//			null, 
//			null
//			);
//
//	//Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
//	
//	if(c.getCount() > 0){
//		while(c.moveToNext()){
//			Weapon weapon = new Weapon();
//			weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
//			weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
//			weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
//			weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
//			weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
//			weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
//			weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
//			weaponsByType.add(weapon);
//			//Log.i(TAG, weapon.getName());
//		}
//	}
//	return weaponsByType;
//} // END LIST WEAPON
}
