package com.klusman.keepup;

import android.util.Log;

public class ScoreHandler {

	int _SCORE;
	int _kitsUsed;
	int _pointsReceivedBeforeFirstResourceUsed;
	MainActivity _mainActivity = MainActivity.Instance;
	
	
	
	public ScoreHandler(){
		
	}


	public void checkAndPushAchievements(int SCORE, int kitsUsed, int pointsReceivedBeforeFirstResourceUsed){
		
		if(_SCORE <= 50){
			MainActivity.aHelper.getGamesClient().unlockAchievement(String.valueOf(R.string.achievement_quick_death));
		}
		
		if(_pointsReceivedBeforeFirstResourceUsed >= 300){
			MainActivity.aHelper.getGamesClient().unlockAchievement(String.valueOf(R.string.achievement_thrify_business));
		}
		
		if(_SCORE >= 500){
			MainActivity.aHelper.getGamesClient().unlockAchievement(String.valueOf(R.string.achievement_500_points));
		}
		
		if(_SCORE >= 750){
			MainActivity.aHelper.getGamesClient().unlockAchievement(String.valueOf(R.string.achievement_750_points));
		}
		
		if(_SCORE >= 1000){
			MainActivity.aHelper.getGamesClient().unlockAchievement(String.valueOf(R.string.achievement_1000_points));
		}
	
		if(_kitsUsed >= 1){
			MainActivity.aHelper.getGamesClient().incrementAchievement(String.valueOf(R.string.achievement_doctor_doctor), _kitsUsed);
		}
	}

	
	
	public void submitScores(int SCORE, int kitsUsed, int pointsBeforeResourceused){

		Log.i(MainKeepUp.TAG, "ScoreHandler submit scores");
		_SCORE = SCORE;
		_kitsUsed = kitsUsed;
		_pointsReceivedBeforeFirstResourceUsed = pointsBeforeResourceused;

		checkAndPushAchievements(_SCORE, _kitsUsed, _pointsReceivedBeforeFirstResourceUsed);
		_mainActivity.submitScore(_SCORE);
	}


}
