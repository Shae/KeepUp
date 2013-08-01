package com.klusman.keepup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.klusman.keepup.database.ScoreSource;
import com.klusman.keepup.screens.LocalLeaderboardList;
import com.klusman.keepup.screens.ResourceManagerActivity;



public class MainActivity extends AndroidApplication implements GameHelperListener, GoogleInterface {
	static Context context;
	public static GameHelper aHelper;
	public SQLiteOpenHelper dbHelper;
	public SQLiteDatabase database;
	ScoreSource datasource;
	public static String userName = "";
	public static MainActivity Instance = null;

	public static int spawnRateKit = 25;
	public static int spawnRateShield = 25;
	public static int spawnRateBomb = 25;
	public static int spawnRateFreeze = 25;

	
	
	private OnLeaderboardScoresLoadedListener theLeaderboardListener;

	public MainActivity(){

		context = this;
		Instance = this;
		aHelper = new GameHelper(this);
		aHelper.enableDebugLog(true, "KeepUp");
	
		//create a listener for getting raw data back from leaderboard
		theLeaderboardListener = new OnLeaderboardScoresLoadedListener() {

			public void onLeaderboardScoresLoaded(int arg0, LeaderboardBuffer arg1, LeaderboardScoreBuffer arg2) {

				for(int i = 0; i <= arg2.getCount(); i++){
					System.out.println(arg2.get(i).getScoreHolderDisplayName() + " : " + arg2.get(i).getDisplayScore());
				}
			}


		};
	}

	public String getUserName(){
		return userName;
	}

	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			Log.i(MainKeepUp.TAG, "CONNECTION: Online");
			return true;
		}
		Log.i(MainKeepUp.TAG, "CONNECTION: Not Online");
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		aHelper.setup(this);
		initialize(new MainKeepUp(MainActivity.this), cfg);
		//initialize(new ReasourceManagerActivity(MainActivity.this), cfg);
		datasource = new ScoreSource(this);
		datasource.open();
	}

	@Override
	public void onStart(){
		super.onStart();
		aHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		aHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		aHelper.onActivityResult(request, response, data);
	}

	public void onSignInFailed() {
		System.out.println("sign in failed");
	}

	public void onSignInSucceeded() {
		System.out.println("sign in succeeded");
		Player player = aHelper.getGamesClient().getCurrentPlayer();
		userName = player.getDisplayName();
	}

	public void Login() {
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
					aHelper.beginUserInitiatedSignIn();
				}
			});
		}catch (final Exception ex){

		}
	}

	public void LogOut() {
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
					aHelper.signOut();
					userName = "";
				}
			});
		}catch (final Exception ex){

		}

	}

	public boolean getSignedIn() {
		return aHelper.isSignedIn();
	}

	public void submitScore(int _score) {
		//System.out.println("in submit score");
		aHelper.getGamesClient().submitScore(getString(R.string.leaderboard_ID), _score);
	}

	public void getScores() {
		startActivityForResult(aHelper.getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_ID)), 5001 /*105*/);
	}

	public void getAchievements(){
		startActivityForResult(aHelper.getGamesClient().getAchievementsIntent(), 5001 );
	}

	public void getScoresData() {
		aHelper.getGamesClient().loadPlayerCenteredScores(theLeaderboardListener,
				getString(R.string.leaderboard_ID),
				1,
				1,
				25) ;
	}


	/**
	 * Notifies the played via alert dialog pop-up of their score.
	 * Also checks to see if the user online and if they are signed into Google Play.
	 * Adjusts Message accordingly.  
	 * Positive button links to the local or Google Play leaderboard.
	 * Negative button closes dialog.
	 * @param Score
	 */

	public void notifyUser(int Score){
		final int s = Score;
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
				
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
					String stringMsg;

					if(isOnline() == true){
						if(getSignedIn() == true){
							stringMsg = userName + "\nYour final score was: " + s + "\nSaved to Google Play and Local Leaderboards. " ;
						}else{
							stringMsg = userName + "\nYour final score was: " + s + "\nSaved to your Local Leaderboard. " ;
						}
					}else{
						stringMsg = "FINAL SCORE: " + s + "\nSaved to your Local Leaderboard. ";
					}

					// set title
					alertDialogBuilder.setTitle("-- GAME OVER --");
					// set dialog message
					alertDialogBuilder
					.setMessage(stringMsg)
					.setCancelable(false)

					.setPositiveButton("View Leaderboard",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							if(getSignedIn() == true){
								if(getSignedIn() == true){
									getScores();
								}
							}else{
								Intent intent = new Intent(MainActivity.this, LocalLeaderboardList.class);
								startActivity(intent);				
							}
							Log.i(MainKeepUp.TAG, "Leaderboard");
						}
					})
					.setNegativeButton("Done",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Log.i(MainKeepUp.TAG, "Done");
							dialog.cancel();
						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			});
		}catch (final Exception ex){

		}

	}

	/**
	 * Builds an alert dialog box with an edit text input on a separate thread.
	 * Returns nothing.  Must be caught within code.
	 */

	public void getUsername(){
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){

					final AlertDialog.Builder alert = new AlertDialog.Builder(context);
					final EditText input = new EditText(context);
					alert.setTitle("Enter User Name");
					alert.setMessage("Please enter you name for the Leaderboard.");
					alert.setView(input);
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							String value = input.getText().toString().trim();
							Log.i(MainKeepUp.TAG, "INPUT: " + value);
							userName = value;
						}
					});

					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.cancel();
						}
					});
					alert.show();   

				}
			});
		}catch (final Exception ex){

		}
	}

	public void whichLeaderboard(){
		
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
					String stringMsg;
	
					stringMsg = "Which Leaderboard would you like to view?";

					// set title
					alertDialogBuilder.setTitle("-- Leaderboard Menu --");
					// set dialog message
					alertDialogBuilder
					.setMessage(stringMsg)
					.setCancelable(false)

					.setPositiveButton("Google+",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							getScores();
						}

					})
					.setNegativeButton("Local",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							getLocalLeaderboard();
							dialog.cancel();
						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			});
		}catch (final Exception ex){

		}

	}
	
	
	
	/**
	 * Starts the page where the user can set the resource spawn ratio
	 */		
	public void startResourcePage(){
		Intent intent = new Intent(this, ResourceManagerActivity.class);
		startActivity(intent);
	}

	
	
	public void checkAndPushAchievements(int SCORE, int kitsUsed, int pointsReceivedBeforeFirstResourceUsed){

		if(SCORE <= 50){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_quick_death));
			Log.i(MainKeepUp.TAG, "Achievement: Quick Death");
		}

		if(pointsReceivedBeforeFirstResourceUsed >= 300){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_thrify_business));
			Log.i(MainKeepUp.TAG, "Achievement: Thrifty Business");
		}

		if(SCORE >= 500){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_500_points));
			Log.i(MainKeepUp.TAG, "Achievement: 500 points");
		}

		if(SCORE >= 750){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_750_points));
			Log.i(MainKeepUp.TAG, "Achievement: 750 points");
		}

		if(SCORE >= 1000){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_1000_points));
			Log.i(MainKeepUp.TAG, "Achievement: 1000 points");
		}

		if(kitsUsed >= 1){
			aHelper.getGamesClient().incrementAchievement(getString(R.string.achievement_doctor_doctor), kitsUsed);
			Log.i(MainKeepUp.TAG, "Achievement: Doctor! Doctor!");
		}
	}

	public void getLocalLeaderboard(){
		Intent intent = new Intent(MainActivity.this, LocalLeaderboardList.class);
		startActivity(intent);
	}

}