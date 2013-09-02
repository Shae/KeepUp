package com.klusman.keepup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import com.klusman.keepup.screens.GameSettings;



public class MainActivity extends AndroidApplication implements GameHelperListener, GoogleInterface {


	static Context context;
	MainKeepUp _game;
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
	SharedPreferences prefs;
	public static boolean vibrate = true;
	private OnLeaderboardScoresLoadedListener theLeaderboardListener;
	int gameDif = 1;
	int finalScore = 0;
	AlertDialog difficultyDialog;

	public static Music bgMusic;
	boolean playTheMusic;
	boolean popupActive = false;
	boolean isSignedInFromMainMenu = false;

	public int Avatar = 1;

	public MainActivity(){
		Log.i(MainKeepUp.TAG, "MainAcitivy");

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


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		aHelper.setup(this);
		initialize(new MainKeepUp(MainActivity.this), cfg);
		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);

		spawnRateKit = prefs.getInt("kitValue", 25);
		spawnRateShield = prefs.getInt("shieldValue", 25);
		spawnRateFreeze = prefs.getInt("freezeValue", 25);
		spawnRateBomb = prefs.getInt("bombValue", 25);


		datasource = new ScoreSource(this);
		datasource.open();
		playTheMusic = getSoundBool();

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/gymShoes16.mp3"));	
		bgMusic.setLooping(true);  
		bgMusic.setVolume(0.4f);
		if(playTheMusic == true){
			bgMusic.play();
		}
	}


	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			Log.i(MainKeepUp.TAG, "CONNECTION: Online");
			return true;
		}
		Log.i(MainKeepUp.TAG, "CONNECTION: Not Online");
		return false;
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
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		aHelper.onActivityResult(request, response, data);
	}

	public void onSignInFailed() {
		isSignedInFromMainMenu = false;
	}

	public void onSignInSucceeded() {
		Player player = aHelper.getGamesClient().getCurrentPlayer();
		userName = player.getDisplayName();
		isSignedInFromMainMenu = true;
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
		setPopupActive(true);
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
						stringMsg = userName + "\nYour final score was: " + s + "\nSaved to your Local Leaderboard. " ;
					}

					alertDialogBuilder.setTitle("-- GAME OVER --");
					alertDialogBuilder
					.setMessage(stringMsg)
					.setCancelable(false)

					.setPositiveButton("View Leaderboard",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {

							if((isOnline() == true) && (getSignedIn() == true)){
								if(getSignedIn() == true){
									setPopupActive(false);
									whichLeaderboard();
								}
							}else{
								setPopupActive(false);
								Intent intent = new Intent(MainActivity.this, LocalLeaderboardList.class);
								startActivity(intent);	
							}
						}
					})
					.setNegativeButton("Done",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});

					AlertDialog alertDialog = alertDialogBuilder.create();
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
		setPopupActive(true);
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){

					final AlertDialog.Builder alert = new AlertDialog.Builder(context);
					final EditText input = new EditText(context);
					alert.setTitle("Enter User Name");
					alert.setMessage("Please enter your name for the Leaderboard.");
					alert.setView(input);
					alert.setCancelable(false);
					alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							String value = input.getText().toString().trim();
							setPopupActive(false);
							userName = value;
							datasource.createScore(userName, finalScore, gameDif);
							notifyUser(finalScore);
						}
					});


					alert.show();   

				}
			});
		}catch (final Exception ex){

		}
	}

	public void whichLeaderboard(){
		setPopupActive(true);
		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
					String stringMsg;

					stringMsg = "Which Leaderboard would you like to view?";
					alertDialogBuilder.setTitle("-- Leaderboard Menu --");
					alertDialogBuilder
					.setMessage(stringMsg)
					.setCancelable(false)
					.setPositiveButton("Google+",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							setPopupActive(false);
							getScores();
						}

					})
					.setNegativeButton("Local",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							setPopupActive(false);
							getLocalLeaderboard();
							dialog.cancel();
						}
					});

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			});
		}catch (final Exception ex){

		}

	}

	public void achievement50(){
		aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_quick_death));
	}

	public void achievement500(){
		aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_500_points));
	}

	public void achievement750(){
		aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_750_points));
	}

	public void achievement1000(){
		aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_1000_points));
	}

	public void achievementKitsUsed(){
		aHelper.getGamesClient().incrementAchievement(getString(R.string.achievement_doctor_doctor), 1);
	}

	public void startResourcePage(){
		Intent intent = new Intent(this, ResourceManagerActivity.class);
		startActivity(intent);
	}


	/**
	 * Starts the User Prefs page
	 */		
	public void startUserPrefsPage(){
		Intent intent = new Intent(this, GameSettings.class);

		startActivity(intent);

	}


	public void checkAndPushAchievements(int SCORE, int kitsUsed, int pointsReceivedBeforeFirstResourceUsed){		
		if(gameDif == 3){
			if(SCORE > 800){
				if(kitsUsed == 0){
					aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_No_Medic_for_Me));
					Log.i(MainKeepUp.TAG, "Achievement: No Medic for Me! HARD MODE");
				}
			}
		}
	}

	public void getLocalLeaderboard(){
		Intent intent = new Intent(MainActivity.this, LocalLeaderboardList.class);
		startActivity(intent);
	}

	public void vibrate(int howLong){
		Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(howLong);
	}

	public boolean getVibBool(){
		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
		return prefs.getBoolean("vibrateBool", true);
	}

	public boolean getSoundBool(){
		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
		return prefs.getBoolean("soundBool", true);
	}

	public boolean getCourtBool(){
		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
		return prefs.getBoolean("darkCourt", true);
	}

	public void setUserName(String name){
		userName = name;
	}

	public void setFinalScore(int newScore){
		finalScore = newScore;
	}

	public void setDifficulty(int difficulty){
		gameDif = difficulty;
	}

	public int getGameDifficulty(){
		return gameDif;
	}


	public int getAvatar(){
		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
		return prefs.getInt("avatarChoice", 1);
	}

	public void bugReport(){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"shae.klusman@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Bug Report - Dodgeball Extreme " + MainKeepUp.VERSION);
		i.putExtra(Intent.EXTRA_TEXT   , "Please enter a description of the error here: ");

		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	public String getSavedUserName(){
		return userName;
	}

	public void setPopupActive(boolean bool){
		popupActive = bool;
	}

	public boolean getPopupActive (){
		return popupActive;
	}

	public boolean isMusicPlaying(){
		if(bgMusic.isPlaying()){
			return true;
		}
		return false;
	}

	public void playBgMusic(boolean bool){
		if(bool == true){
			bgMusic.play();
		}else{
			bgMusic.stop();
		}
	}

	public boolean isPlayerSignedIn(){
		return isSignedInFromMainMenu;
	}

}