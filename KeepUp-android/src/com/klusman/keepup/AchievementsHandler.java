package com.klusman.keepup;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import android.util.Log;


public class AchievementsHandler extends AndroidApplication implements GameHelperListener, GoogleInterface{
	MainActivity _mainActivity;
	public static GameHelper aHelper;
	boolean firstResource = false;
	boolean ach50 = false;
	boolean ach500 = false;
	boolean ach750 = false;
	boolean ach1000 = false;
	boolean gameCheck = false;
	
	
	
	public AchievementsHandler(){
		_mainActivity = MainActivity.Instance;
	}
	
	
	
	public void checkItAll(int SCORE, int kitsUsed, int pointsReceivedBeforeFirstResourceUsed, int gameDif){
		checkAgainstGameDifficulty(gameDif, SCORE, kitsUsed);
		checkAgainstKits(kitsUsed);
		checkAgainstScore(SCORE);
	}
	

	
	public void checkAgainstScore(int SCORE){
		if((SCORE <= 50) && (ach50 == false)){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_quick_death));
			Log.i(MainKeepUp.TAG, "Achievement: Quick Death");
			ach50 = true;
		}

		if((SCORE >= 500) && (ach500 == false)){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_500_points));
			Log.i(MainKeepUp.TAG, "Achievement: 500 points");
			ach500 = true;
		}

		if((SCORE >= 750) && (ach750 == false)){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_750_points));
			Log.i(MainKeepUp.TAG, "Achievement: 750 points");
			ach750 = true;
		}

		if((SCORE >= 1000) && (ach1000 == false)){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_1000_points));
			Log.i(MainKeepUp.TAG, "Achievement: 1000 points");
			ach1000 = true;
		}

		
		
		
	}

	public void checkPointsBeforeSomething(int pointsReceivedBeforeFirstResourceUsed){
		if((pointsReceivedBeforeFirstResourceUsed >= 300) && (firstResource == false)){
			aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_thrify_business));
			Log.i(MainKeepUp.TAG, "Achievement: Thrifty Business");
			firstResource = true;
		}
	}
	
	public void checkAgainstKits(int kitsUsed){  // Need to figure out how to send 1 every time and no more.
		if(kitsUsed >= 1){
			aHelper.getGamesClient().incrementAchievement(getString(R.string.achievement_doctor_doctor), kitsUsed);
			Log.i(MainKeepUp.TAG, "Achievement: Doctor! Doctor!");
		}
	}
	
	public void checkAgainstGameDifficulty(int gameDif, int SCORE, int kitsUsed){
		if(gameCheck == false){
			if(gameDif == 3){
				if(SCORE > 800){
					if(kitsUsed == 0){
						aHelper.getGamesClient().unlockAchievement(getString(R.string.achievement_No_Medic_for_Me));
						Log.i(MainKeepUp.TAG, "Achievement: No Medic for Me! HARD MODE");
						gameCheck = true;
					}
				}
			}
		}
	}
	@Override
	public void Login() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void LogOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void submitScore(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getScores() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}


	
}
